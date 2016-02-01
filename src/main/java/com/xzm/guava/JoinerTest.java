package com.xzm.guava;

import com.google.common.base.Joiner;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Bradish7Y on 15/4/23.
 */
public class JoinerTest {
    public static void main(String[] args) {
        // 数组转字符串
        String s = Joiner.on(",").join(new Integer[]{1, 2, 3, 4, 5});
        System.out.println("s = " + s);
        // null 用指定字符替换
        s = Joiner.on(",").useForNull("a").join(new Integer[]{1, 2, null, 4, 5}) ;
        System.out.println("s = " + s);

        Map<String, String> m = new HashMap<String, String>() ;
        m.put("a", "1") ;
        m.put("b", "2") ;
        
        s = Joiner.on(",").withKeyValueSeparator("=").join(m) ;
        System.out.println("s = " + s);

    }
}
