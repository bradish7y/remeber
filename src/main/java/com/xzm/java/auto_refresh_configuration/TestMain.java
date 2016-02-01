package com.xzm.java.auto_refresh_configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Created by Bradish7Y on 15/10/20.
 * 测试自动刷参数
 */
public class TestMain {
    public static final Logger log = LoggerFactory.getLogger(TestMain.class.getName());

    public static void main(String[] args) {
        final ConfigurationManagerImpl config = new ConfigurationManagerImpl("ua_process", 256);
        config.loadData();

        for (int i = 0; i < 5; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        for (; ; ) {
                            TimeUnit.SECONDS.sleep(10);
                            config.loadData();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        try {

            for (int i = 0; i < 5; i++) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (; ; ) {
                            log.debug("threadId#" + Thread.currentThread().getId() + "   " + config.getObject());


                            try {
                                TimeUnit.SECONDS.sleep(1);
                            } catch (Exception e) {
                            }
                        }
                    }
                }).start();
            }
        } catch (Exception e) {
        }

    }

}
