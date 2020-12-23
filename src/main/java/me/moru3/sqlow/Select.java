package me.moru3.sqlow;

import me.moru3.sqlow.exceptions.NoPropertyException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Select {

    private final String tableName;
    private final Where where;
    private OrderType order;
    private String orderKey;
    private int limit;
    private int offset;

    public Select(String tableName, Where where) {
        this.tableName = tableName;
        this.where = where;
    }

    public Select(String tableName, Where where, int limit, int offset) {
        this.tableName = tableName;
        this.where = where;
        this.limit = limit;
        this.offset = offset;
    }

    public Select(String tableName, Where where, OrderType order, String orderKey) {
        this.tableName = tableName;
        this.where = where;
        this.order = order;
        this.orderKey = orderKey;
    }

    public Select(String tableName, Where where, OrderType order, String orderKey, int limit, int offset) {
        this.tableName = tableName;
        this.where = where;
        this.order = order;
        this.limit = limit;
        this.offset = offset;
        this.orderKey = orderKey;
    }

    public String build() {
        StringBuilder result = new StringBuilder();
        result.append("SELECT * FROM ").append(tableName)
            .append(where.build())
                .append(limit < 1 ? "" : " LIMIT " + limit)
                .append(limit < 1 ? "" : " OFFSET " + offset)
                .append(orderKey==null ? "" : " ORDER BY " + orderKey + order);
        return new String(result);
    }

    public ResultSet send() throws IllegalArgumentException, NoPropertyException, SQLException {
        if(SQLow.getConnection()==null) throw new NoPropertyException("No connection has been created with SQ Low (Connection).");
        PreparedStatement ps = SQLow.getConnection().prepareStatement(build());
        return ps.executeQuery();
    }

    public Boolean exists() throws IllegalArgumentException, NoPropertyException, SQLException {
        if(SQLow.getConnection()==null) throw new NoPropertyException("No connection has been created with SQ Low (Connection).");
        ResultSet rs = send();
        return rs.next();
    }

    @Override
    public String toString() {
        return build();
    }
}
