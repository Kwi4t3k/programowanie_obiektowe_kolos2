package org.example;

import at.favre.lib.crypto.bcrypt.BCrypt;
import javax.naming.AuthenticationException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// Klasa dla konta trzymanego w bazie danych
public class Account {
    protected final int id;
    protected final String username;

    // Konstruktor klasy Account
    public Account(int id, String username) {
        this.id = id;
        this.username = username;
    }

    // Getter dla pola id
    public int getId() {
        return id;
    }

    // Getter dla pola username
    public String getUsername() {
        return username;
    }

    // Metoda toString dla klasy Account
    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", username='" + username + '\'' +
                '}';
    }

    // Klasa wewnętrzna Persistence do obsługi operacji na bazie danych
    public static class Persistence {
        // Metoda do inicjalizacji tabeli w bazie danych
        public static void init() {
            try {
                // SQL do stworzenia tabeli jeśli nie istnieje
                String createSQLTable = "CREATE TABLE IF NOT EXISTS account( " +
                        "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                        "username TEXT NOT NULL," +
                        "password TEXT NOT NULL)";
                PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(createSQLTable);
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        // Metoda do rejestracji nowego użytkownika
        public static int register(String username, String password) {
            // Hashowanie hasła
            String hashedPassword =  BCrypt.withDefaults().hashToString(12, password.toCharArray());
            try {
                // SQL do wstawienia nowego użytkownika do bazy danych
                String insertSQL = "INSERT INTO account(username, password) VALUES (?, ?)";
                PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(insertSQL);

                statement.setString(1, username);
                statement.setString(2, hashedPassword);
                statement.executeUpdate();

                ResultSet resultSet = statement.getGeneratedKeys();
                if(resultSet.next())
                    return resultSet.getInt(1);
                else throw new SQLException();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        // Metoda do autentykacji użytkownika
        public static Account authenticate(String username, String password) throws AuthenticationException {
            try {
                // SQL do pobrania użytkownika o podanej nazwie użytkownika
                String sql = "SELECT id, username, password FROM account WHERE username = ?";

                PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql);
                statement.setString(1, username);

                if (!statement.execute()) throw new RuntimeException("SELECT failed");

                ResultSet result = statement.getResultSet();

                if (!result.next()) {
                    throw new AuthenticationException("No such user");
                }
                String hashedPassword = result.getString(3);
                // Weryfikacja hasła
                boolean okay = BCrypt.verifyer().verify(password.toCharArray(), hashedPassword.toCharArray()).verified;

                if (!okay) {
                    throw new AuthenticationException("Wrong password");
                }

                // Zwrócenie obiektu Account dla zautentykowanego użytkownika
                return new Account(
                        result.getInt(1),
                        result.getString(2)
                );
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
