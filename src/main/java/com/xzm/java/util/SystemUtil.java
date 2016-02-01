package com.xzm.java.util;

/**
 * Created by Bradish7Y on 16/1/29.
 */
public class SystemUtil {
    public static void main(String[] args) {
        System.out.println("CPU核数:" + Runtime.getRuntime().availableProcessors());
        System.out.println("总内存:" + Runtime.getRuntime().totalMemory()/1024/1024 + 'm');
    }
}
