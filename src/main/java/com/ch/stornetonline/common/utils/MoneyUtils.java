package com.ch.stornetonline.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class  MoneyUtils{
    //金额验证
    public static boolean isNumber(String str){
        Pattern pattern=Pattern.compile("^[0-9]+.?[0-9]*$"); // 判断小数点后2位的数字的正则表达式
        Matcher match=pattern.matcher(str);
        if(match.matches()==false){
            return false;
        }else{
            return true;
        }
    }

}