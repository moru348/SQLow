package me.moru3;

import me.moru3.exceptions.NoPropertyException;
import me.moru3.utils.ObjConv;
import org.jetbrains.annotations.NotNull;

import javax.xml.crypto.Data;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

public class Insert extends ObjConv {
    private final String tableName;
    private Map<String, AbstractMap.SimpleEntry<DataType<?>, Object>> values;
    public Insert(@NotNull String tableName, @NotNull Map<String, Object> values) {
        this.tableName = tableName;
        values.forEach((key, value) -> {
            AbstractMap.SimpleEntry<DataType<?>, Object> temp = new AbstractMap.SimpleEntry<>(ObjToType(value), value);
            this.values.put(key, temp);
        });
    }

    public String build() {
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
        result.append("INSERT INTO ")
                .append(tableName)
                .append(" (").append(keyJoiner).append(")")
                .append(" VALUES (")
                .append(valueJoiner).append(");");
        return new String(result);
    }

    public void send() throws IllegalArgumentException, NoPropertyException, SQLException {
        if(SQLow.getConnection()==null) throw new NoPropertyException("No connection has been created with SQ Low (Connection).");
        PreparedStatement ps = SQLow.getConnection().prepareStatement(build());
        ps.executeUpdate();
    }
}
