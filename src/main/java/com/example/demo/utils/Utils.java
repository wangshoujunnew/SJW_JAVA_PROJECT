package com.example.demo.utils;

import java.util.ArrayList;

public class Utils {
    // 集合的链接
    public static String join(Object[] objects,String seq){
        String result = "";
        for (int i = 0; i < objects.length; i++) {
            result += objects[i] + seq;
        }
        return result.substring(0,result.length() - seq.length());
    }

//    public static void main(String[] args) {
//        System.out.println(Utils.join(new Object[]{"1","2"},"|&"));;
//    }
}
