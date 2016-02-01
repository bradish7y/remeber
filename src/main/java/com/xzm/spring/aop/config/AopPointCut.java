package com.xzm.spring.aop.config;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Created by Bradish7Y on 15/7/27.
 */

@Aspect
@Component
public class AopPointCut {

    @Pointcut("execution(* com.xzm.spring.aop.service..*.*(..))")
    public void serviceLayer() {
    }

    //target 连接点的实现类

    @Before(value = "serviceLayer() && target(bean) && args(who))", argNames = "bean,who")
    public void before(Object bean, String who) {
        System.out.println("bean = " + bean);
        System.out.println("annotation before:" + who);
    }

    @After("serviceLayer()")
    public void after() {
        System.out.println("annotation after");
    }


}
