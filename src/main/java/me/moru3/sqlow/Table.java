package me.moru3.sqlow;

import me.moru3.sqlow.exceptions.NoPropertyException;
import org.jetbrains.annotations.NotNull;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringJoiner;

public class Table implements ITable {
    private ArrayList<Column> columns = new ArrayList<>();
    private final String name;

    public Table(@NotNull String name,@NotNull ArrayList<Column> columns) {
        this.columns = columns;
        this.name = name;
    }

    public Table(@NotNull String name) {
        this.name = name;
    }

    public Table addColumn(@NotNull Column column) {
        columns.add(column);
        return this;
    }

    public String build(boolean force) throws IllegalArgumentException, NoPropertyException {
        StringBuilder result = new StringBuilder();
        result.append("CREATE TABLE ").append(force ? "" + name : "IF NOT EXISTS " + name).append(" (");
        Column[] primaryKeys = columns.stream().filter(Column::isPrimaryKey).toArray(Column[]::new);
        Column[] autoIncrements = columns.stream().filter(Column::isAutoIncrement).toArray(Column[]::new);
        Column[] uniqueIndexes = columns.stream().filter(Column::isUniqueindex).toArray(Column[]::new);
        if(autoIncrements.length>1) throw new IllegalArgumentException("The maximum number of AI that can be set is 1.");
        if(SQLow.getDatabaseType()==DatabaseType.SQLITE && primaryKeys.length>1 && autoIncrements.length>=1) throw new IllegalArgumentException("If you set AI, PK is limited to one.");
        StringJoiner columnList = new StringJoiner(", ");
        StringJoiner primaryKeyList = new StringJoiner(", ");
        StringJoiner uniqueIndexList = new StringJoiner(", ");
        if(SQLow.getDatabaseType()==DatabaseType.SQLITE&&autoIncrements.length<=0) primaryKeys = Arrays.stream(primaryKeys).map(i -> i.setPrimaryKey(false)).toArray(Column[]::new);
        columns.stream().map(Column::build).forEach(columnList::add);
        Arrays.stream(primaryKeys).map(Column::getName).forEach(primaryKeyList::add);
        if(SQLow.getDatabaseType()==DatabaseType.SQLITE&&autoIncrements.length>0) primaryKeys = new Column[]{};
        if(primaryKeys.length>=1) columnList.add(" PRIMARY KEY (" + primaryKeyList + ")");
        if(uniqueIndexes.length>=1) {
            if(SQLow.getDatabaseType()!=DatabaseType.SQLITE) {
                columnList.add(" UNIQUE " + name + "_index (" + uniqueIndexList + ")");
            } else {
                columnList.add(" UNIQUE(" + uniqueIndexList + ")");
            }
        }
        result.append(columnList).append(")");
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
