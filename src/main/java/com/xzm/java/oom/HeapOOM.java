package com.xzm.java.oom;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Bradish7Y on 16/2/2.
 */
public class HeapOOM {
    static class OOMObject{
        private String var1 ;
        private String var2 ;
        private char[] var3 ;
    }

    public static void main(String[] args) {
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (Exception e) {

        }
        List<OOMObject> list = new ArrayList<> () ;
        while(true){
            list.add(new OOMObject()) ;
        }

    }
}
