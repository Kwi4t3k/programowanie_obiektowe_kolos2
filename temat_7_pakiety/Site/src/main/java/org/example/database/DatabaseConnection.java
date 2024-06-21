package org.example.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Klasa DatabaseConnection służy do zarządzania połączeniem z bazą danych.
public class DatabaseConnection {
    // Prywatne pole connection przechowuje aktualne połączenie z bazą danych.
    private Connection connection;

    // Metoda getConnection zwraca aktualne połączenie z bazą danych.
    public Connection getConnection() {
        return connection;
    }

    // Metoda connect służy do nawiązywania połączenia z bazą danych.
    public void connect (String path){
        try {
            // Używamy DriverManager do nawiązania połączenia z bazą danych.
            connection = DriverManager.getConnection("jdbc:sqlite:" + path);
            System.out.println("connected");
        } catch (SQLException throwables) {
            // W przypadku wystąpienia wyjątku SQLException, wyświetlamy stos wywołań.
            throwables.printStackTrace();
        }
    }

    // Metoda disconnect służy do zamykania połączenia z bazą danych.
    public void disconnect () {
        try {
            // Zamykamy połączenie z bazą danych.
            connection.close();
            System.out.println("connection closed");
        } catch (SQLException throwables) {
            // W przypadku wystąpienia wyjątku SQLException, wyświetlamy stos wywołań.
            throwables.printStackTrace();
        }
    }
}
