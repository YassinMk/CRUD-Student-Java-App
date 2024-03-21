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
            createTables();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("Error initializing database connection", e);
        }
    }

    private static void createTables() throws SQLException {
        Statement stmt = connection.createStatement();

        String sqlStudent = "CREATE TABLE IF NOT EXISTS Student (" +
                "id INT PRIMARY KEY AUTO_INCREMENT, " +
                "name VARCHAR(50), " +
                "age INT, " +
                "ville_id INT)";
        stmt.execute(sqlStudent);

        String sqlVille = "CREATE TABLE IF NOT EXISTS Ville (" +
                "id INT PRIMARY KEY AUTO_INCREMENT, " +
                "name VARCHAR(50))";
        stmt.execute(sqlVille);
    }
    public static Connection getConnection() {
        return connection;
    }

}