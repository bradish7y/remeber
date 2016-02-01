package com.xzm.java.sample;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Bradish7Y on 15/6/25.
 */
public class ThreadPoolTest {
    public static void main(String[] args) {
        poolTest();

        System.out.println("end");
        for (int i = 0; i < 10; i++) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("in main");
        }
        //pool.shutdown();
    }

    private static void poolTest() {

        ExecutorService pool = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            pool.execute(new Runnable() {
                public void run() {
                    try {
                        TimeUnit.MILLISECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(System.currentTimeMillis());
                }
            });

//            if(f.isDone()){
//                System.out.println("success");
//            }
        }
    }
}
