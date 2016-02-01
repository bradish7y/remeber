package com.xzm.java.sample;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Bradish7Y on 15/10/22.
 * 总结：
 * 1.同步方法，同一个对象，多线程访问，结果：按线程启动顺序执行
 * 2.静态同步方法，无论多少个new对象访问，结果都是一个线程阻塞其它
 * 3.synchronized(N.class)也是类级别的同步，无论多少个new对象访问，结果都是一个线程阻塞其它。但不会阻塞静态同步方法
 * 4.synchronized(this)和同步方法互相阻塞，细颗粒
 * <p/>
 * 类锁: 同一对象/不同对象/ClassName.staticMethod访问多会lock
 * 加在静态方法
 * synchronized(N.class)
 * <p/>
 * 对象锁: 同一对象访问会lock
 * 加在普通方法
 * synchronized(this)
 * synchronized(Object obj)
 */
public class SyncBlockTest {
    static class Example1 {
        private static int var = 0;

        /**
         * 同步的是对象
         */
        public synchronized void syncMethod1() {
            try {
                System.out.println(Thread.currentThread().getId() + " enter syncMethod1 method, var:" + var++);
                TimeUnit.SECONDS.sleep(3);
            } catch (Exception e) {

            }
        }

        /**
         * 同步的是对象
         */
        public synchronized void syncMethod2() {
            try {
                System.out.println(Thread.currentThread().getId() + " enter syncMethod2 method, var:" + var++);
                TimeUnit.SECONDS.sleep(3);
            } catch (Exception e) {

            }
        }

        /**
         * 同步的是类
         */
        public static synchronized void syncStaticMethod1() {
            try {
                System.out.println(Thread.currentThread().getId() + " enter syncStaticMethod1 method, var:" + var++);

                TimeUnit.SECONDS.sleep(3);
            } catch (Exception e) {

            }
        }

        // 测试同步块

        /**
         *
         */
        public void syncBlockThisMethod() {
            synchronized (this) {
                try {
                    System.out.println(Thread.currentThread().getId() + " enter syncBlockThisMethod method, var:" + var++);
                    TimeUnit.SECONDS.sleep(3);
                } catch (Exception e) {

                }
            }
        }

        /**
         * 该同步块属于类，无论new多少个类，同时访问该方法都会被阻塞
         */
        public void syncBlockClassMethod() {
            synchronized (SyncBlockTest.class) {
                try {
                    System.out.println(Thread.currentThread().getId() + " enter syncBlockClassMethod method, var:" + var++);
                    TimeUnit.SECONDS.sleep(3);
                } catch (Exception e) {

                }
            }
        }
    }

    /**
     * 同步普通方法,同一个对象访问同一个同步方法
     * 期待: 阻塞
     *
     * @throws Exception
     */
    @org.junit.Test
    public void testNormalMethod() throws Exception {
        final Example1 e = new Example1();


        ExecutorService pool = Executors.newCachedThreadPool();

        for (int i = 0; i < 5; i++) {
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    e.syncMethod1();
                }
            };

            pool.execute(r);
        }


        pool.shutdown();

        new BufferedReader(new InputStreamReader(System.in)).readLine();

    }

    /**
     * 多线程访问不同个对象的同步方法，期待：顺序混乱
     *
     * @throws Exception
     */
    @org.junit.Test
    public void testSync2() throws Exception {

        ExecutorService pool = Executors.newCachedThreadPool();

        for (int i = 0; i < 5; i++) {
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    new Example1().syncMethod1();
                }
            };

            pool.execute(r);
        }


        pool.shutdown();

        new BufferedReader(new InputStreamReader(System.in)).readLine();

    }

    /**
     * 多线程访问不同个对象的同步方法，期待：阻塞
     *
     * @throws Exception
     */
    @org.junit.Test
    public void testStaticMethod() throws Exception {

        ExecutorService pool = Executors.newCachedThreadPool();

        for (int i = 0; i < 5; i++) {
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    new Example1().syncStaticMethod1();
                }
            };

            pool.execute(r);
        }


        pool.shutdown();

        new BufferedReader(new InputStreamReader(System.in)).readLine();

    }

    /**
     * 测试多线程访问同一个对象的不同的同步方法，期待：访问其中一个同步方法，另一个阻塞
     * 同一个对象访问2个同步方法,期待:互相阻塞
     *
     * @throws Exception
     */
    @org.junit.Test
    public void testNormal2Method() throws Exception {

        ExecutorService pool = Executors.newCachedThreadPool();

        final Example1 e = new Example1();
        for (int i = 0; i < 5; i++) {
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    e.syncMethod1();
                }
            };

            pool.execute(r);
        }

        for (int i = 0; i < 5; i++) {
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    e.syncMethod2();
                }
            };

            pool.execute(r);
        }


        pool.shutdown();

        new BufferedReader(new InputStreamReader(System.in)).readLine();

    }
    //测试同步块

    /**
     * 多线程访问不同对象的同一个带有同步块的方法， 期待：顺序混乱
     *
     * @throws Exception
     */
    @org.junit.Test
    public void testBlockThis() throws Exception {

        ExecutorService pool = Executors.newCachedThreadPool();

        for (int i = 0; i < 5; i++) {
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    new Example1().syncBlockThisMethod();
                }
            };

            pool.execute(r);
        }


        pool.shutdown();

        new BufferedReader(new InputStreamReader(System.in)).readLine();

    }

    /**
     * 多线程访问不同对象的同一个带有同步块的方法， 期待：阻塞
     * 类锁
     *
     * @throws Exception
     */
    @org.junit.Test
    public void testBlockClass1() throws Exception {

        ExecutorService pool = Executors.newCachedThreadPool();
        final Example1 e = new Example1();

        for (int i = 0; i < 5; i++) {
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    new Example1().syncBlockClassMethod();
                }
            };

            pool.execute(r);
        }


        pool.shutdown();

        new BufferedReader(new InputStreamReader(System.in)).readLine();

    }

    /**
     * 多线程访问同一个对象的同一个带有同步块的方法， 期待：阻塞
     * 类锁
     *
     * @throws Exception
     */
    @org.junit.Test
    public void testBlockClass2() throws Exception {

        ExecutorService pool = Executors.newCachedThreadPool();
        final Example1 e = new Example1();

        for (int i = 0; i < 5; i++) {
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    e.syncBlockClassMethod();
                }
            };

            pool.execute(r);
        }


        pool.shutdown();

        new BufferedReader(new InputStreamReader(System.in)).readLine();

    }


    /**
     * synchronized(this)和synchroized(ClassName.class)互不阻塞
     *
     * @throws Exception
     */
    @org.junit.Test
    public void testBlockthisAndBlockclass() throws Exception {

        ExecutorService pool = Executors.newCachedThreadPool();

        final Example1 e = new Example1();
        for (int i = 0; i < 5; i++) {
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    e.syncBlockThisMethod();
                }
            };

            pool.execute(r);
        }

        for (int i = 0; i < 5; i++) {
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    e.syncBlockClassMethod();
                }
            };

            pool.execute(r);
        }


        pool.shutdown();

        new BufferedReader(new InputStreamReader(System.in)).readLine();

    }

    /**
     * synchronized(this)和同步方法  期待: 互相阻塞
     *
     * @throws Exception
     */
    @org.junit.Test
    public void testSyncMethodAndBlockthis() throws Exception {

        ExecutorService pool = Executors.newCachedThreadPool();

        final Example1 e = new Example1();
        for (int i = 0; i < 5; i++) {
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    e.syncMethod1();
                }
            };

            pool.execute(r);
        }

        for (int i = 0; i < 5; i++) {
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    e.syncBlockThisMethod();
                }
            };

            pool.execute(r);
        }


        pool.shutdown();

        new BufferedReader(new InputStreamReader(System.in)).readLine();

    }

    /**
     * synchronized(ClassName.class)和静态同步方法, 期待: 互不阻塞
     *
     * @throws Exception
     */
    @org.junit.Test
    public void testSync14() throws Exception {

        ExecutorService pool = Executors.newCachedThreadPool();

        final Example1 e = new Example1();
        for (int i = 0; i < 5; i++) {
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    Example1.syncStaticMethod1();
                }
            };

            pool.execute(r);
        }

        for (int i = 0; i < 5; i++) {
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    e.syncBlockClassMethod();
                }
            };

            pool.execute(r);
        }


        pool.shutdown();

        new BufferedReader(new InputStreamReader(System.in)).readLine();

    }
}
