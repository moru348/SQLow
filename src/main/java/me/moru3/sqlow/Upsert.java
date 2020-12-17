package me.moru3.sqlow;

import me.moru3.sqlow.exceptions.NoPropertyException;
import me.moru3.sqlow.utils.ObjConv;
import org.jetbrains.annotations.NotNull;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

public class Upsert {
    private final String tableName;
    private final List<String> keys = new ArrayList<>();
    private final Map<String, String> values = new HashMap<>();
    public Upsert(@NotNull String tableName) {
        this.tableName = tableName;
    }

    /**
     * It is for SQLite and does not need to be used with other SQL.
     * @param key Primary key or unique index.
     * @return Upsert.
     */
    public Upsert addKey(String key) {
        keys.add(key);
        return this;
    }

    public Upsert addValue(DataType<?> type, String key, Object value) {
        values.put(key, type.getConvM().apply(value));
        return this;
    }

    public Upsert addValue(String key, Object value) {
        if(value instanceof Select) {
            values.put(key, ((Select) value).build());
            return this;
        }
        values.put(key, ObjConv.ObjToType(value).getConvM().apply(value));
        return this;
    }

    /**
     * Converts UPSERT to SQL syntax and returns it.
     * @return String
     */
    public String build() {
        StringBuilder result = new StringBuilder();
        result.append(new Insert(tableName, values).build(true));
        StringJoiner updateJoiner = new StringJoiner(",");
        StringJoiner keyJoiner = new StringJoiner(",");
        keys.forEach(keyJoiner::add);
        result.append(SQLow.getDatabaseType()!=DatabaseType.SQLITE?" ON DUPLICATE KEY UPDATE ":") DO UPDATE SET ");
        this.values.forEach((key, value) -> updateJoiner.add(key + "="  + value));
        result.append(updateJoiner);
        return new String(result);
    }

    public void send() throws IllegalArgumentException, NoPropertyException, SQLException {
        if(SQLow.getConnection()==null) throw new NoPropertyException("No connection has been created with SQLow (Connection).");
        PreparedStatement ps = SQLow.getConnection().prepareStatement(build());
        ps.executeUpdate();
        ps.close();
    }

    @Override
    public String toString() {
        return build();
    }
}
