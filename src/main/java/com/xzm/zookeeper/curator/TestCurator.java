package com.xzm.zookeeper.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.RetrySleeper;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.CuratorListener;
import org.apache.curator.framework.api.transaction.CuratorTransaction;
import org.apache.curator.framework.api.transaction.CuratorTransactionResult;
import org.apache.curator.framework.recipes.nodes.PersistentEphemeralNode;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.retry.RetryOneTime;
import org.apache.curator.test.TestingServer;
import org.apache.curator.utils.CloseableUtils;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Bradish7Y on 15/10/14.
 */

public class TestCurator {
    private static final Logger log = LoggerFactory.getLogger(TestCurator.class.getName());
    private static final String PATH = "/app";
    private static final int PORT = 49767;
    private CuratorFramework client;
    private TestingServer fakeServer;

    private static final String tmpDir;

    static {
        tmpDir = System.getProperty("java.io.tmpdir") + File.separator + "curatortest";

        File file = new File(tmpDir);

        if (!file.exists() && !file.isDirectory()) {
            file.mkdir();
        }
    }

    /**
     * @param bytes
     * @return
     */
    public String toStr(byte[] bytes) {
        return new String(bytes);
    }

    @Before
    public void before() {
        try {
            fakeServer = new TestingServer(PORT, new File(tmpDir));

            client = CuratorFrameworkFactory.newClient(fakeServer.getConnectString(), new ExponentialBackoffRetry(1000, 3));
            client.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @After
    public void after() {
        try {

        } finally {
            CloseableUtils.closeQuietly(client);
            CloseableUtils.closeQuietly(fakeServer);
        }
    }

    @Test
    public void testJavaTmp() {
        System.out.println(System.getProperty("java.io.tmpdir"));
    }

    /**
     * 基本测试
     */
    @Test
    public void test() {
        try {

            log.debug("检查目录[{}]是否存在", PATH);
            // 检查目录
            Stat stat = client.checkExists().forPath(PATH);
            if (null == stat) {
                log.debug("目录[{}]不存在，建立目录", PATH);
                client.create().forPath(PATH);
            }

            // setData
            client.setData().forPath(PATH, "ronaldo's favourite is football".getBytes());

            // getData
            byte[] bytes = client.getData().forPath(PATH);
            log.debug("目录[{}]的data:{}", PATH, toStr(bytes));

            // create children
            String resp = client.create().creatingParentsIfNeeded().forPath(PATH + "/model1");
            Assert.assertNotNull(resp);
            log.debug("目录:[{}]已经创建", PATH + "/model1");

            log.debug("deleting目录[{}]", PATH + "/model1");
            // delete /app/model1
            client.delete().forPath(PATH + "/model1");
            stat = client.checkExists().forPath(PATH + "/model1");
            Assert.assertNull(stat);
            log.debug("delete successfully!!!");


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 临时节点
     */
    @org.junit.Test
    public void testEphemeral() {
        try {
            // create Ephemeral_sequential
            client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(PATH + "/tmp");

            // check Ephemeral node
            Stat stat = client.checkExists().forPath(PATH + "/tmp");
            Assert.assertNotNull(stat);

            log.debug("Close client connection");
            // 关闭客户端
            client.close();

            TimeUnit.SECONDS.sleep(5);
            log.debug("Start client.....");
            //启动客户端
            client = CuratorFrameworkFactory.newClient(fakeServer.getConnectString(), new ExponentialBackoffRetry(1000, 3));
            client.start();
            stat = client.checkExists().forPath(PATH + "/tmp");
            Assert.assertNull(stat);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 后台操作监听器
     */
    @org.junit.Test
    public void testCuratorListenerForInBackgroud() {
        try {
            // 同步倒数计数器
            final CountDownLatch count = new CountDownLatch(2);
            client.getCuratorListenable().addListener(new CuratorListener() {
                @Override
                public void eventReceived(CuratorFramework curatorFramework, CuratorEvent event) throws Exception {
                    log.debug(">>>path:{}, data:{}, type:{}", event.getPath(), toStr(event.getData()), event.getType());
                    count.countDown();
                }
            });

            log.debug("Creating 目录[{}]", PATH);
            // 注意:只有inBackground才会触发CuratorListener监听器
            client.create().creatingParentsIfNeeded().inBackground().forPath(PATH, "listener test".getBytes());

            // getData
            client.getData().inBackground().forPath(PATH);

            count.await();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 连接状态监听器，模拟丢失连接后，重新连接
     */
    @org.junit.Test
    public void testConnectionListener() {
        CuratorFramework clientRetryOneTime = null;
        try {
            // 同步倒数计数器
            final CountDownLatch count = new CountDownLatch(1);
            // 重试1次后确定丢失连接
            clientRetryOneTime = CuratorFrameworkFactory.newClient(fakeServer.getConnectString(), new RetryOneTime(1));

            clientRetryOneTime.getConnectionStateListenable().addListener(new ConnectionStateListener() {
                @Override
                public void stateChanged(CuratorFramework curatorFramework, ConnectionState connectionState) {
                    if (connectionState == ConnectionState.LOST) {
                        log.debug("Lost connection");
                        count.countDown();

                    } else if (connectionState == ConnectionState.RECONNECTED) {
                        log.debug("Reconnected !!!!!");
                    }
                }
            });

            clientRetryOneTime.start();

            // create path
            Stat stat = clientRetryOneTime.checkExists().forPath(PATH);
            if (stat == null) {
                clientRetryOneTime.create().creatingParentsIfNeeded().forPath(PATH, "just play".getBytes());
            }
            byte[] bytes = clientRetryOneTime.getData().forPath(PATH);
            log.debug("path:{}, data:{}, before stop fakeServer", PATH, toStr(bytes));

            TimeUnit.SECONDS.sleep(1);
            fakeServer.stop();
            log.debug("--------fakeServer is closed--------");

            count.await();// 等待监听到lost状态

            TimeUnit.SECONDS.sleep(1);
            log.debug("--------starting fakeServer--------");
            fakeServer = new TestingServer(fakeServer.getPort(), new File(tmpDir));
            //fakeServer.start();

            TimeUnit.SECONDS.sleep(1);
            bytes = clientRetryOneTime.getData().forPath(PATH);
            log.debug("path:{}, data:{}, after stop fakeServer", PATH, toStr(bytes));


            TimeUnit.SECONDS.sleep(3);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            clientRetryOneTime.close();
        }
    }

    /**
     * 事务
     */
    @org.junit.Test
    public void testTransaction() {
        try {
            CuratorTransaction transaction = client.inTransaction();
            Collection<CuratorTransactionResult> results = transaction.create().forPath(PATH, PATH.getBytes()).and().commit();

            byte[] bytes = client.getData().forPath(PATH);
            log.debug("path:[{}] data:{}", PATH, toStr(bytes));

            for (CuratorTransactionResult c : results) {
                System.out.println(c.getForPath() + '-' + c.getType());
            }

            transaction = client.inTransaction();
            results = transaction.create().forPath(PATH, PATH.getBytes()).and().create().forPath(PATH, PATH.getBytes()).and().commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @org.junit.Test
    public void testAssert() {
        Assert.fail();

    }

    @org.junit.Test
    public void testRetry() throws Exception {
        final int MAX_RETRIES = 3;
        final int serverPort = fakeServer.getPort();

        final CuratorFramework client2 = CuratorFrameworkFactory.newClient(fakeServer.getConnectString(), 1000, 1000, new RetryOneTime(10));
        client2.start();
        try {
            final AtomicInteger retries = new AtomicInteger(0);
            final Semaphore semaphore = new Semaphore(0);
            client2.getZookeeperClient().setRetryPolicy
                    (
                            new RetryPolicy() {
                                @Override
                                public boolean allowRetry(int retryCount, long elapsedTimeMs, RetrySleeper sleeper) {
                                    semaphore.release();// 释放3个许可，每释放一个，就在可用许可上加1
                                    if (retries.incrementAndGet() == MAX_RETRIES) {
                                        try {
                                            fakeServer = new TestingServer(serverPort);
                                        } catch (Exception e) {
                                            throw new Error(e);
                                        }
                                    }
                                    return true;
                                }
                            }
                    );

            fakeServer.stop();

            // test foreground retry
            client2.checkExists().forPath("/hey");
            // 仅当有N个可用许可时，才获得许可
            Assert.assertTrue(semaphore.tryAcquire(MAX_RETRIES, 10, TimeUnit.SECONDS));

            semaphore.drainPermits();
            retries.set(0);

            fakeServer.stop();

            // test background retry
            client2.checkExists().inBackground().forPath("/hey");
            Assert.assertTrue(semaphore.tryAcquire(MAX_RETRIES, 10, TimeUnit.SECONDS));
        } catch (Throwable e) {
            Assert.fail();
        } finally {
            CloseableUtils.closeQuietly(client2);
        }
    }

    /**
     * 持久和临时节点（受保护）
     * @throws Exception
     */
    @Test
    public void testEphemeralNode() throws Exception{
        PersistentEphemeralNode node = new PersistentEphemeralNode(client, PersistentEphemeralNode.Mode.EPHEMERAL, PATH, PATH.getBytes()) ;
        node.start();
        node.waitForInitialCreate(10, TimeUnit.SECONDS) ;

        byte[] bytes = client.getData().forPath(PATH) ;
        System.err.println(toStr(bytes));

        node.close();
    }


    @Test
    public void testEphemeralSequence()throws  Exception{

        client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath(PATH + "/item") ;
        client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath(PATH + "/item") ;
        client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath(PATH + "/item") ;
        client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath(PATH + "/item") ;
        List<String> paths = client.getChildren().forPath(PATH) ;
        for(String s : paths){
            System.out.println("s = " + s);
        }
    }

}
