package me.moru3;

import me.moru3.utils.Obj2Str;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

public class DataType<T> {
    public final static DataType<?> TINYINT = new DataType<>("TINYINT", true, true, true, true, true, Object::toString, Byte.class);
    public final static DataType<?> SMALLINT = new DataType<>("SHORT", true, true, true, true, true, Object::toString, Short.class);
    public final static DataType<?> MEDIUMINT = new DataType<>("MEDIUMINT", true, true, true, true, true, Object::toString, Integer.class);
    public final static DataType<?> INT = new DataType<>("INT", true, true, true, true, true, Object::toString, Integer.class);
    public final static DataType<?> BIGINT = new DataType<>("BIGINT", true, true, true, true, true, Object::toString, Long.class);
    public final static DataType<?> FLOAT = new DataType<>("FLOAT", true, true, true, true, true, Object::toString, Float.class);
    public final static DataType<?> DOUBLE = new DataType<>("DOUBLE", true, true, true, true, true, Object::toString, Double.class);
    public final static DataType<?> BOOLEAN = new DataType<>("boolean", false, false, false, false, true, Object::toString, Boolean.class);
    public final static DataType<?> DATETIME = new DataType<>("DATETIME", false, false, false, true, true, Obj2Str::convDateTime, Date.class);
    public final static DataType<?> DATE = new DataType<>("DATE", false, false, false, true, true, Obj2Str::convDate, Date.class);
    public final static DataType<?> TIME = new DataType<>("TIME", false, false, false, true, true, Obj2Str::convTime, Date.class);
    public final static DataType<?> VARCHAR = new DataType<>("VARCHAR", false, false, false, false, true, 255, 65535, Obj2Str::convString, String.class);
    public final static DataType<?> TEXT = new DataType<>("TEXT", false, false, false, false, false, 255, 14090025, Obj2Str::convString, String.class);

    public static Set<DataType<?>> dataTypes = new HashSet<>();

    @NotNull
    private final String typeName;

    private final boolean allowUnsigned;
    private final boolean allowZeroFill;
    private final boolean allowAutoIncrement;
    private final boolean allowPrimaryKey;
    private final boolean allowDefault;
    private final Function<Object, String> convM;
    private int maxLength;
    private final Class<T> typeClass;


    @Nullable
    private Object property;

    @NotNull
    public String getTypeName() {return typeName;}

    public boolean getUnsigned() {return allowUnsigned;}

    public boolean getZeroFill() {return allowZeroFill;}

    public boolean getAutoIncrement() {return allowAutoIncrement;}

    public boolean getPrimaryKey() {return allowPrimaryKey;}

    public boolean getDefault() {return allowDefault;}

    public @Nullable Object getProperty() {return property;}

    public int getMaxLength() {return maxLength;}

    public Function<Object, String> getConvM() {return convM;}

    public Class<T> toClass() {return typeClass;}

    private DataType(@NotNull String typeName, boolean unsigned, boolean zeroFill, boolean autoIncrement, boolean primaryKey, boolean defaultKey, @Nullable Object property, int maxLength, @NotNull Function<Object, String> convM, @NotNull Class<T> typeClass) {
        this.typeName = typeName;
        this.allowUnsigned= unsigned;
        this.allowZeroFill = zeroFill;
        this.allowAutoIncrement = autoIncrement;
        this.allowPrimaryKey = primaryKey;
        this.allowDefault = defaultKey;
        this.property = property;
        this.maxLength = maxLength;
        this.convM = convM;
        this.typeClass = typeClass;
    }

    private DataType(@NotNull String typeName, boolean unsigned, boolean zeroFill, boolean autoIncrement, boolean primaryKey, boolean defaultKey, @NotNull Function<Object, String> convM, @NotNull Class<T> typeClass) {
        this.typeName = typeName;
        this.allowUnsigned= unsigned;
        this.allowZeroFill = zeroFill;
        this.allowAutoIncrement = autoIncrement;
        this.allowPrimaryKey = primaryKey;
        this.allowDefault = defaultKey;
        this.convM = convM;
        this.typeClass = typeClass;
    }
}
