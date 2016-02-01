package com.xzm.spring.aop.introduce;

import com.xzm.spring.aop.service.Apology;

/**
 * Created by Bradish7Y on 15/7/27.
 */
//@Aspect
//@Component
public class GreetingAspect {
    //@DeclareParents(value = "com.xzm.spring.aop.service.impl.GreetingImpl", defaultImpl = ApologyImpl.class)
    private Apology apology;
}
