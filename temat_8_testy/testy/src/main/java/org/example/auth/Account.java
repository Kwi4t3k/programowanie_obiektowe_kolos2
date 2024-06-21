package org.example.auth;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.example.database.DatabaseConnection;

import javax.naming.AuthenticationException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// Klasa reprezentująca konto użytkownika
public class Account {
    protected final int id; // Unikalne ID użytkownika
    protected final String username; // Nazwa użytkownika

    // Konstruktor klasy Account
    public Account(int id, String username) {
        this.id = id; // Przypisanie wartości do ID użytkownika
        this.username = username; // Przypisanie wartości do nazwy użytkownika
    }

    // Metoda zwracająca ID konta
    public int getId() {
        return id; // Zwraca ID użytkownika
    }

    // Metoda zwracająca nazwę użytkownika
    public String getUsername() {
        return username; // Zwraca nazwę użytkownika
    }

    // Metoda zwracająca reprezentację konta jako ciąg znaków
    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", username='" + username + '\'' +
                '}'; // Zwraca reprezentację konta jako ciąg znaków
    }

    // Klasa statyczna do obsługi operacji na kontach w bazie danych
    public static class Persistence {
        // Metoda do inicjalizacji tabeli kont w bazie danych
        public static void init() {
            try {
                String createSQLTable = "CREATE TABLE IF NOT EXISTS account( " +
                        "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                        "username TEXT NOT NULL," +
                        "password TEXT NOT NULL)";
                PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(createSQLTable);
                statement.executeUpdate(); // Wykonanie zapytania SQL
            } catch (SQLException e) {
                throw new RuntimeException(e); // Obsługa wyjątków SQL
            }
        }

        // Metoda do rejestracji nowego użytkownika
        public static int register(String username, String password) {
            String hashedPassword =  BCrypt.withDefaults().hashToString(12, password.toCharArray()); // Hashowanie hasła
            try {
                String insertSQL = "INSERT INTO account(username, password) VALUES (?, ?)";
                PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(insertSQL);

                statement.setString(1, username); // Ustawienie nazwy użytkownika
                statement.setString(2, hashedPassword); // Ustawienie zahashowanego hasła
                statement.executeUpdate(); // Wykonanie zapytania SQL

                ResultSet resultSet = statement.getGeneratedKeys();
                if(resultSet.next())
                    return resultSet.getInt(1); // Zwraca ID nowo utworzonego użytkownika
                else throw new SQLException();
            } catch (SQLException e) {
                throw new RuntimeException(e); // Obsługa wyjątków SQL
            }
        }

        // Metoda do autentykacji użytkownika
        public static Account authenticate(String username, String password) throws AuthenticationException {
            try {
                String sql = "SELECT id, username, password FROM account WHERE username = ?";

                PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql);
                statement.setString(1, username); // Ustawienie nazwy użytkownika

                if (!statement.execute()) throw new RuntimeException("SELECT failed"); // Wykonanie zapytania SQL

                ResultSet result = statement.getResultSet();

                if (!result.next()) {
                    throw new AuthenticationException("No such user"); // Obsługa wyjątków autentykacji
                }
                String hashedPassword = result.getString(3);
                boolean okay = BCrypt.verifyer().verify(password.toCharArray(), hashedPassword.toCharArray()).verified; // Weryfikacja hasła

                if (!okay) {
                    throw new AuthenticationException("Wrong password"); // Obsługa wyjątków autentykacji
                }

                return new Account(
                        result.getInt(1), // Zwraca ID użytkownika
                        result.getString(2) // Zwraca nazwę użytkownika
                );
            } catch (SQLException e) {
                throw new RuntimeException(e); // Obsługa wyjątków SQL
            }
        }
    }
}
