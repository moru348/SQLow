package me.moru3.sqlow;

import me.moru3.sqlow.exceptions.NoPropertyException;
import me.moru3.sqlow.utils.ObjConv;
import org.jetbrains.annotations.NotNull;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

public class Update {
    private final String tableName;
    private final Where where;
    private final Map<String, String> values = new HashMap<>();
    public Update(@NotNull String tableName, Where where) {
        this.tableName = tableName;
        this.where = where;
    }

    public Update addValue(DataType<?> type, String key, Object value) {
        values.put(key, type.getConvM().apply(value));
        return this;
    }

    public Update addValue(String key, Object value) {
        if(value instanceof Select) {
            values.put(key, ((Select) value).build());
            return this;
        }
        values.put(key, ObjConv.ObjToType(value).getConvM().apply(value));
        return this;
    }

    public String build() {
        StringBuilder result = new StringBuilder();
        result.append("UPDATE ").append(tableName).append(" SET ");
        StringJoiner values = new StringJoiner(",");
        this.values.forEach((key, value) -> values.add(key + " = " + value));
        result.append(values);
        result.append(where.build());
        return new String(result);
    }

    public void send() throws IllegalArgumentException, NoPropertyException, SQLException {
        if(SQLow.getConnection()==null) throw new NoPropertyException("No connection has been created with SQ Low (Connection).");
        PreparedStatement ps = SQLow.getConnection().prepareStatement(build());
        ps.executeUpdate();
        ps.close();
    }

    @Override
    public String toString() {
        return build();
    }
}
