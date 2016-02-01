package com.xzm.lang3.date;

import org.apache.commons.lang3.time.DateParser;
import org.apache.commons.lang3.time.FastDateFormat;

import java.text.ParseException;
import java.util.Date;

/**
 * Created by Bradish7Y on 15/4/24.
 */
public class FastDateParserTest {
    public static void main(String[] args) {
        DateParser f = FastDateFormat.getInstance("yyyy") ;
        try {
            Date s = f.parse("2014") ;
            System.out.println("s = " + s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
