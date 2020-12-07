package me.moru3;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import me.moru3.utils.Obj2Str;

import java.util.Date;
import java.util.Set;
import java.util.function.Function;

public class DataType<T> extends Obj2Str {

    public static DataType<Byte> TINYINT = new DataType<>("TINYINT", true, true, true, true, true, Object::toString);
    public static DataType<Short> SMALLINT = new DataType<>("SHORT", true, true, true, true, true, Object::toString);
    public static DataType<Integer> MEDIUMINT = new DataType<>("MEDIUMINT", true, true, true, true, true, Object::toString);
    public static DataType<Integer> INT = new DataType<>("INT", true, true, true, true, true, Object::toString);
    public static DataType<Long> BIGINT = new DataType<>("BIGINT", true, true, true, true, true, Object::toString);
    public static DataType<Float> FLOAT = new DataType<>("FLOAT", true, true, true, true, true, Object::toString);
    public static DataType<Double> DOUBLE = new DataType<>("DOUBLE", true, true, true, true, true, Object::toString);
    public static DataType<Boolean> BOOLEAN = new DataType<>("boolean", false, false, false, false, true, Object::toString);
    public static DataType<Date> DATETIME = new DataType<>("DATETIME", false, false, false, true, true, Obj2Str::convDateTime);
    public static DataType<Date> DATE = new DataType<>("DATE", false, false, false, true, true, Obj2Str::convDate);
    public static DataType<Date> TIME = new DataType<>("TIME", false, false, false, true, true, Obj2Str::convTime);
    public static DataType<String> VARCHAR = new DataType<>("VARCHAR", false, false, false, false, true, 255, 65535, Obj2Str::convString);
    public static DataType<String> TEXT = new DataType<>("TEXT", false, false, false, false, false, 255, 14090025, Obj2Str::convString);
    public static DataType<Set<?>> ENUM = new DataType<>("ENUM", false, false, false, false, true, Obj2Str::convSet);
    public static DataType<Set<?>> SET = new DataType<>("SET", false, false, false, false, true, Obj2Str::convSet);


    @NotNull
    private final String typeName;

    private final boolean allowUnsigned;
    private final boolean allowZeroFill;
    private final boolean allowAutoIncrement;
    private final boolean allowPrimaryKey;
    private final boolean allowDefault;
    private final Function<Object, String> convM;
    private int maxLength;

    @Nullable
    private Object property;

    @NotNull
    public String getTypeName() {return typeName;}

    @NotNull
    public boolean getUnsigned() {return allowUnsigned;}

    @NotNull
    public boolean getZeroFill() {return allowZeroFill;}

    @NotNull
    public boolean getAutoIncrement() {return allowAutoIncrement;}

    @NotNull
    public boolean getPrimaryKey() {return allowPrimaryKey;}

    @NotNull
    public boolean getDefault() {return allowDefault;}

    @NotNull
    public Object getProperty() {return property;}

    @Nullable
    public int getMaxLength() {return maxLength;}

    @NotNull
    public Function<Object, String> getConvM() {return convM;}

    public DataType(@NotNull String typeName, @NotNull boolean unsigned, @NotNull boolean zeroFill, @NotNull boolean autoIncrement, @NotNull boolean primaryKey, @NotNull boolean defaultKey, @Nullable Object property, @NotNull int maxLength, @NotNull Function<Object, String> convM) {
        this.typeName = typeName;
        this.allowUnsigned= unsigned;
        this.allowZeroFill = zeroFill;
        this.allowAutoIncrement = autoIncrement;
        this.allowPrimaryKey = primaryKey;
        this.allowDefault = defaultKey;
        this.property = property;
        this.maxLength = maxLength;
        this.convM = convM;
    }

    public DataType(@NotNull String typeName, @NotNull boolean unsigned, @NotNull boolean zeroFill, @NotNull boolean autoIncrement, @NotNull boolean primaryKey, @NotNull boolean defaultKey, @NotNull Function<Object, String> convM) {
        this.typeName = typeName;
        this.allowUnsigned= unsigned;
        this.allowZeroFill = zeroFill;
        this.allowAutoIncrement = autoIncrement;
        this.allowPrimaryKey = primaryKey;
        this.allowDefault = defaultKey;
        this.convM = convM;
    }
}
