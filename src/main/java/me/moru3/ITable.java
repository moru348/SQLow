package me.moru3;

import me.moru3.exceptions.NoPropertyException;

public interface ITable {

    /**
     * Convert Table to SQL syntax.
     * @return converted SQL syntax.
     * @throws NoPropertyException If the property is not set for the Data Type that requires the property
     */
    String build() throws NoPropertyException;
}
