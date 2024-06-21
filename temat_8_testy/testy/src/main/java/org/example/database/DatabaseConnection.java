package org.example.database;
import java.util.HashMap;
import java.util.Map;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    // Mapa przechowująca połączenia do bazy danych
    static private final Map<String, Connection> connections = new HashMap<>();

    // Metoda zwracająca domyślne połączenie do bazy danych
    static public Connection getConnection() {
        return getConnection("");
    }

    // Metoda zwracająca połączenie do bazy danych o podanej nazwie
    static public Connection getConnection(String name) {
        return connections.get(name);
    }

    // Metoda łącząca z bazą danych o podanej ścieżce pliku
    static public void connect(String filePath) {
        connect(filePath, "");
    }

    // Metoda łącząca z bazą danych o podanej ścieżce pliku i nazwie połączenia
    static public void connect(String filePath, String connectionName){
        try {
            // Tworzenie nowego połączenia do bazy danych
            Connection connection = DriverManager.getConnection("jdbc:sqlite:" + filePath);
            // Dodawanie nowego połączenia do mapy
            connections.put(connectionName, connection);
        } catch (SQLException e) {
            // Wyrzucenie wyjątku w przypadku błędu SQL
            throw new RuntimeException(e);
        }
    }

    // Metoda rozłączająca domyślne połączenie do bazy danych
    static public void disconnect() {
        disconnect("");
    }

    // Metoda rozłączająca połączenie do bazy danych o podanej nazwie
    static public void disconnect(String connectionName){
        try {
            // Pobranie połączenia do bazy danych o podanej nazwie
            Connection connection = connections.get(connectionName);
            // Zamknięcie połączenia
            connection.close();
            // Usunięcie połączenia z mapy
            connections.remove(connectionName);
        } catch (SQLException e) {
            // Wyrzucenie wyjątku w przypadku błędu SQL
            throw new RuntimeException(e);
        }
    }
}
