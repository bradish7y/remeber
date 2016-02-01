package com.xzm.java.collection;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.slf4j.Logger;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Bradish7Y on 15/10/30.
 */
public class TreeMapTest {
    public static final Logger log = org.slf4j.LoggerFactory.getLogger(TreeMapTest.class.getName());

    private static final int TOPK = 10;

    private static final int THREADS = 2;

    public static void main(String[] args) {

        final CountDownLatch cd = new CountDownLatch(THREADS);
        final List<TreeMap<Integer, String>> reduceList = Lists.newArrayList();

        final int[] n1 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 26, 27, 28, 29}; //generateRandom(100, 10000);
        final int[] n2 = {20, 21, 22, 23, 24, 5, 6, 7, 8, 9, 10, 32, 33, 34, 35, 36, 37}; //generateRandom(10, 10000);


        log.debug("Stage map:");
        // maps
        for (int i = 0; i < THREADS; i++) {

            new Thread(new Runnable() {
                private TreeMap<Integer, String> tree = Maps.newTreeMap();

                @Override
                public void run() {
                    init();

                    for (int i = 0; i < n2.length; i++) {
                        tree.put(n2[i], n2[i] + "");

                        if (tree.size() > 10) {
                            tree.remove(tree.firstKey());
                        }
                    }

                    for (Map.Entry<Integer, String> e : tree.entrySet()) {
                        System.out.print(e.getKey() + ",");
                    }
                    System.out.println();

                    reduceList.add(tree);

                    cd.countDown();
                }

                public void init() {
                    for (int i = 0; i < TOPK; i++) {
                        tree.put(i, i + "");
                    }
                }
            }).start();
        }

        // reduces
        new Thread(new Runnable() {
            private TreeMap<Integer, String> tree = Maps.newTreeMap();

            @Override
            public void run() {
                try {
                    cd.await();

                    log.debug("Stage reduce:");

                    for (TreeMap<Integer, String> t : reduceList) {
                        for (Map.Entry<Integer, String> e : t.entrySet()) {
                            tree.put(e.getKey(), e.getKey() + "");
                            if (tree.size() > TOPK) {
                                tree.remove(tree.firstKey());
                            }

                        }
                    }

                    for (Map.Entry<Integer, String> e : tree.entrySet()) {
                        System.out.print(e.getKey() + ",");
                    }
                    System.out.println();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.out.println("CountDownLatch.wait InterruptedException");
                }
            }

            public void init() {
                for (int i = 0; i < TOPK; i++) {
                    tree.put(i, i + "");
                }
            }
        }).start();

        /*try {
            new BufferedReader(new InputStreamReader(System.in)).readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 生成一个数组
     *
     * @return
     */
    public static int[] generateRandom(int seed, int size) {
        int[] ret = new int[size];
        Random random = new Random(seed);
        for (int i = 0; i < size; i++) {
            ret[i] = random.nextInt(size);
        }

        return ret;
    }
}
