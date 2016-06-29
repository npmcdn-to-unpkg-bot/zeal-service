package com.zeal.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 6/29/2016.
 */
public class DateUtils {

    public static final String SHORT = "yyyy-MM-dd";

    public static final String LONG = "yyyy-MM-dd hh:mm:ss";


    public static String format(Date date, String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date);
    }

    public static String format(Date date) {
        return format(date, LONG);
    }

    public static Date parse(String date, String pattern) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.parse(date);
    }
}
