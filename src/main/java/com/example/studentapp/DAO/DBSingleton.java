package com.example.studentapp.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBSingleton {
    private static Connection connection;

    static {
        try {
            DatabaseConfig config = new DatabaseConfig();
            Class.forName("com.mysql.cj.jdbc.Driver"); // Use "com.mysql.cj.jdbc.Driver" for MySQL 8 and above
            connection = DriverManager.getConnection(config.getUrl(), config.getUser(), config.getPassword());
            config.connectAndCreateIfNotExist();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("Error initializing database connection", e);
        }
    }

    public static Connection getConnection() {
        return connection;
    }
}