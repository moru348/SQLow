package me.moru3.sqlow;

import me.moru3.sqlow.exceptions.NoPropertyException;
import me.moru3.sqlow.utils.ObjConv;
import org.jetbrains.annotations.NotNull;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

public class Delete extends ObjConv {
    private final String tableName;
    private final Where where;
    private Set<String> conditions = new HashSet<String>();

    public Delete(@NotNull String tableName, @NotNull Where where) {
        this.tableName = tableName;
        this.where = where;
    }

    public String build() {
        StringBuilder result = new StringBuilder();
        result.append("DELETE FROM ").append(tableName).append(where.build());
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
