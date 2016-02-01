package com.xzm.zookeeper.curator;

import com.google.common.collect.Lists;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.atomic.AtomicValue;
import org.apache.curator.framework.recipes.atomic.DistributedAtomicLong;
import org.apache.curator.framework.recipes.locks.*;
import org.apache.curator.framework.recipes.shared.SharedCount;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.retry.RetryNTimes;
import org.apache.curator.test.TestingServer;
import org.apache.curator.utils.CloseableUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by Bradish7Y on 15/10/16.
 */
public class TestLock {
    /**
     * 全局可重入的锁。 Shared意味着锁是全局可见的， 客户端都可以请求锁。
     * Reentrant和JDK的ReentrantLock类似， 意味着同一个客户端在拥有锁的同时，可以
     * 多次获取，不会被阻塞。 它是由类InterProcessMutex来实现
     */
    @Test
    public void testSharedReentrantLock() {

        try {

            final CountDownLatch count = new CountDownLatch(10);
            ExecutorService es = Executors.newCachedThreadPool();

            for (int i = 0; i < 10; i++) {
                es.submit(new Runnable() {
                    @Override
                    public void run() {
                        CuratorFramework client = null;
                        try {
                            client = CuratorFrameworkFactory.builder()
                                    .connectString("127.0.0.1:2181")
                                    .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                                    .sessionTimeoutMs(10000)
                                    .connectionTimeoutMs(10000).build();
                            // 重要
                            InterProcessMutex lock = new InterProcessMutex(client, "/lock");

                            client.start();

                            // first
                            lock.acquire();

                            System.err.println("threadId[" + Thread.currentThread().getId() + "] get lock");


                            // second
                            lock.acquire();
                            System.err.println("threadId[" + Thread.currentThread().getId() + "] get lock again");

                            lock.release();// first
                            System.err.println("threadId[" + Thread.currentThread().getId() + "] release1");

                            lock.release();// second
                            System.err.println("threadId[" + Thread.currentThread().getId() + "] release2");

                            System.err.println(lock.isAcquiredInThisProcess());

                            TimeUnit.SECONDS.sleep(60);
                            // 线程结束，锁也就释放了
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            client.close();
                        }

                    }
                });
                /*new Thread(new Runnable() {
                    @Override
                    public void run() {
                        CuratorFramework client = null ;
                        try {
                            client = CuratorFrameworkFactory.builder()
                                    .connectString("127.0.0.1:2181")
                                    .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                                    .sessionTimeoutMs(10000)
                                    .connectionTimeoutMs(10000).build();
                            // **
                            InterProcessMutex lock = new InterProcessMutex(client, "/lock");
                            client.start();

                            lock.acquire() ;

                            System.err.println("threadId[" + Thread.currentThread().getId() + "] get lock" );
                            TimeUnit.SECONDS.sleep(5);
                            System.err.println("threadId[" + Thread.currentThread().getId() + "] release" );

                            lock.release();
                            count.countDown();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }finally {
                            client.close();
                        }

                    }
                }).start();*/
            }

            count.await();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 不可重入
     */
    @Test
    public void testInterProcessSemaphoreMutex() {
        try {

            final CountDownLatch count = new CountDownLatch(1);
            ExecutorService es = Executors.newCachedThreadPool();

            for (int i = 0; i < 10; i++) {
                es.submit(new Runnable() {
                    @Override
                    public void run() {
                        CuratorFramework client = null;
                        try {
                            client = CuratorFrameworkFactory.builder()
                                    .connectString("127.0.0.1:2181")
                                    .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                                    .sessionTimeoutMs(10000)
                                    .connectionTimeoutMs(10000).build();
                            // 重要
                            InterProcessSemaphoreMutex lock = new InterProcessSemaphoreMutex(client, "/lock");

                            client.start();

                            // first
                            lock.acquire();// 阻塞在此行，证明InterProcessSemaphoreMutex是不可重入的锁

                            System.err.println("threadId[" + Thread.currentThread().getId() + "] get lock");


                            // second
                            lock.acquire();
                            System.err.println("threadId[" + Thread.currentThread().getId() + "] get lock again");

                            lock.release();// first
                            System.err.println("threadId[" + Thread.currentThread().getId() + "] release1");

                            lock.release();// second
                            System.err.println("threadId[" + Thread.currentThread().getId() + "] release2");

                            System.err.println(lock.isAcquiredInThisProcess());

                            TimeUnit.SECONDS.sleep(60);
                            // 线程结束，锁也就释放了
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            client.close();
                        }

                    }
                });
                /*new Thread(new Runnable() {
                    @Override
                    public void run() {
                        CuratorFramework client = null ;
                        try {
                            client = CuratorFrameworkFactory.builder()
                                    .connectString("127.0.0.1:2181")
                                    .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                                    .sessionTimeoutMs(10000)
                                    .connectionTimeoutMs(10000).build();
                            // **
                            InterProcessMutex lock = new InterProcessMutex(client, "/lock");
                            client.start();

                            lock.acquire() ;

                            System.err.println("threadId[" + Thread.currentThread().getId() + "] get lock" );
                            TimeUnit.SECONDS.sleep(5);
                            System.err.println("threadId[" + Thread.currentThread().getId() + "] release" );

                            lock.release();
                            count.countDown();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }finally {
                            client.close();
                        }

                    }
                }).start();*/
            }

            count.await();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 读写锁，一个线程获取写锁，sleep 60后释放锁，然后sleep 60，10个线程获取到读锁，60s后重新获取写锁
     */
    @Test
    public void testInterProcessReadWriteLock() {
        try {

            final CountDownLatch count = new CountDownLatch(1);
            ExecutorService es = Executors.newCachedThreadPool();

            es.submit(new Runnable() {
                @Override
                public void run() {
                    CuratorFramework client = null;
                    try {
                        client = CuratorFrameworkFactory.builder()
                                .connectString("127.0.0.1:2181")
                                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                                .sessionTimeoutMs(10000)
                                .connectionTimeoutMs(10000).build();
                        client.start();
                        // 重要
                        InterProcessReadWriteLock lock = new InterProcessReadWriteLock(client, "/lock");
                        InterProcessMutex writeLock = lock.writeLock();

                        for (; ; ) {
                            writeLock.acquire();
                            System.err.println("get write lock.....");
                            TimeUnit.SECONDS.sleep(60);
                            writeLock.release();
                            System.err.println("release write lock.....");

                            TimeUnit.SECONDS.sleep(60);
                        }
                        // 线程结束，锁也就释放了
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        client.close();
                    }

                }
            });

            TimeUnit.SECONDS.sleep(1);

            for (int i = 0; i < 10; i++)
                es.submit(new Runnable() {
                    @Override
                    public void run() {
                        CuratorFramework client = null;
                        try {
                            client = CuratorFrameworkFactory.builder()
                                    .connectString("127.0.0.1:2181")
                                    .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                                    .sessionTimeoutMs(10000)
                                    .connectionTimeoutMs(10000).build();
                            client.start();
                            // 重要
                            InterProcessReadWriteLock lock = new InterProcessReadWriteLock(client, "/lock");
                            InterProcessMutex readLock = lock.readLock();

                            for (; ; ) {
                                if (!readLock.acquire(10, TimeUnit.SECONDS)) {
                                    //System.err.println("threadId[" + Thread.currentThread().getId() + "] get lock failure");
                                    continue;
                                }

                                System.err.println("threadId[" + Thread.currentThread().getId() + "] get read lock.....");
                                TimeUnit.SECONDS.sleep(10);
                                readLock.release();
                            }

                        } catch (Exception e) {

                        } finally {
                            CloseableUtils.closeQuietly(client);
                        }
                    }
                });


            count.await();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试Semaphore租约
     */
    @Test
    public void testLease() {

        CuratorFramework client = null;
        try {
            client = CuratorFrameworkFactory.builder()
                    .connectString("127.0.0.1:2181")
                    .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                    .sessionTimeoutMs(10000)
                    .connectionTimeoutMs(10000).build();
            client.start();
            InterProcessSemaphoreV2 sem = new InterProcessSemaphoreV2(client, "/lock", 4);
            Collection<Lease> leases = sem.acquire(5, 5, TimeUnit.SECONDS);
            Assert.assertTrue("申请失败，超时或租约数不等于5", leases != null && leases.size() == 5);

            sem.returnAll(leases);
            System.err.println("归还租约");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CloseableUtils.closeQuietly(client);
        }

    }

    /**
     * 多锁对象
     */
    @Test
    public void testInterProcessMutliLock() {
        CuratorFramework client = null;
        try {
            client = CuratorFrameworkFactory.builder()
                    .connectString("127.0.0.1:2181")
                    .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                    .sessionTimeoutMs(10000)
                    .connectionTimeoutMs(10000).build();
            client.start();

            InterProcessMutex lock1 = new InterProcessMutex(client, "/lock");//可重入
            InterProcessSemaphoreMutex lock2 = new InterProcessSemaphoreMutex(client, "/app");//不可重入

            InterProcessMultiLock lock = new InterProcessMultiLock(Arrays.asList(lock1, lock2));

            // 当其中有一个acquire锁失败，其它也失败
            /*ExecutorService es = Executors.newCachedThreadPool();
            es.submit(new Runnable() {
                @Override
                public void run() {
                    CuratorFramework client = null;
                    try {
                        client = CuratorFrameworkFactory.builder()
                                .connectString("127.0.0.1:2181")
                                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                                .sessionTimeoutMs(10000)
                                .connectionTimeoutMs(10000).build();
                        client.start();
                        // 重要
                        InterProcessMutex lock1 = new InterProcessMutex(client, "/lock");//可重入
                        lock1.acquire();
                        TimeUnit.SECONDS.sleep(100);

                    } catch (Exception e) {

                    } finally {
                        CloseableUtils.closeQuietly(client);
                    }
                }
            });*/


            lock.acquire(10, TimeUnit.SECONDS);
            System.err.println("获得多锁");

            System.err.println("lock1:" + lock1.isAcquiredInThisProcess());
            System.err.println("lock2:" + lock2.isAcquiredInThisProcess());


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CloseableUtils.closeQuietly(client);
        }

    }

    /**
     * 计数器
     */
    @Test
    public void testCounter() {
        CuratorFramework client = null;
        try {
            client = CuratorFrameworkFactory.builder()
                    .connectString("127.0.0.1:2181")
                    .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                    .sessionTimeoutMs(10000)
                    .connectionTimeoutMs(10000).build();
            client.start();
            // 要想获得最新值，要么每次new，要么加监听器
            SharedCount sc = new SharedCount(client, "/count", 0);
            sc.start();

//            SharedCount sc2 = new SharedCount(client, "/count", 0);
//            sc2.start();
//            sc2.setCount(100);

            System.err.println("before versionType:" + sc.getVersionedValue().getVersion());
            sc.setCount(sc.getCount() + 1);
            System.err.println("after versionType:" + sc.getVersionedValue().getVersion());

            System.err.println(sc.getCount());



            sc.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CloseableUtils.closeQuietly(client);
        }
    }

    /**
     * long型计数器
     */
    @Test
    public void testDistributeAtomicLong(){
        final int QTY = 5 ;
        final String PATH = "/count"  ;
        try (TestingServer server = new TestingServer()) {
            CuratorFramework client = CuratorFrameworkFactory.newClient(server.getConnectString(), new ExponentialBackoffRetry(1000, 3));
            client.start();

            List<DistributedAtomicLong> examples = Lists.newArrayList();
            ExecutorService service = Executors.newFixedThreadPool(QTY);
            for (int i = 0; i < QTY; ++i) {
                final DistributedAtomicLong count = new DistributedAtomicLong(client, PATH, new RetryNTimes(10, 10));

                examples.add(count);
                Callable<Void> task = new Callable<Void>() {
                    @Override
                    public Void call() throws Exception {
                        try {
                            //Thread.sleep(rand.nextInt(1000));
                            AtomicValue<Long> value = count.increment();
                            //AtomicValue<Long> value = count.decrement();
                            //AtomicValue<Long> value = count.add((long)rand.nextInt(20));
                            System.out.println("succeed: " + value.succeeded());
                            if (value.succeeded())
                                System.out.println("Increment: from " + value.preValue() + " to " + value.postValue());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        return null;
                    }
                };
                service.submit(task);
            }

            service.shutdown();
            service.awaitTermination(10, TimeUnit.MINUTES);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}