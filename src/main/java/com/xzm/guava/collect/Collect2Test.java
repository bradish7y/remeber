package com.xzm.guava.collect;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

import java.util.Collection;
import java.util.List;

/**
 * Created by Bradish7Y on 15/4/28.
 */
public class Collect2Test {
    public static void main(String[] args) {
        List<String> lists = Lists.newArrayList();
        lists.add("ronaldo");
        lists.add("beckham");

        // filter
        Collection<String> ll = Collections2.filter(lists, new Predicate<String>() {
            @Override
            public boolean apply(String s) {
                return s.equals("beckham") ? false : true;
            }
        });

        System.out.println("ll = " + ll);
        
        //transform
        ll = Collections2.transform(lists, new Function<String, String>() {
            private int sum = 0;

            @Override
            public String apply(String s) {
                sum++;
                return s + sum;
            }
        });
        System.out.println("ll = " + ll);

        List<String> lll = Lists.transform(lists, new Function<String, String>() {
            private int sum = 0;

            @Override
            public String apply(String s) {
                sum++;
                System.out.println("sum = " + sum);
                return s + sum;
            }
        });
        System.out.println("lll = " + lll);


    }
}
