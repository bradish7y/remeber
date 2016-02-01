package com.xzm.java.sample;

import java.util.concurrent.TimeUnit;

/**
 * 测试ctrl+c
 *
 * Created by Bradish7Y on 15/7/9.
 */
public class HookSample {
    private volatile static boolean stop = true;

    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                stop = false;
            }
        });


        while (stop) {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
