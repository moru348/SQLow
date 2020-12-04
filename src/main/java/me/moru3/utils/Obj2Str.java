package me.moru3.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

public abstract class Obj2Str {
    public String obj2Str(Object obj) {
        if(obj == null) {
            return "null";
        }
        if(obj instanceof Byte || obj instanceof Double || obj instanceof java.math.BigInteger) {
            return obj.toString();
        } else if (obj instanceof Date) {
            return "\"" + new SimpleDateFormat("yyyy/MM/dd E HH:mm:ss").format((Date) obj) + "\"";
        } else if (obj instanceof Set<?>) {
            StringBuilder result = new StringBuilder();
            ((Set<?>) obj).forEach(i -> {
                result.append(",\"").append(i).append("\"");
            });
            return new String(result).replaceFirst(",", "");
        }
        return "\"" + obj.toString() + "\"";
    }
}
