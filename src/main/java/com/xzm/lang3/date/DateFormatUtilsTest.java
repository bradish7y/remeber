package com.xzm.lang3.date;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;

/**
 * Created by Bradish7Y on 15/4/24.
 */
public class DateFormatUtilsTest {
    public static void main(String[] args) {
        System.out.println(DateFormatUtils.format(new Date(), "yyyyMMddHHmmss")) ;
    }
}
