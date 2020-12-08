package me.moru3;

import org.jetbrains.annotations.NotNull;

public interface IColumn {

    /**
     * Convert Column to SQL syntax.
     * @return converted SQL syntax.
     */
    String build();

    String getName();

    /**
     * Whether to make it Not Null
     * @return column
     */
    Column setNotNull(boolean bool);

    boolean isNotNull();

    //Column setUnsigned(boolean bool);

    //boolean isUnsigned();


    /**
     * @param bool Whether to make it zerofill
     * @return column
     */
    Column setZeroFill(boolean bool);

    boolean isZeroFill();

    /**
     * @param bool Whether to make it auto increment
     * @return column
     */
    Column setAutoIncrement(boolean bool);

    boolean isAutoIncrement();

    /**
     * @param bool Whether to make it primary key
     * @return column
     */
    Column setPrimaryKey(boolean bool);

    boolean isPrimaryKey();

    /**
     * @param obj If you need a default value, set it.
     * @return column
     */
    Column setDefaultValue(@NotNull Object obj);

    /**
     * @param obj Set the property if needed.
     * @return column
     */
    Column setProperty(@NotNull Object obj);
}

