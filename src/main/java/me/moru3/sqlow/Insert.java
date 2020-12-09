package me.moru3;

import me.moru3.exceptions.NoPropertyException;
import me.moru3.utils.ObjConv;
import org.jetbrains.annotations.NotNull;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

public class Insert extends ObjConv {
    private final String tableName;
    private final Map<String, AbstractMap.SimpleEntry<DataType<?>, Object>> values = new HashMap<>();
    public Insert(@NotNull String tableName) {
        this.tableName = tableName;
    }

    public Insert addValue(DataType<?> type, String key, Object value) {
        values.put(key, new AbstractMap.SimpleEntry<>(type, value));
        return this;
    }

    public Insert addValue(String key, Object value) {
        values.put(key, new AbstractMap.SimpleEntry<>(ObjToType(value), value));
        return this;
    }

    public String build(boolean force) {
        List<String> keys = new ArrayList<>();
        Map<DataType<?>, Object> values = new HashMap<>();
        this.values.forEach((key, value) -> {
            keys.add(key);
            values.put(value.getKey(), value.getValue());
        });
        StringBuilder result = new StringBuilder();
        StringJoiner keyJoiner = new StringJoiner(",");
        StringJoiner valueJoiner = new StringJoiner(",");
        values.forEach((key, value) -> {
            valueJoiner.add(key.getConvM().apply(value));
        });
        keys.forEach(keyJoiner::add);
        result.append("INSERT").append(force?"":" IGNORE").append(" INTO ")
                .append(tableName)
                .append(" (").append(keyJoiner).append(")")
                .append(" VALUES (")
                .append(valueJoiner).append(");");
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
