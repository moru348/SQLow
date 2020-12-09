package me.moru3;

import me.moru3.exceptions.NoPropertyException;
import org.jetbrains.annotations.NotNull;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.StringJoiner;

public class Table implements ITable {
    private final Column[] columns;
    private final String name;

    public Table(@NotNull String name,@NotNull Column[] columns) {
        this.columns = columns;
        this.name = name;
    }

    public String build(boolean force) throws IllegalArgumentException, NoPropertyException {
        StringBuilder result = new StringBuilder();
        result.append("CREATE TABLE ").append(force ? "" + name : "IF NOT EXISTS " + name).append(" (");
        Column[] primaryKeys = Arrays.stream(columns).filter(Column::isPrimaryKey).toArray(Column[]::new);
        if(primaryKeys.length>1) throw new IllegalArgumentException("Only 1 Primary Key can be set for each table.");
        Column primaryKey = primaryKeys[0];
        StringJoiner columnList = new StringJoiner(", ");
        Arrays.stream(columns).map(Column::build).forEach(columnList::add);
        if(primaryKey!=null) columnList.add("PRIMARY KEY (" + primaryKey.getName() + ")");
        result.append(columnList).append(")");
        result.append(";");
        return new String(result);
    }

    public void send(boolean force) throws IllegalArgumentException, NoPropertyException, SQLException {
        if(SQLow.getConnection()==null) throw new NoPropertyException("No connection has been created with SQ Low (Connection).");
        PreparedStatement ps = SQLow.getConnection().prepareStatement(build(force));
        ps.executeUpdate();
        ps.close();
    }
    @Override
    public String toString() {
        try {
            return build(false);
        } catch (NoPropertyException e) {
            e.printStackTrace();
        }
        return null;
    }
}
