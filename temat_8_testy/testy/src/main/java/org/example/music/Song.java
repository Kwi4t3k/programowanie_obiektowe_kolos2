package org.example.music;

import org.example.database.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public record Song(String artist, String title, int timeInSeconds) {
    public static class Persistence{
        public static Optional<Song> read(int index) throws SQLException {
            String query = "SELECT * FROM song WHERE id = ?;";

            PreparedStatement preparedStatement = DatabaseConnection.getConnection("song").prepareStatement(query);

            preparedStatement.setInt(1, index);
            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getResultSet();
            Optional<Song> song = Optional.empty();

            if(resultSet.next()){
                String artist = resultSet.getString("artist");
                String title = resultSet.getString("title");
                int timeInSeconds = resultSet.getInt("length");
                song = Optional.of(new Song(artist, title, timeInSeconds));
            }
            return song;
        }
    }
}
