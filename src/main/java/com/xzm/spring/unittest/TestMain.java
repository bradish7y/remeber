package com.xzm.spring.unittest;


import org.junit.runner.RunWith;
import org.springframework.test.annotation.Repeat;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Bradish7Y on 15/7/9.
 */
@Transactional
//@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class TestMain {

    //    @org.junit.TestMain(expected = Exception.class,timeout = 3000)
    @org.junit.Test(timeout = 3000)
    @Repeat(3)
    public void testMybatis() {
    }
}
