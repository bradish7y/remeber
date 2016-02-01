package com.xzm.spring.configuration;

import com.xzm.spring.bean.Config;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;

/**
 * Created by Bradish7Y on 15/11/4.
 */
@Configuration
@ComponentScan("com.xzm")
@PropertySource("classpath:config.properties")
public class AppConfig {


    @Bean
    public PropertiesFactoryBean config() {
        PropertiesFactoryBean p = new PropertiesFactoryBean();

        p.setLocation(new ClassPathResource("db.properties"));
        return p;
    }


    /**
     * conf()将会被调用1次
     *
     */
    @Bean
    public String string1() {
        Config conf = conf();

        System.out.println("AppConfig#conf " + conf);
        return new String();
    }

    @Bean
    public String string2() {
        Config conf = conf();

        System.out.println("AppConfig#conf " + conf);
        return new String();
    }

    private static int s = 0;

    @Bean
    //@Scope("prototype")
    public Config conf() {

        System.out.println("AppConfig#conf " + ++s + " 次");
        return new Config(s);
    }

}
