package test;

import java.util.Scanner;

import ssl.SimpleClient;

public class test {
	public static void main(String[] args){	
       	System.out.println("USAGE: java SimpleClient host port");
       	SimpleClient sc = new SimpleClient("name","123");
       	sc.logIn(); 
       	System.out.print("您的名字？");  
        @SuppressWarnings("resource")
		Scanner sb = new Scanner(System.in); 
        sb.nextLine();
        System.out.println(sc.createFile("123","123","123","123","123"));
        System.out.println(sc.readFile(123,"123"));
        System.out.println(sc.deleFile(123,"123"));
    }
}
