package me.moru3.sqlow;

public enum DatabaseType {
    MYSQL(3306),
    MARIADB(3306),
    SQLITE(-1),
    ;

    private final int port;
    DatabaseType(int i) {
        port = i;
    }

    public int getPort() {return port;}
}
