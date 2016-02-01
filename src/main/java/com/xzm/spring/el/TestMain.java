package com.xzm.spring.el;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by Bradish7Y on 15/11/4.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class TestMain {

    @Resource
    private ELBean bean ;

    @org.junit.Test
    public void test(){
        System.out.println("bean = " + bean);
    }

}
