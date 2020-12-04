package me.moru3;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import me.moru3.utils.Obj2Str;

import java.util.Date;
import java.util.Set;

public class DataType<T> extends Obj2Str {

    public static DataType<Byte> TINYINT = new DataType<>("TINYINT", true, true, true, true, true);
    public static DataType<Short> SMALLINT = new DataType<>("SHORT", true, true, true, true, true);
    public static DataType<Integer> MEDIUMINT = new DataType<>("MEDIUMINT", true, true, true, true, true);
    public static DataType<Integer> INT = new DataType<>("INT", true, true, true, true, true);
    public static DataType<Long> BIGINT = new DataType<>("BIGINT", true, true, true, true, true);
    public static DataType<Float> FLOAT = new DataType<>("FLOAT", true, true, true, true, true);
    public static DataType<Double> DOUBLE = new DataType<>("DOUBLE", true, true, true, true, true);
    public static DataType<Boolean> BOOLEAN = new DataType<>("BOOLEAN", false, false, false, false, true);
    public static DataType<Date> DATETIME = new DataType<>("DATETIME", false, false, false, true, true);
    public static DataType<Date> DATE = new DataType<>("DATE", false, false, false, true, true);
    public static DataType<Date> TIME = new DataType<>("TIME", false, false, false, true, true);
    public static DataType<String> VARCHAR = new DataType<>("VARCHAR", false, false, false, false, true, 255, 65535);
    public static DataType<String> TEXT = new DataType<>("TEXT", false, false, false, false, false, 255, 14090025);
    public static DataType<Set<?>> ENUM = new DataType<>("ENUM", false, false, false, false, true);
    public static DataType<Set<?>> SET = new DataType<>("SET", false, false, false, false, true);


    @NotNull
    private final String typeName;

    private final Boolean allowUnsigned;
    private final Boolean allowZeroFill;
    private final Boolean allowAutoIncrement;
    private final Boolean allowPrimaryKey;
    private final Boolean allowDefault;
    private Integer maxLength;

    @Nullable
    private Object property;

    public String getTypeName() {return typeName;}
    public Boolean getUnsigned() {return allowUnsigned;}
    public Boolean getZeroFill() {return allowZeroFill;}
    public Boolean getAutoIncrement() {return allowAutoIncrement;}
    public Boolean getPrimaryKey() {return allowPrimaryKey;}
    public Boolean getDefault() {return allowDefault;}
    public Object getProperty() {return property;}
    public Integer getMaxLength() {return maxLength;}

    public DataType(@NotNull String typeName, @NotNull Boolean unsigned, @NotNull Boolean zeroFill, @NotNull Boolean autoIncrement, @NotNull Boolean primaryKey, @NotNull Boolean defaultKey, @Nullable Object property, Integer maxLength) {
        this.typeName = typeName;
        this.allowUnsigned= unsigned;
        this.allowZeroFill = zeroFill;
        this.allowAutoIncrement = autoIncrement;
        this.allowPrimaryKey = primaryKey;
        this.allowDefault = defaultKey;
        this.property = property;
        this.maxLength = maxLength;
    }
    public DataType(@NotNull String typeName, @NotNull Boolean unsigned, @NotNull Boolean zeroFill, @NotNull Boolean autoIncrement, @NotNull Boolean primaryKey, @NotNull Boolean defaultKey) {
        this.typeName = typeName;
        this.allowUnsigned= unsigned;
        this.allowZeroFill = zeroFill;
        this.allowAutoIncrement = autoIncrement;
        this.allowPrimaryKey = primaryKey;
        this.allowDefault = defaultKey;
    }

    @Override
    public String toString() {
        return "DataType{typename=\"" + getTypeName() + "\",unsigned=\"" + getUnsigned() + "\",zerofill=\"" + getZeroFill() + "\",autoincrement=\"" + getAutoIncrement() + "\",primarykey=\"" + getPrimaryKey() + "\",default=\"" + getDefault() + "\",property=" + obj2Str(getProperty()) + ",maxLength=" + obj2Str(getMaxLength()) + "}";
    }
}
