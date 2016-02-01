package com.xzm.lang3.date;

import org.apache.commons.lang3.time.FastDateFormat;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Bradish7Y on 15/4/24.
 */
public class FastDateFormatTest {
    public static void main(String[] args) {
        FastDateFormat fastDateFormat = FastDateFormat.getInstance("yyyyMMddHHmmss");
        System.out.println(fastDateFormat.format(new Date()));

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        System.out.println("fastDateFormat.format(cal.getTime()) = "
                + fastDateFormat.format(cal.getTime()));

        System.out.println("fastDateFormat.format(cal.getTime()) = "
                + fastDateFormat.format(cal.getTime()));

    }
}
