package com.xzm.velocity;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * Created by Bradish7Y on 16/1/10.
 */
public class Test {
    public static void main(String[] args) {
        Properties pro  = new Properties() ;
        pro.setProperty(Velocity.ENCODING_DEFAULT, "utf-8") ;
        pro.setProperty(Velocity.INPUT_ENCODING, "utf-8") ;
        pro.setProperty(Velocity.OUTPUT_ENCODING, "utf-8") ;


        VelocityEngine ve = new VelocityEngine();
        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());

        ve.init();

        Template t = ve.getTemplate("velocity/test.vm", "utf-8");
        VelocityContext ctx = new VelocityContext();

        // var test
        ctx.put("name", "罗纳尔多");
        ctx.put("date", (new Date()).toString());

        // habits
        List<String> habits = new ArrayList<String>();
        habits.add("football");
        habits.add("baseball");
        habits.add("basketball");
        ctx.put("habits", habits);

        Person person = new Person ();
        person.setName("贝克汉姆");
        person.setCountry("英国");
        person.setAddress("英国曼切斯特");

        ctx.put("person", person) ;


        StringWriter sw = new StringWriter();
        t.merge(ctx, sw);
        System.out.println(sw.toString());
    }
}
