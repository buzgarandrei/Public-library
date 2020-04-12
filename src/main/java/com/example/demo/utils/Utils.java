package com.example.demo.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public static float getNoDays(Date dateStart, Date dateEnd) {
        return (float)(dateEnd.getTime() - dateStart.getTime()) / (24 * 60 * 60 * 1000) + 1;
    }
}
