package org.example.databasecreator;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Creator {

    public static void main(String[] args) {
        String url = "jdbc:sqlite:usereeg.db"; // URL bazy danych
        Creator creator = new Creator(); // Tworzenie nowego obiektu Creator
        creator.create(url); // Tworzenie bazy danych

    }
    // Metoda do tworzenia bazy danych
    public void create(String url){
        // SQL do tworzenia tabeli
        String createTableSQL = "CREATE TABLE IF NOT EXISTS user_eeg ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "username TEXT NOT NULL,"
                + "electrode_number INTEGER NOT NULL,"
                + "image TEXT NOT NULL"
                + ");";

        try (Connection conn = DriverManager.getConnection(url); // Nawiązanie połączenia z bazą danych
             Statement stmt = conn.createStatement()) { // Tworzenie obiektu Statement
            stmt.execute(createTableSQL); // Wykonanie SQL
            System.out.println("Ok");
        } catch (SQLException e) {
            System.out.println(e.getMessage()); // Wyświetlanie błędów SQL
        }
    }

    // Metoda do usuwania bazy danych
    public void delete(String url) {
        String filepath = url.substring(url.indexOf("\\")); // Ścieżka do pliku bazy danych
        File dbFile = new File(filepath); // Tworzenie obiektu File
        if (dbFile.exists()) { // Sprawdzanie, czy plik istnieje
            if (!dbFile.delete()){ // Usuwanie pliku
                System.out.println("Error during delete database"); // Wyświetlanie błędu podczas usuwania
            }
        }else{
            System.out.println("Error database dosent exist"); // Wyświetlanie błędu, gdy baza danych nie istnieje
        }
    }
}
