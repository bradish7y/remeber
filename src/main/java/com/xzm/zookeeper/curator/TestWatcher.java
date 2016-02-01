package com.xzm.zookeeper.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;
import org.apache.zookeeper.data.Stat;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Objects;

/**
 * Created by Bradish7Y on 15/10/19.
 */
public class TestWatcher {
    private static final String PATH = "/cache";

    @Test
    public void testNodeCache() {
        CuratorFramework client = null;
        NodeCache nodeCache = null;
        try {

            client = CuratorFrameworkFactory.builder().connectString("127.0.0.1:2181").retryPolicy(new ExponentialBackoffRetry(1000, 3)).build();
            client.start();

            Stat stat = client.checkExists().forPath(PATH);
            if (stat == null) {
                client.create().creatingParentsIfNeeded().forPath(PATH, "test watcher".getBytes());
            }

            final NodeCache cache = new NodeCache(client, PATH);
            nodeCache = cache;
            cache.getListenable().addListener(new NodeCacheListener() {
                @Override
                public void nodeChanged() throws Exception {
                    ChildData node = cache.getCurrentData();
                    /**
                     * 创建子节点不会触发监听器
                     */
                    if (Objects.equals(node, null) || Objects.equals(node.getStat(), null)) {
                        System.err.println("node#path:" + PATH + " not create or has deleted");
                    } else {
                        System.err.println("node#path:" + node.getPath() + ", data:" + new String(node.getData()));
                    }
                }
            });

            cache.start(true);

            new BufferedReader(new InputStreamReader(System.in)).read();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CloseableUtils.closeQuietly(nodeCache);
            CloseableUtils.closeQuietly(client);
        }
    }

    /**
     * 子节点监听器
     */
    @Test
    public void testPathChildCache() {
        CuratorFramework client = null;
        PathChildrenCache cache = null;

        try {
            client = CuratorFrameworkFactory.builder().connectString("127.0.0.1:2181").retryPolicy(new ExponentialBackoffRetry(1000, 3)).build();
            client.start();

            cache = new PathChildrenCache(client, PATH, true);
            cache.getListenable().addListener(new PathChildrenCacheListener() {
                @Override
                public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent event) throws Exception {
                    System.err.println("type:" + event.getType() + ", path:" + event.getData().getPath() +
                            ", data:" + new String(event.getData().getData()));
                }
            });
            cache.start();


            new BufferedReader(new InputStreamReader(System.in)).readLine();
        } catch (Exception e) {

        } finally {
            CloseableUtils.closeQuietly(cache);
            CloseableUtils.closeQuietly(client);
        }
    }

}