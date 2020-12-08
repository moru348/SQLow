package me.moru3;

import me.moru3.utils.ObjConv;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Function;

public class DataType<T> {
    public final static DataType<?> TINYINT = new DataType<>("TINYINT", true, true, true, true, true, null, Object::toString, Byte.class, 100);
    public final static DataType<?> SMALLINT = new DataType<>("SHORT", true, true, true, true, true, null, Object::toString, Short.class, 101);
    public final static DataType<?> MEDIUMINT = new DataType<>("MEDIUMINT", true, true, true, true, true, null, Object::toString, Integer.class, 102);
    public final static DataType<?> INT = new DataType<>("INT", true, true, true, true, true, null, Object::toString, Integer.class, 103);
    public final static DataType<?> BIGINT = new DataType<>("BIGINT", true, true, true, true, true, null, Object::toString, Long.class, 104);
    public final static DataType<?> FLOAT = new DataType<>("FLOAT", true, true, true, true, true, null, Object::toString, Float.class, 100);
    public final static DataType<?> DOUBLE = new DataType<>("DOUBLE", true, true, true, true, true, null, Object::toString, Double.class, 101);
    public final static DataType<?> BOOLEAN = new DataType<>("boolean", false, false, false, false, true, null, Object::toString, Boolean.class, 100);
    public final static DataType<?> DATETIME = new DataType<>("DATETIME", false, false, false, true, true, null, ObjConv::convDateTime, Date.class, 100);
    public final static DataType<?> DATE = new DataType<>("DATE", false, false, false, true, true, null, ObjConv::convDate, Date.class, 101);
    public final static DataType<?> TIME = new DataType<>("TIME", false, false, false, true, true, null, ObjConv::convTime, Date.class, 102);
    public final static DataType<?> VARCHAR = new DataType<>("VARCHAR", false, false, false, false, true, 255, ObjConv::convString, String.class, 110);
    public final static DataType<?> TEXT = new DataType<>("TEXT", false, false, false, false, false, 65535,  ObjConv::convString, String.class, 110);
    public final static DataType<?> LONGTEXT = new DataType<>("LONGTEXT", false, false, false, false, false, 	2147483647, ObjConv::convString, String.class, 110);

    public static TreeMap<Integer, List<DataType<?>>> dataTypes = new TreeMap<>();

    @NotNull
    private final String typeName;

    private final boolean allowUnsigned;
    private final boolean allowZeroFill;
    private final boolean allowAutoIncrement;
    private final boolean allowPrimaryKey;
    private final boolean allowDefault;
    private final Function<Object, String> convM;
    private final Class<T> typeClass;


    @Nullable
    private final Object property;

    @NotNull
    public String getTypeName() {return typeName;}

    public boolean getUnsigned() {return allowUnsigned;}

    public boolean getZeroFill() {return allowZeroFill;}

    public boolean getAutoIncrement() {return allowAutoIncrement;}

    public boolean getPrimaryKey() {return allowPrimaryKey;}

    public boolean getDefault() {return allowDefault;}

    public @Nullable Object getProperty() {return property;}

    public Function<Object, String> getConvM() {return convM;}

    public Class<T> toClass() {return typeClass;}

    private DataType(@NotNull String typeName, boolean unsigned, boolean zeroFill, boolean autoIncrement, boolean primaryKey, boolean defaultKey, @Nullable Object property, @NotNull Function<Object, String> convM, @NotNull Class<T> typeClass, int priority) {
        this.typeName = typeName;
        this.allowUnsigned= unsigned;
        this.allowZeroFill = zeroFill;
        this.allowAutoIncrement = autoIncrement;
        this.allowPrimaryKey = primaryKey;
        this.allowDefault = defaultKey;
        this.convM = convM;
        this.property = property;
        this.typeClass = typeClass;
        if(dataTypes.get(priority)!=null) {
            List<DataType<?>> temp = dataTypes.get(priority);
            temp.add(this);
            dataTypes.put(priority, temp);
        } else {
            List<DataType<?>> temp = new ArrayList<>();
            temp.add(this);
            dataTypes.put(priority, temp);
        }
    }
}
