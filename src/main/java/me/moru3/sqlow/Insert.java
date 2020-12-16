package me.moru3.sqlow;

import me.moru3.sqlow.exceptions.NoPropertyException;
import me.moru3.sqlow.utils.ObjConv;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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

    public String build(@Nullable IgnoreType ignoreType) {
        if(ignoreType==null) ignoreType=IgnoreType.IGNORE;
        StringJoiner keyJoiner = new StringJoiner(",");
        StringJoiner valueJoiner = new StringJoiner(",");
        this.values.forEach((key, value) -> {
            keyJoiner.add(key);
            valueJoiner.add(value);
        });
        StringBuilder result = new StringBuilder();
        result.append("INSERT").append(ignoreType==IgnoreType.IGNORE?"": SQLow.getDatabaseType()!=DatabaseType.SQLITE?" IGNORE":" OR IGNORE").append(" INTO ")
                .append(tableName)
                .append(" (").append(keyJoiner).append(")")
                .append(" VALUES (")
                .append(valueJoiner)
                .append(")");
        if(ignoreType!=IgnoreType.UPDATE) return new String(result);
        StringJoiner updateJoiner = new StringJoiner(",");
        if(SQLow.getDatabaseType()!=DatabaseType.SQLITE) {
            result.append(" ON DUPLICATE KEY UPDATE ");
            this.values.forEach((key, value) -> updateJoiner.add(key + "="  + value));
            result.append(updateJoiner);
        } else {
            result.append(" ON CONFLICT DO UPDATE SET ");
            this.values.forEach((key, value) -> updateJoiner.add(key + "="  + value));
            result.append(updateJoiner);
        }
        return new String(result);
    }

    public String build() {
        return build(IgnoreType.IGNORE);
    }

    public void send(IgnoreType ignoreType) throws IllegalArgumentException, NoPropertyException, SQLException {
        if(SQLow.getConnection()==null) throw new NoPropertyException("No connection has been created with SQ Low (Connection).");
        PreparedStatement ps = SQLow.getConnection().prepareStatement(build(ignoreType));
        ps.executeUpdate();
        ps.close();
    }

    public void send() throws IllegalArgumentException, NoPropertyException, SQLException {
        if(SQLow.getConnection()==null) throw new NoPropertyException("No connection has been created with SQ Low (Connection).");
        PreparedStatement ps = SQLow.getConnection().prepareStatement(build(IgnoreType.IGNORE));
        ps.executeUpdate();
        ps.close();
    }

    @Override
    public String toString() {
        return build(IgnoreType.IGNORE);
    }
}
