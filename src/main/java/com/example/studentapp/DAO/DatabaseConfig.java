package com.example.studentapp.DAO;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DatabaseConfig{
    private String url;
    private String user;
    private String password;
    private boolean createIfNotExist;

    public DatabaseConfig() {
        try (InputStream input = new FileInputStream("src/main/resources/database.properties")) {
            Properties prop = new Properties();
            prop.load(input);

            this.url = prop.getProperty("db.url");
            this.user = prop.getProperty("db.user");
            this.password = prop.getProperty("db.password");
            this.createIfNotExist = Boolean.parseBoolean(prop.getProperty("db.create_if_not_exist"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void connectAndCreateIfNotExist() {
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            if (conn != null && createIfNotExist) {
                String dbName = url.substring(url.lastIndexOf("/") + 1);
                Statement stmt = conn.createStatement();
                stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + dbName);
            }
        } catch (SQLException ex) {
            System.out.println("An error occurred while connecting to the database");
            ex.printStackTrace();
        }
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
}