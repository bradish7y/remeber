package com.xzm.guava.collect;

import com.google.common.base.Function;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.util.HashSet;
import java.util.Map;

/**
 * Created by Bradish7Y on 15/7/17.
 */
public class HashSetTest {
    public static void main(String[] args) {
        final HashSet<String> set = Sets.newHashSet();
        set.add("ronaldo");
        set.add("beckham");
        set.add("brazil");

        Map<String, Boolean> m = Maps.asMap(Sets.newHashSet(set), new Function<String, Boolean>() {
            @Override
            public Boolean apply(String input) {
                return set.contains(input);
            }
        });

        for (Map.Entry<String, Boolean> entry : m.entrySet()) {
            System.out.print("entry.getKey() = " + entry.getKey());
            System.out.println(",entry.getValue() = " + entry.getValue());
        }

    }
}
