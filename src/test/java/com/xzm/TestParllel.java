package com.xzm;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Bradish7Y on 16/1/17.
 */
public class TestParllel {
    public int s = 0;
    private ReentrantLock lock = new ReentrantLock() ;

    public void execute(String from) {
        lock.lock();
        for (int i = 0; i < 10; i++) {
            try {
                TimeUnit.MICROSECONDS.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(from + "-i = " + i);
        }
        lock.unlock();
    }

    public static void main(String[] args) {

        final TestParllel t = new TestParllel();

        for (int i = 0; i < 2; i++) {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    t.execute(Thread.currentThread() + "");
                }
            }).start();
        }


        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
