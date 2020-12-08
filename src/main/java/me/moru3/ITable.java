package me.moru3;

import me.moru3.exceptions.NoPropertyException;

import java.sql.SQLException;

public interface ITable {

    /**
     * Convert Table to SQL syntax.
     * @return converted SQL syntax.
     * @throws NoPropertyException If the property is not set for the Data Type that requires the property
     */
    String build(boolean force) throws IllegalArgumentException, NoPropertyException;

    void send(boolean force) throws IllegalArgumentException, NoPropertyException, SQLException;
}
