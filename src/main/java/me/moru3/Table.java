package me.moru3;

import me.moru3.exceptions.NoPropertyException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Table {
    private List<Column> columns;
    private String name;
    public Table(String name, List<Column> columns) {
        this.columns = columns;
        this.name = name;
    }
    public String build(boolean force) {
        ArrayList<Exception> ex = new ArrayList<>();
        StringBuilder result = new StringBuilder();
        result.append("TABLE CREATE ").append(force ? "":"IF NOT EXISTS ").append(name).append(" (");
        Column[] primarykeys = columns.stream().filter(Column::isPrimaryKey).toArray(Column[]::new);
        if(primarykeys.length>1) throw new IllegalArgumentException("Only 1 Primary Key can be set for each table.");
        columns.forEach((table) -> {
            try {
                table.build();
            } catch (NoPropertyException e) {
                Objects.requireNonNull(ex).add(e);
            }
        });
        return new String(result);
    }
}
