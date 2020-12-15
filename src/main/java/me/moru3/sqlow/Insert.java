package me.moru3.sqlow;

import me.moru3.sqlow.exceptions.NoPropertyException;
import me.moru3.sqlow.utils.ObjConv;
import org.jetbrains.annotations.NotNull;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

public class Insert extends ObjConv {
    private final String tableName;
    private final Map<String, String> values = new HashMap<>();
    public Insert(@NotNull String tableName) {
        this.tableName = tableName;
    }

    public Insert addValue(DataType<?> type, String key, Object value) {
        values.put(key, type.getConvM().apply(value));
        return this;
    }

    public Insert addValue(String key, Object value) {
        if(value instanceof Select) {
            values.put(key, ((Select) value).build());
            return this;
        }
        values.put(key, ObjConv.ObjToType(value).getConvM().apply(value));
        return this;
    }
    public Insert addValue(String key, String value) {
        values.put(key, value);
        return this;
    }

    public String build(boolean force) {
        StringJoiner keyJoiner = new StringJoiner(",");
        StringJoiner valueJoiner = new StringJoiner(",");
        this.values.forEach((key, value) -> {
            keyJoiner.add(key);
            valueJoiner.add(value);
        });
        StringBuilder result = new StringBuilder();
        result.append("INSERT").append(force?"":" IGNORE").append(" INTO ")
                .append(tableName)
                .append(" (").append(keyJoiner).append(")")
                .append(" VALUES (")
                .append(valueJoiner);
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
        return build(false);
    }
}
