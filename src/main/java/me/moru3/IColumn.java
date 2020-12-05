package me.moru3;

import com.sun.istack.internal.NotNull;
import me.moru3.exceptions.NoPropertyException;

public interface IColumn {

    /**
     * Convert Column to SQL syntax.
     * @return converted SQL syntax.
     * @throws NoPropertyException If the property is not set for the Data Type that requires the property
     */
    String build() throws NoPropertyException;

    /**
     * Whether to make it Not Null
     * @return column
     */
    Column setNotNull(Boolean bool);

    /**
     * @param bool Whether to make it unsigned
     * @return column
     */
    Column setUnsigned(Boolean bool);

    /**
     * @param bool Whether to make it zerofill
     * @return column
     */
    Column setZeroFill(Boolean bool);

    /**
     * @param bool Whether to make it auto increment
     * @return column
     */
    Column setAutoIncrement(Boolean bool);

    /**
     * @param bool Whether to make it primary key
     * @return column
     */
    Column setPrimaryKey(Boolean bool);

    /**
     * @param obj If you need a default value, set it.
     * @return column
     */
    Column setDefault(@NotNull Object obj);

    /**
     * @param obj Set the property if needed.
     * @return column
     */
    Column setProperty(@NotNull Object obj);
}

