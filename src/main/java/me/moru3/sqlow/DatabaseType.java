package me.moru3;

public enum DatabaseType {
    MYSQL(3306),
    MARIADB(3306),
    ;

    private final int port;
    DatabaseType(int i) {
        port = i;
    }

    public int getPort() {return port;}
}
