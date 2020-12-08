package me.moru3;

import org.jetbrains.annotations.NotNull;

import java.sql.Connection;

public class SQLow {
    public static Connection connection;

    public SQLow(@NotNull Connection con) {
        connection = con;
    }
}
