package me.moru3.utils;

import me.moru3.DataType;
import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class ObjConv {
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

    public static DataType<?> ObjToType(@NotNull Object obj) {
        for(int key : DataType.dataTypes.keySet()) {
            for(DataType<?> type : DataType.dataTypes.get(key)) {
                if(type.toClass().isInstance(obj)) {
                    return type;
                }
            }
        }
        return DataType.VARCHAR;
    }

    public static byte toByte(@NotNull Object obj) {
        return Byte.parseByte(String.valueOf(obj));
    }


    public static short toShort(@NotNull Object obj) {
        return Short.parseShort(String.valueOf(obj));
    }

    public static int toInt(@NotNull Object obj) {
        return Integer.parseInt(String.valueOf(obj));
    }

    public static long toLong(@NotNull Object obj) {
        return Long.parseLong(String.valueOf(obj));
    }

    public static float toFloat(@NotNull Object obj) {
        return Float.parseFloat(String.valueOf(obj));
    }

    public static double toDouble(@NotNull Object obj) {
        return Double.parseDouble(String.valueOf(obj));
    }

    public static boolean toBoolean(@NotNull Object obj) {
        return (Boolean) obj;
    }

    public static Date toDateTime(Object obj) {
        try {
            return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(String.valueOf(obj));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date toDate(Object obj) {
        try {
            return new SimpleDateFormat("yyyy/MM/dd").parse(String.valueOf(obj));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date toTime(Object obj) {
        try {
            return new SimpleDateFormat("HH:mm:ss").parse(String.valueOf(obj));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
