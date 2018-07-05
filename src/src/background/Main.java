package baomi;

import java.util.ArrayList;

public class Main {
	public static void main(String[] args) {
		MysqlOperation mysqlOperetion = new MysqlOperation();
		System.out.println("注册测试:");
		int RegisterRet = mysqlOperetion.register("BigBoss", "7aSfHrRG4LN9", 1);
		mysqlOperetion.register("user2", "123456", 2);
		mysqlOperetion.register("user3", "123456", 3);
		mysqlOperetion.register("user4", "123456", 4);
		mysqlOperetion.register("user5", "123456", 5);
		switch (RegisterRet) {
		case -3:
			System.out.println("用户名过长");
			break;
		case -2:
			System.out.println("数据库连接失败");
			break;
		case -1:
			System.out.println("等级超出范围");
			break;
		case 0:
			System.out.println("命令执行无效");
			break;
		default:
			System.out.println("命令执行成功");
		}
		System.out.println("登陆测试:");
		int LoginRet = mysqlOperetion.login("BigBoss", "7aSfHrRG4LN9");
		switch (LoginRet) {
		case -2:
			System.out.println("数据库连接失败");
			break;
		case -1:
			System.out.println("非法用户");
			break;
		case 0:
			System.out.println("命令执行无效");
			break;
		default:
			System.out.println("命令执行成功");
			System.out.println("此用户等级为:" + LoginRet);
		}
		System.out.println("创建文件测试(文件名、真假路径都要唯一):");
		int newFileRet = mysqlOperetion.newFile("doc1", "BigBoss", 1, "/home/ubuntu/baomi/test/1.txt",
				"/home/ubuntu/baomi/test/2.txt", "123456", "456789");
		mysqlOperetion.newFile("doc2", "user5", 2, "/home/ubuntu/baomi/test/3.txt",
				"/home/ubuntu/baomi/test/4.txt", "123456", "456789");
		mysqlOperetion.newFile("doc3", "user5", 3, "/home/ubuntu/baomi/test/5.txt",
				"/home/ubuntu/baomi/test/6.txt", "123456", "456789");
		mysqlOperetion.newFile("doc4", "user5", 4, "/home/ubuntu/baomi/test/7.txt",
				"/home/ubuntu/baomi/test/8.txt", "123456", "456789");
		mysqlOperetion.newFile("doc5", "user5", 5, "/home/ubuntu/baomi/test/9.txt",
				"/home/ubuntu/baomi/test/a.txt", "123456", "456789");
		switch (newFileRet) {
		case -5:
			System.out.println("用户超出权限");
			break;
		case -4:
			System.out.println("文件不存在");
			break;
		case -3:
			System.out.println("文件等级非法");
			break;
		case -2:
			System.out.println("数据库连接失败");
			break;
		case -1:
			System.out.println("非法用户");
			break;
		case 0:
			System.out.println("SQL命令执行失效");
			break;
		default:
			System.out.println("命令执行成功");
		}
		System.out.println("查看文件测试:");
		ArrayList<String> seeFileRet = mysqlOperetion.seeFile("BigBoss", "doc1", "123456");
		System.out.println(seeFileRet.get(0));
		if(seeFileRet.size() == 2){
			System.out.println("文件路径:");
			System.out.println(seeFileRet.get(1));
		}
		System.out.println("删除文件测试(无需密码):");
		int deleteFileRet = mysqlOperetion.deleteFile("user2", "doc5");
		switch (deleteFileRet) {
		case -6:
			System.out.println("输入输出异常");
			break;
		case -5:
			System.out.println("中断指令");
			break;
		case -4:
			System.out.println("用户权限不够");
			break;
		case -3:
			System.out.println("非法文件");
			break;
		case -2:
			System.out.println("数据库连接失败");
			break;
		case -1:
			System.out.println("非法用户");
			break;
		case 0:
			System.out.println("SQL命令执行失效");
			break;
		default:
			System.out.println("命令执行成功");
		}
		System.out.println("删除用户测试:");
		int deleteUserRet = mysqlOperetion.deleteUser("BigBoss", "user5");
		switch (deleteUserRet) {
		case -3:
			System.out.println("管理员权限不够");
			break;
		case -2:
			System.out.println("数据库连接失败");
			break;
		case -1:
			System.out.println("非法管理员");
			break;
		case 0:
			System.out.println("SQL命令执行失效");
			break;
		default:
			System.out.println("命令执行成功");
		}
		System.out.println("更改密码测试:");
		int changePasswordRet = mysqlOperetion.changePassword("user2", "456789");
		switch (changePasswordRet) {
		case -2:
			System.out.println("数据库连接失败");
			break;
		case 0:
			System.out.println("命令执行无效");
			break;
		default:
			System.out.println("命令执行成功");
		}
		System.out.println("更改用户等级测试:");
		int changeUserLevelRet = mysqlOperetion.changeUserLevel("BigBoss", "user4", 5);
		switch (changeUserLevelRet) {
		case -4:
			System.out.println("输入等级超出范围");
			break;
		case -3:
			System.out.println("管理员权限不够");
			break;
		case -2:
			System.out.println("数据库连接失败");
			break;
		case -1:
			System.out.println("非法管理员");
			break;
		case 0:
			System.out.println("SQL命令执行失效");
			break;
		default:
			System.out.println("命令执行成功");
		}
		System.out.println("查看文件列表测试:");
		ArrayList<String> result = mysqlOperetion.seeFiles("user2");
		for (int i = 0; i < result.size(); i++)
			System.out.println(result.get(i));
		System.out.println("更改管理员电话测试:");
		int changeUserPhoneRet = mysqlOperetion.changeUserPhone("BigBoss", "(+86)17759925598");
		switch (changeUserPhoneRet) {
		case -2:
			System.out.println("数据库连接失败");
			break;
		case -1:
			System.out.println("手机号过长");
			break;
		case 0:
			System.out.println("SQL命令执行失效");
			break;
		default:
			System.out.println("命令执行成功");
		}
		System.out.println("查看管理员电话测试:");
		String PhoneNumber = mysqlOperetion.userPhoneNumber("BigBoss");
		System.out.println(PhoneNumber);
		mysqlOperetion.close();
	}
}
