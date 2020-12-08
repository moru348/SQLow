package me.moru3;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

public class SQLow {
    private static Connection connection;
    private static String password;
    private static String user;
    private static String url;

    public static Connection getConnection() throws SQLException {
        if(connection==null) throw new NullPointerException("getConnection() was called with no connection established.");
        if(connection.isClosed() || connection.isValid(5000)) {
            connection = reconnect();
        }
        return connection;
    }

    /**
     * Reconnect to the database.
     * @return Connection
     * @throws SQLException SQL Error
     */
    public static Connection reconnect() throws SQLException, NullPointerException {
        if(connection==null) throw new NullPointerException("reconnect() was called with no connection established.");
        if(user==null||password==null) {
            return connect(url);
        }

        return connect(url, user, password);
    }

    /**
     * This is a function to prepare Connection.
     * @param dbtype DatabaseType The database to use.
     * @param host String Database host.
     * @param userName String SQL username.
     * @param password String SQL password.
     * @param databaseName String Database name.
     * @param properties TreeMap<String, String> Properties when connecting.
     * @return Connection
     * @throws SQLException SQL Error
     */
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

    /**
     * This is a function to prepare Connection.
     * @param dbtype DatabaseType The database to use.
     * @param host String Database host.
     * @param port int Database port.
     * @param userName String SQL username.
     * @param password String SQL password.
     * @param databaseName String Database name.
     * @param properties TreeMap<String, String> Properties when connecting.
     * @return Connection
     * @throws SQLException SQL Error
     */
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

    /**
     * This is a function to prepare Connection.
     * @param url String JDBC URL.
     * @param userName String SQL user name.
     * @param password String SQL password.
     * @return Connection
     * @throws SQLException SQL Error
     */
    public static Connection connect(String url, String userName, String password) throws SQLException {
        SQLow.password = password;
        user = userName;
        SQLow.url = url;
        connection = DriverManager.getConnection(url, userName, password);
        return connection;
    }

    /**
     * This is a connect prepared for SQ Low.
     * @param url String The JDBC URL.
     * @return Connection
     * @throws SQLException SQL Error
     */
    public static Connection connect(String url) throws SQLException {
        connection = DriverManager.getConnection(url);
        return connection;
    }
}
