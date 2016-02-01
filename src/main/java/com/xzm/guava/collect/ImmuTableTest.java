package com.xzm.guava.collect;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

/**
 * Created by Bradish7Y on 15/4/24.
 */
public class ImmuTableTest {
    public static void main(String[] args) {
        ImmutableList<String> immutableList = ImmutableList.of("1", "2", "3") ;

        ImmutableMap<String, String> immutableMap = ImmutableMap.of("a", "1") ;
        System.out.println("immutableMap.get(\"a\") = " + immutableMap.get("a"));

    }
}
