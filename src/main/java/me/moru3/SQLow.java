package me.moru3;

import com.sun.istack.internal.NotNull;

import java.sql.Connection;

public class SQLow {
    public static Connection connection;

    public SQLow(@NotNull Connection con) {
        connection = con;
    }
}
