package com.xzm.guava.collect;

import com.google.common.collect.*;

/**
 * Multimap 不允许重复(key,value)，hash方式存放
 * get返回Collection
 * ListMultimap 允许重复，value放在list
 * get返回List
 * SetMultimap 不允许重复，hash方式存放
 * get返回Set
 * Created by Bradish7Y on 15/4/24.
 */
public class MultimapTest {
    public static void main(String[] args) {
        /**
         * 不允许重复，hash方式存放
         */
        Multimap<String, String> multimap = HashMultimap.create();
        multimap.put("1", "1");
        multimap.put("5", "1");
        multimap.put("1", "b");
        multimap.put("1", "2");
        multimap.put("3", "1");
        // 可以直接作用与主视图
        multimap.get("1").add("888") ;

        System.out.println("all multimap.toString = " + multimap);

        /**
         * 移除一个对象
         */
        multimap.remove("1", "1");
        System.out.println("remove multimap.toString = " + multimap);

        multimap.removeAll("1");
        System.out.println("removeAll multimap.toString = " + multimap);

        // 允许重复，value放在list
        ListMultimap<String, String> listMultimap = ArrayListMultimap.create();
        listMultimap.put("1", "1");
        listMultimap.put("2", "1");
        listMultimap.put("1", "1");

        System.out.println("listMultimap= " + listMultimap);

        /**
         * 不允许重复，hash方式存放
         */
        SetMultimap<String, String> setMultimap = HashMultimap.create();
        setMultimap.put("1", "1");
        setMultimap.put("1", "2");

        System.out.println("setMultimap = " + setMultimap);

        /**
         * 有序LinkedHashMultimap，底层是hash分布
         */
        Multimap<String, String> linkMap = LinkedHashMultimap.create() ;

        linkMap.put("4", "2") ;
        linkMap.put("4", "2") ;
        linkMap.put("2", "2") ;
        linkMap.put("3", "2") ;
        linkMap.put("1", "2") ;
        System.out.println("linkMap = " + linkMap);


        /**
         * 有序，LinkedListMultimap，底层数组
         */
        Multimap<String, String> linkedList = LinkedListMultimap.create() ;
        linkedList.put("4", "1") ;
        linkedList.put("2", "1") ;
        linkedList.put("3", "1") ;
        linkedList.put("1", "1") ;

        System.out.println("linkedList = " + linkedList);
    }

}
