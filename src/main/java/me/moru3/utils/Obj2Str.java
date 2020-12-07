package me.moru3.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

public abstract class Obj2Str {
    public static String convString(Object obj) {
        return "\"" + obj.toString() + "\"";
    }

    public static String convDateTime(Object obj) {
        return "\"" + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format((Date) obj) + "\"";
    }

    public static String convDate(Object obj) {
        return "\"" + new SimpleDateFormat("yyyy/MM/dd").format((Date) obj) + "\"";
    }

    public static String convTime(Object obj) {
        return "\"" + new SimpleDateFormat("HH:mm:ss").format((Date) obj) + "\"";
    }

    public static String convSet(Object obj) {
        StringBuilder result = new StringBuilder();
        ((Set<?>) obj).forEach(i -> {
            result.append(",\"").append(i).append("\"");
        });
        return new String(result).replaceFirst(",", "");
    }
}
