package com.cloudbees.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    public static boolean isSameDate(Date date1, Date date2) {
        return sdf.format(date1).equals(sdf.format(date2));
    }

    public static String getStringDate(Date date) {
        return sdf.format(date);
    }
}
