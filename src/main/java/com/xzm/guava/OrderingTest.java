package com.xzm.guava;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.google.common.primitives.Ints;

import java.util.Comparator;
import java.util.List;

/**
 * Created by Bradish7Y on 15/4/23.
 */
public class OrderingTest {
    public static void main(String[] args) {
        // 重写排序器的方法
        Ordering<String> byLengthOrdering = new Ordering<String>() {
            @Override
            public int compare(String left, String right) {
                return Ints.compare(left.length(), right.length());
            }
        };

        System.out.println(byLengthOrdering.compare("a22222bc", "ddddd")) ;

        // 先声明一个排序，然后应用你
        Ordering<Integer> o2 = Ordering.natural();
        List<Integer> list = Lists.newArrayList() ;
        list.add(1) ;
        list.add(3) ;
        list.add(1) ;
        System.out.println(o2.max(list));


        // 应用排序前，先new函数
        Function<String, String> f = new Function<String, String>() {
            @Override
            public String apply(String s) {

                if(s.equals("1")){
                    return "3" ;
                }
                return s;
            }
        } ;

        Ordering<String> o3 = Ordering.natural().onResultOf(f) ;
        System.out.println(o3.compare("1", "2")) ;

        System.out.println(o2.sortedCopy(list)) ;

        // 比较器来自于java的Comparator
        Ordering.from(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return 0;
            }
        });

    }
}
