package com.xzm.spring.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * Created by Bradish7Y on 15/11/4.
 */
@Component
public class Book {
    @Value("${url}")
    private String url;

    @Value(("${uname}"))
    private String uname;

    @Autowired
    private Environment env;

    public void pringEnv() {
        System.out.println("env.getProperty(est.env) = " + env.getProperty("test.env"));
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Book{");
        sb.append("url='").append(url).append('\'');
        sb.append(", uname='").append(uname).append('\'');
        sb.append('}');
        return sb.toString();
    }


    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    /**
     * conf()将会被调用3次
     *
     */
    @Bean
    public Integer string3() {
        Config conf = conf2();

        System.out.println("Book#conf " + conf);
        return new Integer(8);
    }

    @Bean
    public String string4() {
        Config conf = conf2();

        System.out.println("Book#conf " + conf);
        return new String();
    }

    private static int s = 0;

    @Bean
    public Config conf2() {

        System.out.println("Book#conf " + ++s + " 次");
        return new Config(s);
    }


}
