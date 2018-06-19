/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ouc.encrypt.util;

/**
 * 验证字符串的格式
 * @author ziyue
 */

public class StringUtil {
    // 1 非空验证
    public static boolean checkLength(String s){
        if(s.length() == 0){
            return false;
        }else{
            return true;
        }
    }
    
    // 2 数字验证
    public static boolean checkDigit(String s){
        String regex = "^[0-9]+$";
        if(s.matches(regex)){
            return true;
        }else{
            return false;
        }      
    }
    
    // 3 小数验证
    public static boolean checkDecimal(String s){
        String regex = "^([0-9]*.[0-9]+)|([0-9]+)$";
        if(s.matches(regex)){
            return true;
        }else{
            return false;
        }      
    }
    
    // 4 用户名验证
        public static boolean checkUsname(String s){
        //String regex = "^root[0-9]{6}$";
        return true;     
    }

    // 5 密码验证
        public static boolean checkPassword(String s){
        String regex = "^[a-zA-Z0-9]{6,18}$";
        if(s.matches(regex)){
            return true;
        }else{
            return false;
        }      
    }
        
    // 6 身份证号验证
        public static boolean checkUsid(String s){
        String regex = "^[1-9][0-9]{16}[0-9|X]$";
        if(s.matches(regex)){
            return true;
        }else{
            return false;
        }      
    }    
   
    // 7 手机号验证
        public static boolean checkUstel(String s){
        String regex = "^((13[0-9])|(15[^4])|(18[0-9])|17[0-9])[0-9]{8}$";
        if(s.matches(regex)){
            return true;
        }else{
            return false;
        }      
    }   
        
    // 8 邮箱号验证
        public static boolean checkUsemail(String s){
        String regex = "^[a-zA-Z0-9][a-zA-Z0-9_-]*@([a-zA-Z0-9_-]+\\.)+(com|gov|net|com\\.cn|edu\\.cn)$";
        if(s.matches(regex)){
            return true;
        }else{
            return false;
        }      
    }  
        
    // 9 姓名验证
        public static boolean checkRealname(String s){
        String regex = "^[\\u4e00-\\u9fa5]+$";
        if(s.matches(regex)){
            return true;
        }else{
            return false;
        }      
    }
        public static boolean checkPid(String id) {
        String regex = "^[AB][0-9][0-9]+$";
        if(id.matches(regex)){
            return true;
        }else{
            return false;
        }   
    }
}
