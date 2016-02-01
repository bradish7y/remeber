package com.xzm.spring.aop.proxy;

import com.xzm.spring.aop.service.Apology;
import com.xzm.spring.aop.service.impl.ApologyImpl;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Bradish7Y on 15/7/28.
 */

public class TestProxy {
    public static void main(String[] args) {
        ProxyFactory proxy = new ProxyFactory(new ApologyImpl()) ;
        proxy.addInterface(Apology.class);

        Apology apology = (Apology)proxy.getProxy() ;
        apology.saySorry("ronaldo");

    }
}
