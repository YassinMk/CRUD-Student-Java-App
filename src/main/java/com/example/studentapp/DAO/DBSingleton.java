package com.example.studentapp.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBSingleton {
    private static final String URL = "jdbc:mysql://localhost:3306/test-java";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    private static Connection connection;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Use "com.mysql.cj.jdbc.Driver" for MySQL 8 and above
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("Error initializing database connection", e);
        }
    }

    public static Connection getConnection() {
        return connection;
    }
}
