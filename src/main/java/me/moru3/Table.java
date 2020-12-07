package me.moru3;

import me.moru3.exceptions.NoPropertyException;

import java.util.List;

public class Table {
    private final List<Column> columns;
    private final String name;

    public Table(String name, List<Column> columns) {
        this.columns = columns;
        this.name = name;
    }

    public String build(boolean force) throws IllegalArgumentException, NoPropertyException {
        StringBuilder result = new StringBuilder();
        result.append("TABLE CREATE ").append(force ? "" + name:"IF NOT EXISTS " + name).append(name).append(" (");
        Column[] primaryKeys = columns.stream().filter(Column::isPrimaryKey).toArray(Column[]::new);
        if(primaryKeys.length>1) throw new IllegalArgumentException("Only 1 Primary Key can be set for each table.");
        Column primaryKey = primaryKeys[0];
        for (Column table : columns) {
            result.append(table.build()).append(columns.indexOf(table) == columns.size() && primaryKey==null ? "" : ",");
        }
        if(primaryKey!=null) result.append(" PRIMARY KEY (").append(primaryKey.getName()).append(")");

        return new String(result);
    }
}
