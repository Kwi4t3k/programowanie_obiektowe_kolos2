package org.example.music;

import org.example.database.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

// Klasa reprezentująca piosenkę
public record Song(String artist, String title, int timeInSeconds) {
    // Klasa statyczna do obsługi operacji na piosenkach w bazie danych
    public static class Persistence{
        // Metoda do odczytu piosenki z bazy danych na podstawie indeksu
        public static Optional<Song> read(int index) throws SQLException {
            // Zapytanie SQL do odczytu piosenki
            String query = "SELECT * FROM song WHERE id = ?;";

            // Przygotowanie zapytania SQL
            PreparedStatement preparedStatement = DatabaseConnection.getConnection("song").prepareStatement(query);

            // Ustawienie indeksu piosenki
            preparedStatement.setInt(1, index);
            // Wykonanie zapytania SQL
            preparedStatement.execute();

            // Pobranie wyników zapytania SQL
            ResultSet resultSet = preparedStatement.getResultSet();
            // Inicjalizacja pustego obiektu Optional<Song>
            Optional<Song> song = Optional.empty();

            // Jeśli istnieje wynik zapytania SQL
            if(resultSet.next()){
                // Pobranie artysty, tytułu i czasu trwania piosenki
                String artist = resultSet.getString("artist");
                String title = resultSet.getString("title");
                int timeInSeconds = resultSet.getInt("length");
                // Utworzenie nowego obiektu Song i przypisanie go do obiektu Optional<Song>
                song = Optional.of(new Song(artist, title, timeInSeconds));
            }
            // Zwrócenie obiektu Optional<Song>
            return song;
        }
    }
}
