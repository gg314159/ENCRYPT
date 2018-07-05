package background;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class LinkMysql {
	public static Connection get() {
		Connection con = null;// 声明Connection对象
		String driver = "com.mysql.jdbc.Driver";// 驱动程序名
		String url = "jdbc:mysql://localhost:3306/baomi";
		String user = "root";
		String password = "16893677";
		try {
			Runtime.getRuntime().exec("sudo service mysql start").waitFor();
			Class.forName(driver);// 加载驱动程序
			con = DriverManager.getConnection(url, user, password);// getConnection()方法，连接MySQL数据库！！
		} catch (ClassNotFoundException e) {
			System.out.println("数据库驱动类异常处理");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("数据库连接失败异常处理");
			e.printStackTrace();
		} catch (InterruptedException e) {
		} catch (IOException e) {
		}
		return con;
	}
}
