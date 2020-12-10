package me.moru3.sqlow;

import me.moru3.sqlow.exceptions.NoPropertyException;
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
        Column[] autoIncrements = Arrays.stream(columns).filter(Column::isAutoIncrement).toArray(Column[]::new);
        if(autoIncrements.length>1) throw new IllegalArgumentException("The maximum number of AI that can be set is 1.");
        if(SQLow.getDatabaseType()==DatabaseType.SQLITE && primaryKeys.length>1 && autoIncrements.length>=1) throw new IllegalArgumentException("If you set AI, PK is limited to one.");
        StringJoiner columnList = new StringJoiner(", ");
        StringJoiner primaryKeyList = new StringJoiner(", ");
        Arrays.stream(columns).map(Column::build).forEach(columnList::add);
        Arrays.stream(primaryKeys).map(i -> "\"" + i.getName() + "\"").forEach(primaryKeyList::add);
        if(SQLow.getDatabaseType()==DatabaseType.SQLITE) primaryKeys = new Column[]{};
        if(primaryKeys.length>=1) columnList.add("PRIMARY KEY (" + primaryKeyList + ")");
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
