package org.example;
import java.util.HashMap;
import java.util.Map;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Szablon połęczenia z bazą danych
// Połączania prywatne -> najperw connect pozniej getConnection

// Klasa do zarządzania połączeniami z bazą danych
public class DatabaseConnection {

    // Mapa przechowująca połączenia z bazą danych
    static private final Map<String, Connection> connections = new HashMap<>();

    // Metoda do pobierania połączenia z domyślną nazwą
    static public Connection getConnection() {
        return getConnection("");
    }

    // Metoda do pobierania połączenia o podanej nazwie
    static public Connection getConnection(String name) {
        return connections.get(name);
    }

    // Metoda do nawiązywania połączenia z bazą danych o podanej ścieżce
    static public void connect(String filePath) {
        connect(filePath, "");
    }

    // Metoda do nawiązywania połączenia z bazą danych o podanej ścieżce i nazwie
    static public void connect(String filePath, String connectionName){
        try {
            // Nawiązanie połączenia z bazą danych
            Connection connection = DriverManager.getConnection("jdbc:sqlite:" + filePath);
            // Dodanie połączenia do mapy
            connections.put(connectionName, connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Metoda do zamykania domyślnego połączenia
    static public void disconnect() {
        disconnect("");
    }

    // Metoda do zamykania połączenia o podanej nazwie
    static public void disconnect(String connectionName){
        try {
            // Pobranie połączenia z mapy
            Connection connection = connections.get(connectionName);
            // Zamknięcie połączenia
            connection.close();
            // Usunięcie połączenia z mapy
            connections.remove(connectionName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}