package me.moru3.sqlow;

import me.moru3.sqlow.utils.ObjConv;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Function;

public class DataType<T> {
    public final static DataType<?> TINYINT = new DataType<>("TINYINT", true, true, true, true, true, true, null, Object::toString, ObjConv::toByte, Byte.class, 100);
    public final static DataType<?> SMALLINT = new DataType<>("SHORT", true, true, true, true, true, true, null, Object::toString, ObjConv::toShort, Short.class, 101);
    public final static DataType<?> MEDIUMINT = new DataType<>("MEDIUMINT", true, true, true, true, true, true, null, Object::toString, ObjConv::toInt, Integer.class, 102);
    public final static DataType<?> INT = new DataType<>("INTEGER", true, true, true, true, true, true, null, Object::toString, ObjConv::toInt, Integer.class, 103);
    public final static DataType<?> INTEGER = new DataType<>("INTEGER", true, true, true, true, true, true, null, Object::toString, ObjConv::toInt, Integer.class, 103);
    public final static DataType<?> BIGINT = new DataType<>("BIGINT", true, true, true, true, true, true, null, Object::toString, ObjConv::toLong,Long.class, 104);
    public final static DataType<?> FLOAT = new DataType<>("FLOAT", true, true, true, true, true, true, null, Object::toString, ObjConv::toFloat, Float.class, 100);
    public final static DataType<?> DOUBLE = new DataType<>("DOUBLE", true, true, true, true, true, true, null, Object::toString, ObjConv::toDouble, Double.class, 101);
    public final static DataType<?> BOOLEAN = new DataType<>("boolean", false, false, false, false, true, false, null, Object::toString, ObjConv::toBoolean, Boolean.class, 100);
    public final static DataType<?> DATETIME = new DataType<>("DATETIME", false, false, false, true, true, false, null, ObjConv::convDateTime, ObjConv::toDateTime, Date.class, 100);
    public final static DataType<?> DATE = new DataType<>("DATE", false, false, false, true, true, false, null, ObjConv::convDate, ObjConv::toDate, Date.class, 101);
    public final static DataType<?> TIME = new DataType<>("TIME", false, false, false, true, true, false, null, ObjConv::convTime, ObjConv::toTime, Date.class, 102);
    public final static DataType<?> VARCHAR = new DataType<>("VARCHAR", false, false, false, true, true, false, 255, ObjConv::convString, String::valueOf, String.class, 110);
    public final static DataType<?> TEXT = new DataType<>("TEXT", false, false, false, true, false, false, 65535,  ObjConv::convString, String::valueOf, String.class, 110);
    public final static DataType<?> LONGTEXT = new DataType<>("LONGTEXT", false, false, false, true, false, false, 2147483647, ObjConv::convString, String::valueOf, String.class, 110);

    private final String typeName;

    //private final boolean allowUnsigned;
    private final boolean allowZeroFill;
    private final boolean allowAutoIncrement;
    private final boolean allowPrimaryKey;
    private final boolean allowDefault;
    private final Function<Object, String> convM;
    private final Function<Object, T> convR;
    private final Class<T> typeClass;
    private final int priority;
    private final boolean uniqueindex;

    @Nullable
    private final Object property;

    public String getTypeName() {return typeName;}

    // public boolean getUnsigned() {return allowUnsigned;}

    public boolean getZeroFill() {return allowZeroFill;}

    public boolean getAutoIncrement() {return allowAutoIncrement;}

    public boolean getPrimaryKey() {return allowPrimaryKey;}

    public boolean getDefault() {return allowDefault;}

    public @Nullable Object getProperty() {return property;}

    public Function<Object, String> getConvM() {return convM;}

    public Function<Object, T> getConvR() {return convR;}

    public Class<T> toClass() {return typeClass;}

    public int getPriority() { return priority; }

    public boolean getUniqueIndex() {return uniqueindex; }

    public DataType(@NotNull String typeName, boolean unsigned, boolean zeroFill, boolean autoIncrement, boolean primaryKey, boolean defaultKey, boolean uniqueindex, @Nullable Object property, @NotNull Function<Object, String> convM, @NotNull Function<Object, T> convR, @NotNull Class<T> typeClass, int priority) {
        this.typeName = typeName;
        // this.allowUnsigned= unsigned;
        this.allowZeroFill = zeroFill;
        this.allowAutoIncrement = autoIncrement;
        this.allowPrimaryKey = primaryKey;
        this.allowDefault = defaultKey;
        this.convM = convM;
        this.property = property;
        this.typeClass = typeClass;
        this.convR = convR;
        this.priority = priority;
        this.uniqueindex = uniqueindex;
        DataTypes.add(this);
    }
    protected DataType() {
        this.typeName = null;
        // this.allowUnsigned= false;
        this.allowZeroFill = false;
        this.allowAutoIncrement = false;
        this.allowPrimaryKey = false;
        this.allowDefault = false;
        this.convM = null;
        this.property = null;
        this.typeClass = null;
        this.convR = null;
        this.uniqueindex = false;
        this.priority = 127;
    }
}
