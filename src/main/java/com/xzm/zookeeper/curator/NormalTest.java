package com.xzm.zookeeper.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.CuratorEventType;
import org.apache.curator.framework.api.CuratorListener;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.TimeUnit;

/**
 * Created by Bradish7Y on 15/10/12.
 *
 *
 */
public class NormalTest {
    private static final String BASE_DIR = "/test";

    public static class CuratorWatcherImpl implements CuratorWatcher {

        public void process(WatchedEvent watchedEvent) throws Exception {
            System.out.println("Watcher: node is changed!!!!!!");
        }
    }

    public static String getString(byte[] bytes) {
        return new String(bytes);
    }

    public static void main(String[] args) throws Exception {
        CuratorFramework client = null;

        try {
            client = CuratorFrameworkFactory.builder()
                    .connectString("127.0.0.1:2181")
                    .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                    .build();

            // 后台操作完成后触发监听器inBackground
            client.getCuratorListenable().addListener(new CuratorListener() {
                public void eventReceived(CuratorFramework curatorFramework, CuratorEvent event) throws Exception {
                    System.out.println("CuratorListener: type=" + event.getType());

                    if (event.getType() == CuratorEventType.EXISTS) {
                        System.out.println(event.getPath() + ':' + event.getData());
                    } else if (event.getType() == CuratorEventType.GET_DATA) {
                        System.out.println(event.getPath() + ':' + getString(event.getData()));

                    }
                }
            });

            // 关于连接状态的监听器
            client.getConnectionStateListenable().addListener(new ConnectionStateListener() {
                public void stateChanged(CuratorFramework curatorFramework, ConnectionState connectionState) {
                    System.out.println("ConnectionState:type=" + connectionState);
                }
            });

            client.start();

            // client.getData().inBackground().forPath(BASE_DIR) ;//后台执行时，才会触发监听器CuratorListener

            Stat stat = client.checkExists().inBackground().forPath(BASE_DIR);
            client.getData().inBackground().forPath(BASE_DIR);

            // 上面是异步执行，所以这时stat==null
            /*if (stat == null) {
                client.create().creatingParentsIfNeeded().forPath(BASE_DIR, "test curator".getBytes());
            }*/

            //            //client.create().withMode(CreateMode.EPHEMERAL).forPath("/temp");
            //
            //            byte[] tmp = client.getData().usingWatcher(new CuratorWatcherImpl()).forPath(BASE_DIR);
            //            System.out.println("new String(tmp) = " + new String(tmp));

            // 监听某个节点的变化，同client.getData().usingWatcher()
            final NodeCache nodeCache = new NodeCache(client, BASE_DIR, false);
            nodeCache.start(true);
            nodeCache.getListenable().addListener(
                    new NodeCacheListener() {
                        public void nodeChanged() throws Exception {
                            System.out.println("Node data is changed, new data: " +
                                    new String(nodeCache.getCurrentData().getData()));
                        }
                    }
            );
            /*
            // 集群服务发现，同client.getChildren.usingWatcher()
            final PathChildrenCache pathChildrenCache = new PathChildrenCache(client, BASE_DIR, true) ;
            pathChildrenCache.getListenable().addListener(new PathChildrenCacheListener() {
                public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent pathChildrenCacheEvent) throws Exception {
                    System.out.println("dir:" + BASE_DIR + ", is changed");
                }
            });
            pathChildrenCache.start(PathChildrenCache.StartMode.BUILD_INITIAL_CACHE);*/



            while (true) {
                TimeUnit.SECONDS.sleep(8);
                client.setData().inBackground().forPath(BASE_DIR, "new value".getBytes()) ;
            }

        } finally {
            CloseableUtils.closeQuietly(client);
        }
    }
}