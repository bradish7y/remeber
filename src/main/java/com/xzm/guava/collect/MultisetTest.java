package com.xzm.guava.collect;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

/**
 * Created by Bradish7Y on 15/4/24.
 */
public class MultisetTest {
    public static void main(String[] args) {
        Multiset<String> multiset = HashMultiset.create();
        multiset.add("1");
        multiset.add("1");
        multiset.add("1");
        multiset.add("1");

        System.out.println("count:" + multiset.count("1"));
        for (String s : multiset.elementSet()) {
            System.out.println("s = " + s);
        }

    }
}
