package me.moru3;

import me.moru3.exceptions.NoPropertyException;
import org.jetbrains.annotations.NotNull;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;

public class Table implements ITable {
    private final Column[] columns;
    private final String name;

    public Table(@NotNull String name,@NotNull Column[] columns) {
        this.columns = columns;
        this.name = name;
    }

    public String build(boolean force) throws IllegalArgumentException, NoPropertyException {
        StringBuilder result = new StringBuilder();
        result.append("TABLE CREATE ").append(force ? "" + name : "IF NOT EXISTS " + name).append(" (");
        Column[] primaryKeys = Arrays.stream(columns).filter(Column::isPrimaryKey).toArray(Column[]::new);
        if(primaryKeys.length>1) throw new IllegalArgumentException("Only 1 Primary Key can be set for each table.");
        Column primaryKey = primaryKeys[0];
        Arrays.stream(columns).map(Column::build);
        if(primaryKey!=null) result.append(", PRIMARY KEY (").append(primaryKey.getName()).append(")");
        result.append(";");
        return new String(result);
    }

    public void send(boolean force) throws IllegalArgumentException, NoPropertyException, SQLException {
        if(SQLow.connection==null) throw new NoPropertyException("No connection has been created with SQ Low (Connection).");
        PreparedStatement ps = SQLow.connection.prepareStatement(build(force));
        ps.executeUpdate();
    }

    public void send() throws IllegalArgumentException, NoPropertyException, SQLException {
        send(false);
    }
}
