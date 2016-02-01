package com.xzm.guava;

import com.google.common.base.Strings;

/**
 *
 * 测试Strings
 *
 * Created by Bradish7Y on 15/4/21.
 */
public class StringsTest {
    public static void main(String[] args) {
        // 重复
        System.out.println(Strings.repeat("a", 10));
        // 取相同的前缀
        System.out.println(Strings.commonPrefix("com.google.duck", "com.google.duck"));
        // 取相同的后缀
        System.out.println(Strings.commonSuffix("com.tydic", "com.tydic"));
        // 判断变量是否NULL
        System.out.println(Strings.emptyToNull("123"));
        // 判断变量是否NULL
        System.out.println(Strings.isNullOrEmpty("123"));
        // 后补零
        System.out.println(Strings.padEnd("s", 10, '0'));
        // 前补零
        System.out.println(Strings.padStart("s", 10, '0'));
    }
}
