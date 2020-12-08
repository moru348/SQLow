package me.moru3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

public class SQLow {
    private static Connection connection;
    private static String password;
    private static String user;
    private static String url;

    public static Connection getConnection() throws SQLException {
        if(connection.isClosed() || connection.isValid(5000)) {
            connection = reconnect();
        }
        return connection;
    }

    public static Connection reconnect() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public static Connection connect(DatabaseType dbtype, String host, String userName, String password, String databaseName, TreeMap<String, String> properties) throws SQLException {
        StringBuilder url = new StringBuilder();
        url.append("jdbc:").append(dbtype.toString().toLowerCase()).append("://").append(host).append(":").append(dbtype.getPort()).append("/").append(databaseName);
        ArrayList<String> property = new ArrayList<>();
        properties.forEach((key, value) -> {
            property.add(key + "=" + value);
        });
        if(property.size()>0) {
            StringJoiner join = new StringJoiner("&");
            property.forEach(join::add);
            url.append("?").append(join);
        }
        return connect(new String(url), userName, password);
    }

    public static Connection connect(DatabaseType dbtype, String host, int port, String userName, String password, String databaseName, TreeMap<String, String> properties) throws SQLException {
        StringBuilder url = new StringBuilder();
        url.append("jdbc:").append(dbtype.toString().toLowerCase()).append("://").append(host).append(":").append(port).append("/").append(databaseName);
        ArrayList<String> property = new ArrayList<>();
        properties.forEach((key, value) -> {
            property.add(key + "=" + value);
        });
        if(property.size()>0) {
            StringJoiner join = new StringJoiner("&");
            property.forEach(join::add);
            url.append("?").append(join);
        }
        return connect(new String(url), userName, password);
    }

    public static Connection connect(String url, String userName, String password) throws SQLException {
        SQLow.password = password;
        user = userName;
        SQLow.url = url;
        connection = DriverManager.getConnection(url, userName, password);
        return connection;
    }
}
