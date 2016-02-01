package com.xzm.taobao.diamond;

import com.taobao.diamond.client.DiamondConfigure;
import com.taobao.diamond.common.Constants;
import com.taobao.diamond.manager.DiamondManager;
import com.taobao.diamond.manager.ManagerListener;
import com.taobao.diamond.manager.impl.DefaultDiamondManager;

import java.util.concurrent.Executor;

/**
 * Created by Bradish7Y on 16/1/11.
 */
public class Test {
    public static void main(String[] args) {

        DiamondConfigure config = new DiamondConfigure(Constants.DEFAULT_DIAMOND_CLUSTER) ;

        DefaultDiamondManager.Builder builder = new DefaultDiamondManager.Builder("test", new ManagerListener() {
            @Override
            public Executor getExecutor() {
                return null;
            }

            @Override
            public void receiveConfigInfo(String configInfo) {
                System.out.println("configInfo = " + configInfo);
            }
        }) ;

        builder.setGroup("DEFAULT_GROUP");


        DiamondManager manager = builder.build() ;


        String availableConfigureInfomation = manager.getAvailableConfigureInfomation(5000);
        System.out.println("start config: " + availableConfigureInfomation);
    }
}
