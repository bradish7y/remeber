package com.xzm.java.sync;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Bradish7Y on 16/1/17.
 * 非synchronized实现锁
 */
public class LockTest {

    public int s = 0;
    private ReentrantLock lock = new ReentrantLock();

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

        final LockTest t = new LockTest();

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
