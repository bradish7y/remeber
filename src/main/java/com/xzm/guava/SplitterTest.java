package com.xzm.guava;

import com.google.common.base.Splitter;

import java.util.List;
import java.util.Map;

/**
 * 
 * 测试Splitter
 * 
 * Created by Bradish7Y on 15/4/23.
 */
public class SplitterTest {
    public static void main(String[] args) {
        String string = "1|2|3|4|" ;
        // 拆分字符串
        List<String> ret = Splitter.on("|").trimResults().omitEmptyStrings().splitToList(string) ;
        System.out.println("ret.size() = " + ret.size());
        for(String s: ret){
            System.out.println("s = " + s);
        }

        String mapString = "a=1,b=2,c=3" ;
        // 拆分map
        Map<String, String> map = Splitter.on(",").withKeyValueSeparator("=").split(mapString) ;
        for(Map.Entry<String, String> m : map.entrySet()){
            System.out.println("first = " + m.getKey() +", second = " + m.getValue());
        }

    }
}
