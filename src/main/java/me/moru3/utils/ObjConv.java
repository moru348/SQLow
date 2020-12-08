package me.moru3.utils;

import me.moru3.DataType;
import org.jetbrains.annotations.NotNull;

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
}
