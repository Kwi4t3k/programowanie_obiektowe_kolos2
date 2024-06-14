package org.example.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void connect (String path){
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + path);
            System.out.println("connected");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void disconnect () {
        try {
            connection.close();
            System.out.println("connection closed");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
