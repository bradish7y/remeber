package com.xzm.zookeeper.curator;

import com.google.common.collect.Lists;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Bradish7Y on 15/10/16.
 * 测试leader选举
 */
public class TestLeaderEljection {
    /**
     * leader选举1
     */
    @Test
    public void testLeaderLatch() {


        List<CuratorFramework> clients = new ArrayList<CuratorFramework>();
        List<LeaderLatch> leaders = new ArrayList<LeaderLatch>();
        try {

            for (int i = 0; i < 10; i++) {
                CuratorFramework client = CuratorFrameworkFactory.builder().connectString("127.0.0.1:2181").retryPolicy(new ExponentialBackoffRetry(1000, 3)).build();
                clients.add(client);

                LeaderLatch leader = new LeaderLatch(client, "/leader", i + "");
                leaders.add(leader);

                client.start();
                leader.start();
            }

            System.err.println("Waiting election results");
            // Waiting election results
            TimeUnit.SECONDS.sleep(20);

            LeaderLatch leader = null;
            for (LeaderLatch l : leaders) {
                if (l.hasLeadership()) {
                    // 当前leader
                    leader = l;
                    System.err.println("current leader id:" + l.getId());
                }
            }

            leader.close();

            leaders.get(0).await(2, TimeUnit.SECONDS);

            System.err.println("current 0 leader id:" + leaders.get(0).getId());


            new BufferedReader(new InputStreamReader(System.in)).readLine();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            for (LeaderLatch l : leaders) {
                CloseableUtils.closeQuietly(l);
            }

            for (CuratorFramework f : clients) {
                CloseableUtils.closeQuietly(f);
            }

        }

    }

    private class LeaderSelectorImpl extends LeaderSelectorListenerAdapter implements Runnable, Closeable {

        private final String name;
        private final LeaderSelector leader;
        private final AtomicInteger leaderCount = new AtomicInteger();

        public LeaderSelectorImpl(CuratorFramework client, String path, String name) {
            this.name = name;
            this.leader = new LeaderSelector(client, path, this);
            leader.autoRequeue();
        }

        public String getId() {
            return leader.getId();
        }

        public boolean hasLeadership() {
            return leader.hasLeadership();
        }

        @Override
        public void takeLeadership(CuratorFramework curatorFramework) throws Exception {
            try {
                System.err.println("candidate#name:" + name + " is leader, Waiting " + 10 + " seconds...");
                System.err.println("candidate#:" + name + " has been leader " + leaderCount.getAndIncrement() + " time(s) before");

                // relinquishing leadership after 10 seconds
                TimeUnit.SECONDS.sleep(10);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                System.out.println(name + " relinquishing leadership.");
            }
        }

        public void start() throws IOException {
            leader.start();
        }

        @Override
        public void close() throws IOException {
            leader.close();
        }

        @Override
        public void run() {
            try {

                System.err.println("candidate#" + getId()+ ", waiting 10 seconds for election leader");
                // waiting 10 seconds for election leader
                TimeUnit.SECONDS.sleep(10);

                for (; ; ) {
                    if (leader.hasLeadership()) {
                        System.err.println("candidate:" + name + " is leadership, do nothing while sleep 15 seconds");
                        TimeUnit.SECONDS.sleep(15);
                        continue;
                    }

                    System.err.println("name:" + name + " is consumer");

                    TimeUnit.SECONDS.sleep(10);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * test LeaderSelector
     */
    @Test
    public void testLeaderSelector() {
        List<CuratorFramework> clients = Lists.newArrayList();
        List<LeaderSelectorImpl> leaders = Lists.newArrayList();
        try {
            for (int i = 0; i < 10; i++) {
                CuratorFramework client = CuratorFrameworkFactory.builder().connectString("127.0.0.1:2181").retryPolicy(new ExponentialBackoffRetry(1000, 3)).build();
                client.start();
                clients.add(client);

                LeaderSelectorImpl ls = new LeaderSelectorImpl(client, "/leader", "leader#" + i);
                ls.start();
                leaders.add(ls);

                new Thread(ls).start();
            }

            new BufferedReader(new InputStreamReader(System.in)).readLine();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            for (LeaderSelectorImpl l : leaders) {

                CloseableUtils.closeQuietly(l);
            }

            for (CuratorFramework f : clients) {
                CloseableUtils.closeQuietly(f);
            }
        }
    }

}
