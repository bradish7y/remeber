package com.xzm.zookeeper.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;
import org.apache.zookeeper.CreateMode;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by Bradish7Y on 15/10/16.
 */
public class TestReconnect {
    private static final String path = "/iphone";

    public static void main(String[] args) {
        CuratorFramework client = null;

        try {
            final CountDownLatch lock = new CountDownLatch(1);
            client = CuratorFrameworkFactory.builder()
                    .connectString("127.0.0.1:2181")
                    .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                    .sessionTimeoutMs(10000)
                    .connectionTimeoutMs(10000).build();

            client.getConnectionStateListenable().addListener(new ConnectionStateListener() {
                @Override
                public void stateChanged(CuratorFramework curatorFramework, ConnectionState connectionState) {
                    if (connectionState == ConnectionState.LOST) {
                        System.err.println("连接丢失......");
                    } else if (connectionState == ConnectionState.RECONNECTED) {
                        System.err.println("重新连接.....");
                        lock.countDown();
                    } else if (connectionState == ConnectionState.CONNECTED) {
                        // 首次启动时的状态
                        System.err.println("已经连接.....");
                    }
                }
            });

            client.start();

            client.create().creatingParentsIfNeeded().forPath(path, "siri".getBytes());
            client.create().withMode(CreateMode.EPHEMERAL).forPath(path + "/item", "ephemeral node".getBytes());

            // 等待重新连接
            lock.await();

            byte[] bytes = client.getData().forPath(path);
            System.err.println("重新连接后，persist /iphone:" + new String(bytes));
            bytes = client.getData().forPath(path + "/item");
            System.err.println("重新连接后，ephemeral /iphone/item:" + new String(bytes));

            TimeUnit.SECONDS.sleep(10000);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CloseableUtils.closeQuietly(client);
        }


    }
}
