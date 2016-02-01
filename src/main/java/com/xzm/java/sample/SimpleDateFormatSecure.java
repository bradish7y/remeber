package com.xzm.java.sample;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Bradish7Y on 16/2/1.
 */
public class SimpleDateFormatSecure implements Runnable {

    private String date;

    public SimpleDateFormatSecure(String date) {
        this.date = date;
    }

    /**
     *为每个线程创建本地变量;
     */
    public static final ThreadLocal<SimpleDateFormat> sdf = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyMMddHHmmss");
        }
    };

    public Date format() throws ParseException {
        return sdf.get().parse(date);
    }

    //以下为线程不安全方式;如果改用非静态变量,在线程池时,随着任务数量的增大,创建和销毁SimpleDateFormat的数量也加大,造成性能开销
    /*
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");

    public Date format() throws ParseException {
        return sdf.parse(date);
    }
    */

    @Override
    public void run() {
        while (true) {

            try {
                System.out.println(format());
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ParseException e) {

            }
        }
    }

    public static void main(String[] args) {
        ExecutorService es = Executors.newCachedThreadPool();
        for (int i = 0; i < 2; i++) {
            es.execute(new SimpleDateFormatSecure("2016020100000" + i));
        }
    }
}
