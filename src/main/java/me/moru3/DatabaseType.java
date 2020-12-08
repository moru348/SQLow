package me.moru3;

public enum DatabaseType {
    MYSQL(3306),
    POSTGRESQL(5432),
    MARIADB(3306),
    ;

    DatabaseType(int i) {

    }
}
