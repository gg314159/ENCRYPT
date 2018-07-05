package background;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MysqlOperation {
	private Connection con = LinkMysql.get();
	public int maxlevel = 1;
	public int minlevel = 5;
	public int columnNum = 7;

	public int register(String username, String password, int level) {
		if (level < maxlevel || level > minlevel)
			return -1;// 等级超出范围
		if (con == null)
			return -2;// 数据库连接失败返回值
		if (username.length() > 20)
			return -3;// 用户名过长
		int ret = 0;
		try {
			String command = "INSERT INTO UserTable VALUES(?, ?, ?, '')";
			PreparedStatement pstm = con.prepareStatement(command);
			pstm.setObject(1, username);
			pstm.setObject(2, password.hashCode());
			pstm.setObject(3, level);
			ret = pstm.executeUpdate();
			pstm.close();
		} catch (SQLException e) {
		}
		return ret;// 返回数据库更改记录条数
	}

	public int login(String username, String password) {
		if (con == null)
			return -2;// 数据库连接失败返回值
		int ret = 0;
		try {
			String command = "SELECT level FROM UserTable WHERE username = ? and password = ?";
			PreparedStatement pstm = con.prepareStatement(command);
			pstm.setObject(1, username);
			pstm.setObject(2, password.hashCode());
			ResultSet rs = pstm.executeQuery();
			rs.last();
			if (rs.getRow() != 1) {
				rs.close();
				pstm.close();
				return -1;// 非法用户返回值
			}
			String level = rs.getString(1);
			rs.close();
			pstm.close();
			ret = Integer.parseInt(level);
			if (ret < maxlevel || ret > minlevel)
				return -1;// 等级超出范围
		} catch (SQLException e) {
		} catch (NumberFormatException e) {
			return -1;
		}
		return ret;// 返回登陆用户等级
	}

	public int userLevel(String username) {
		if (con == null)
			return -2;// 数据库连接失败返回值
		int level = 0;
		try {
			String command = "SELECT level FROM UserTable WHERE username = ?";
			PreparedStatement pstm = con.prepareStatement(command);
			pstm.setObject(1, username);
			ResultSet rs = pstm.executeQuery();
			rs.last();
			if (rs.getRow() != 1) {
				rs.close();
				pstm.close();
				return -1;// 非法用户返回值
			}
			String Slevel = rs.getString(1);
			rs.close();
			pstm.close();
			level = Integer.parseInt(Slevel);
			if (level < maxlevel || level > minlevel)
				return -1;// 等级超出范围
		} catch (SQLException e) {
		} catch (NumberFormatException e) {
			return -1;
		}
		return level;// 返回用户等级
	}

	public int newFile(String filename, String creator, int level, String realpath, String falsepath,
			String realpassword, String falsepassword) {
		if (con == null)
			return -2;// 数据库连接失败返回值
		if (level < maxlevel || level > minlevel)
			return -3;// 文件等级非法
		File realFile = new File(realpath);
		File falseFile = new File(falsepath);
		if (!(realFile.exists() && falseFile.exists()))
			return -4;// 文件不存在
		int Ulevel = userLevel(creator);
		if (Ulevel < maxlevel)
			return Ulevel;
		if (level > Ulevel)
			return -5;// 用户超出权限
		int ret = 0;
		try {
			String command = "INSERT INTO FileTable VALUES(?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement pstm = con.prepareStatement(command);
			pstm.setObject(1, filename);
			pstm.setObject(2, creator);
			pstm.setObject(3, level);
			pstm.setObject(4, realpath);
			pstm.setObject(5, falsepath);
			pstm.setObject(6, realpassword.hashCode());
			pstm.setObject(7, falsepassword.hashCode());
			ret = pstm.executeUpdate();
			pstm.close();
		} catch (SQLException e) {
		}
		return ret;// 返回数据库更改记录条数
	}

	public ArrayList<String> seeFile(String username, String filename, String password) {
		ArrayList<String> ret = new ArrayList<String>();
		if (con == null) {
			ret.add("Database connect failed");// 数据库连接失败
			return ret;
		}
		int Ulevel = userLevel(username);
		if (Ulevel == 0) {
			ret.add("Command execute failed");// SQL执行失效
			return ret;
		}
		if (Ulevel == -1) {
			ret.add("Illegal user");// 非法用户
			return ret;
		}
		try {
			String command = "SELECT * FROM FileTable WHERE filename = ?";
			PreparedStatement pstm = con.prepareStatement(command);
			pstm.setObject(1, filename);
			ResultSet rs = pstm.executeQuery();
			rs.last();
			if (rs.getRow() != 1 || rs.getMetaData().getColumnCount() != columnNum) {
				rs.close();
				pstm.close();
				ret.add("Illegal file");// 非法文件
				return ret;
			}
			String Slevel = rs.getString(3);
			String realpath = rs.getString(4);
			String falsepath = rs.getString(5);
			String realpassword = rs.getString(6);
			String falsepassword = rs.getString(7);
			rs.close();
			pstm.close();
			int level = Integer.parseInt(Slevel);
			if (level < Ulevel) {
				ret.add("User has no this permission");// 用户权限不够
				return ret;
			}
			File realFile = new File(realpath);
			File falseFile = new File(falsepath);
			if (!(realFile.exists() && falseFile.exists())) {
				ret.add("The file has been invalidated");// 文件已经失效
				return ret;
			}
			String hashpassword = password.hashCode() + "";
			if (hashpassword.equals(realpassword)) {
				ret.add("true");// 真文件路径
				ret.add(realpath);
				return ret;
			}
			if (hashpassword.equals(falsepassword)) {
				ret.add("false");// 假文件路径
				ret.add(falsepath);
				return ret;
			} else {
				ret.add("Password error");// 密码错误
				return ret;
			}
		} catch (SQLException e) {
			ret.add("Command execute failed");// SQL执行失效
			return ret;
		} catch (NumberFormatException e) {
			ret.add("Illegal file");// 非法文件
			return ret;
		}
	}

	public int deleteFile(String username, String filename) {
		if (con == null)
			return -2;// 数据库连接失败返回值
		int Ulevel = userLevel(username);
		if (Ulevel < maxlevel)
			return Ulevel;
		try {
			String command = "SELECT * FROM FileTable WHERE filename = ?";
			PreparedStatement pstm = con.prepareStatement(command);
			pstm.setObject(1, filename);
			ResultSet rs = pstm.executeQuery();
			rs.last();
			if (rs.getRow() != 1 || rs.getMetaData().getColumnCount() != columnNum) {
				rs.close();
				pstm.close();
				return -3;// 非法文件
			}
			String Slevel = rs.getString(3);
			String realpath = rs.getString(4);
			String falsepath = rs.getString(5);
			rs.close();
			int level = Integer.parseInt(Slevel);
			if (level < Ulevel)
				return -4;// 用户权限不够
			Runtime.getRuntime().exec("sudo rm -f " + realpath).waitFor();
			Runtime.getRuntime().exec("sudo rm -f " + falsepath).waitFor();
			command = "DELETE FROM FileTable WHERE filename = ?";
			pstm = con.prepareStatement(command);
			pstm.setObject(1, filename);
			int ret = pstm.executeUpdate();
			pstm.close();
			return ret;
		} catch (SQLException e) {
			return 0;
		} catch (NumberFormatException e) {
			return -3;// 非法文件
		} catch (InterruptedException e) {
			return -5;// 中断指令
		} catch (IOException e) {
			return -6;// 输入输出异常
		}
	}

	public int deleteUser(String managename, String username) {
		if (con == null)
			return -2;// 数据库连接失败返回值
		int Mlevel = userLevel(managename);
		if (Mlevel < maxlevel)
			return Mlevel;
		if (Mlevel != maxlevel)
			return -3;// 管理员权限不够
		try {
			String command = "DELETE FROM UserTable WHERE username = ?";
			PreparedStatement pstm = con.prepareStatement(command);
			pstm.setObject(1, username);
			int ret = pstm.executeUpdate();
			pstm.close();
			return ret;
		} catch (SQLException e) {
			return 0;
		}
	}

	public int changePassword(String username, String password) {
		if (con == null)
			return -2;// 数据库连接失败返回值
		try {
			String command = "UPDATE UserTable SET password = ? WHERE username = ?";
			PreparedStatement pstm = con.prepareStatement(command);
			pstm.setObject(1, password.hashCode());
			pstm.setObject(2, username);
			int ret = pstm.executeUpdate();
			pstm.close();
			return ret;// 返回数据库更改记录条数
		} catch (SQLException e) {
			return 0;
		}
	}

	public int changeUserLevel(String managename, String username, int level) {
		if (level < maxlevel || level > minlevel)
			return -4;// 输入等级超出范围
		if (con == null)
			return -2;// 数据库连接失败返回值
		int Mlevel = userLevel(managename);
		if (Mlevel < maxlevel)
			return Mlevel;// 非法管理员
		if (Mlevel != maxlevel)
			return -3;// 管理员权限不够
		try {
			String command = "UPDATE UserTable SET level = ? WHERE username = ?";
			PreparedStatement pstm = con.prepareStatement(command);
			pstm.setObject(1, level);
			pstm.setObject(2, username);
			int ret = pstm.executeUpdate();
			pstm.close();
			return ret;// 返回数据库更改记录条数
		} catch (SQLException e) {
			return 0;
		}
	}

	public int changeUserPhone(String username, String number) {
		if (con == null)
			return -2;// 数据库连接失败返回值
		if (number.length() > 20)
			return -1;// 手机号过长
		try {
			String command = "UPDATE UserTable SET phonenumber = ? WHERE username = ?";
			PreparedStatement pstm = con.prepareStatement(command);
			pstm.setObject(1, number);
			pstm.setObject(2, username);
			int ret = pstm.executeUpdate();
			pstm.close();
			return ret;// 返回数据库更改记录条数
		} catch (SQLException e) {
			return 0;
		}
	}

	public ArrayList<String> seeFiles(String username) {
		ArrayList<String> ret = new ArrayList<String>();
		if (con == null)
			return ret;// 数据库连接失败
		int Ulevel = userLevel(username);
		if (Ulevel < maxlevel)
			return ret;
		try {
			String command = "SELECT filename FROM FileTable WHERE level >= ?";
			PreparedStatement pstm = con.prepareStatement(command);
			pstm.setObject(1, Ulevel);
			ResultSet rs = pstm.executeQuery();
			while (rs.next())
				ret.add(rs.getString(1));
			rs.close();
			pstm.close();
			return ret;
		} catch (SQLException e) {
			return ret;// SQL执行失效
		}
	}

	public String userPhoneNumber(String username) {
		if (con == null)
			return "Database connect failed";// 数据库连接失败
		try {
			String command = "SELECT phonenumber FROM UserTable WHERE username = ?";
			PreparedStatement pstm = con.prepareStatement(command);
			pstm.setObject(1, username);
			ResultSet rs = pstm.executeQuery();
			rs.last();
			if (rs.getRow() != 1) {
				rs.close();
				pstm.close();
				return "Illegal user";// 非法用户
			}
			String number = rs.getString(1);
			rs.close();
			pstm.close();
			return number;
		} catch (SQLException e) {
			return "Command execute failed";// SQL执行失效
		}
	}
	
	public void close() {
		try {
			if (con != null)
				con.close();
		} catch (SQLException e) {
		}
	}
}
