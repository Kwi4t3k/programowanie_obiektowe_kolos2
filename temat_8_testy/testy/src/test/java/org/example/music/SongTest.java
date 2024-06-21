package org.example.music;

import org.example.database.DatabaseConnection;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.sql.SQLException;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class SongTest {
    // Metoda wywoływana przed wszystkimi testami, służy do otwarcia połączenia z bazą danych
    @BeforeAll
    static void openDatabase(){
        // PAMIĘTAJ O POPRAWNEJ ŚCEIŻCE, BO NIE BĘDZIE CI SIĘŁAĆZYĆ DO BAZY DANYCH!(JAKBY WSZYSTKO ZAWIODŁO, TO KLIKASZ NA PLIK Z BAZĄ DANYCH PRAWYM RPZYCISKIEM "COPY PATH/REFERENCE")
        DatabaseConnection.connect("songs.db", "song");
    }

    // Metoda wywoływana po wszystkich testach, służy do zamknięcia połączenia z bazą danych
    @AfterAll
    static void closeDatabase(){
        DatabaseConnection.disconnect("song");
    }

    // Test sprawdzający odczyt z bazy danych piosenki o poprawnym indeksie
    @Test
    public void testCorrectIndex() throws SQLException {
        Optional<Song> song = Song.Persistence.read(3);
        assertEquals("Stairway to Heaven", song.get().title());
    }

    // Test sprawdzający próbę odczytu piosenki i niepoprawnym indeksie
    @Test
    public void testIncorrectIndex() throws SQLException {
        Optional<Song> song = Song.Persistence.read(100);
        assertTrue(song.isEmpty());
    }

    // Metoda zwracająca strumień indeksów i oczekiwanych piosenek
    private static Stream<Arguments> songs(){
        return Stream.of(
                Arguments.arguments(1,"The Beatles","Hey Jude",431),
                Arguments.arguments(3,"Led Zeppelin","Stairway to Heaven",482),
                Arguments.arguments(7,"The Doors","Light My Fire",426)
        );
    }
    // Test sparametryzowany metodą zwracającą strumień indeksów i oczekiwanych piosenek
    @ParameterizedTest
    @MethodSource("songs")
    public void testReturnStreamOfIndexAndSongs(int index, String artist, String title, int length) throws SQLException {
        Optional<Song> song = Song.Persistence.read(index);
        assertEquals(title, song.get().title());
    }

    // Test sparametryzowany plikiem songs.csv
    @ParameterizedTest
    @CsvFileSource(files = "songs.csv", numLinesToSkip = 1)
    public void testCSV(int index, String artist, String title, int length) throws SQLException {
        Optional<Song> song = Song.Persistence.read(index);
        assertEquals(title, song.get().title());
    }
}
