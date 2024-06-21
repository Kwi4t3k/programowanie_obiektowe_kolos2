package org.example.music;

import org.example.auth.Account;
import org.example.database.DatabaseConnection;

import javax.naming.AuthenticationException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

// Importowanie metody sprawdzającej czy piosenka jest już w posiadaniu użytkownika
import static org.example.music.ListenerAccount.Persistence.hasSong;

// Klasa ListenerAccount dziedziczy po klasie Account
public class ListenerAccount extends Account {
    // Konstruktor klasy ListenerAccount
    public ListenerAccount(int id, String name) {
        super(id, name);
    }

//    public Playlist createPlaylist(List<Integer> songIds) throws SQLException {
//        Playlist playlist = new Playlist();
//        for(var id: songIds) {
//            if(!hasSong(id)) {
//                buySong(id);
//            }
//            var optionalSong = Song.Persistence.read(id);
//            if(optionalSong.isPresent())
//                playlist.add(optionalSong.get());
//            else
//                throw new SQLException();
//        }
//        return playlist;
//    }


    // Klasa Persistence służy do obsługi operacji związanych z kontem słuchacza w bazie danych
    public static class Persistence {
        // Metoda init() inicjalizuje tabele w bazie danych związane z kontem słuchacza
        public static void init() throws SQLException {
            // Inicjalizacja tabeli kont
            Account.Persistence.init();
            {
                // Tworzenie tabeli listener_account, jeśli nie istnieje
                String sql = "CREATE TABLE IF NOT EXISTS listener_account( " +
                        "id_account INTEGER NOT NULL PRIMARY KEY," +
                        "credits INTEGER NOT NULL)";
                PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql);
                statement.executeUpdate();
            }
            {
                // Tworzenie tabeli owned_songs, jeśli nie istnieje
                String sql = "CREATE TABLE IF NOT EXISTS owned_songs( " +
                        "id_account INTEGER NOT NULL," +
                        "id_song INTEGER NOT NULL," +
                        "PRIMARY KEY (id_account, id_song))";
                PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql);
                statement.executeUpdate();
            }
        }

        // Metoda register() rejestruje nowe konto słuchacza w bazie danych
        public static int register(String username, String password) throws SQLException{
            try {
                // Rejestracja konta
                int id = Account.Persistence.register(username, password);
                // Dodanie konta do tabeli listener_account
                String sql = "INSERT INTO listener_account(id_account, credits) VALUES (?, 0)";
                PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql);
                statement.setInt(1,id);
                statement.executeUpdate();
                return id;
            } catch (SQLException | RuntimeException e) {
                throw new RuntimeException(e);
            }
        }

        // Metoda getCredits() pobiera liczbę kredytów danego konta
        private static int getCredits(int id) throws SQLException {
            String sql = "SELECT credits FROM listener_account WHERE id_account = ?";
            PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql);
            statement.setInt(1,id);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                return resultSet.getInt("credits");
            }
            else throw new SQLException();
        }

        // Metoda addCredits() dodaje kredyty do danego konta
        private static void addCredits(int id, int amount) throws SQLException {
            int currentCredits = getCredits(id);
            String sql = "UPDATE listener_account SET credits = ? WHERE id_account = ?";
            PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql);
            statement.setInt(1,currentCredits + amount);
            statement.setInt(2, id);
            statement.executeUpdate();
        }

        // Metoda addSong() dodaje piosenkę do konta
        public static void addSong(int accountId, int songId) throws SQLException {
            String sql = "INSERT INTO owned_songs VALUES(?, ?)";
            PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql);
            statement.setInt(1, accountId);
            statement.setInt(2, songId);
            statement.executeUpdate();
        }

        // Metoda hasSong() sprawdza, czy dana piosenka jest już w posiadaniu konta
        public static boolean hasSong(int accountId, int songId) throws SQLException {
            String sql = "SELECT * FROM owned_songs WHERE id_account = ? AND id_song = ?";
            PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql);
            statement.setInt(1, accountId);
            statement.setInt(2, songId);
            return statement.executeQuery().next();
        }

        // Metoda authenticate() autentykuje konto na podstawie nazwy użytkownika i hasła
        static ListenerAccount authenticate(String username, String password) throws AuthenticationException {
            Account account = Account.Persistence.authenticate(username, password);
            return new ListenerAccount(account.getId(), account.getUsername());
        }

    }
}