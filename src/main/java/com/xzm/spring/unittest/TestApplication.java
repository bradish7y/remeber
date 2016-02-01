package com.xzm.spring.unittest;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.IfProfileValue;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;
import java.util.Properties;

/**
 * Created by Bradish7Y on 15/7/29.
 */

@RunWith(SpringJUnit4ClassRunner.class)
// 加载Spring ApplicationContext配置文件
@ContextHierarchy({
        @ContextConfiguration(locations = "classpath:applicationContext.xml"),
        //@ContextConfiguration(locations = "classpath:applicationContext.xml")
}
)
@ActiveProfiles("test")
public class TestApplication {

    @Autowired
    private ApplicationContext context ;

    @Value("${name}")
    private String value;

    //System.getProperties()
    @IfProfileValue(name = "java.vendor", values = {"Oracle Corporation", "a"})
    @org.junit.Test
    public void test() {
        System.out.println("value = " + value);

        Properties p = System.getProperties() ;
        for(Map.Entry<Object, Object> e: p.entrySet()){
            System.out.println("e.getKey()+\"=\"+ e.getKey() = " + e.getKey()+"="+ e.getValue());
        }
    }

}
