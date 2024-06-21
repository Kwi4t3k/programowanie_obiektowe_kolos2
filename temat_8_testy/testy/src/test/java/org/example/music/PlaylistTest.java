package org.example.music;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PlaylistTest {
    //Nazwa testu -> test _ when _ then -> test _ co robimy _ czego oczekujemy
    // Test sprawdzający, czy nowo utworzona playlista jest pusta.
    @Test
    public void testNewPlaylistIsEmpty(){
        // Tworzymy nową playlistę
        Playlist playlist = new Playlist();
        // Sprawdzamy, czy playlista jest pusta
        assertTrue(playlist.isEmpty());
    }

    // Test sprawdzający, czy po dodaniu jednego utworu playlista ma rozmiar 1.
    @Test
    public void testIsPlaylistLengthOne(){
        // Tworzymy nową playlistę
        Playlist playlist = new Playlist();
        // Dodajemy utwór do playlisty
        playlist.add(new Song("dupa", "rzepa", 200));
        // Sprawdzamy, czy rozmiar playlisty wynosi 1
        assertTrue(playlist.size() == 1);
    }

    // Test sprawdzający, czy po dodaniu jednego utworu, jest w nim ten sam utwór.
    @Test
    public void testIsSongAddedToPlaylist(){
        // Tworzymy nową playlistę
        Playlist playlist = new Playlist();
        // Tworzymy nowy utwór
        Song dupsko = new Song("dupa", "rzepa", 200);
        // Dodajemy utwór do playlisty
        playlist.add(dupsko);
        // Sprawdzamy, czy dodany utwór jest na liście
        assertTrue(playlist.get(0) == dupsko);
    }


    //W klasie Playlist napisz metodę atSecond, która przyjmie całkowitą liczbę sekund i zwróci obiekt Song, który jest odtwarzany po tylu sekundach odtwarzania playlisty. Napisz test sprawdzający działanie tej metody.
    // Test sprawdzający działanie metody atSecond.
    @Test
    public void test_atSecond(){
        // Tworzymy nową playlistę
        Playlist playlist = new Playlist();
        // Tworzymy nowy utwór
        Song dupsko = new Song("dupa", "rzepa", 200);
        // Dodajemy utwór do playlisty
        playlist.add(dupsko);
        // Sprawdzamy, czy po 200 sekundach odtwarzania playlisty, odtwarzany jest utwór "dupsko"
        assertTrue(dupsko == playlist.atSecond(200));
    }

    //Zmodyfikuj metodę atSecond, aby rozróżniała podanie ujemnego czasu i czasu wykraczającego poza czas trwania listy i zapisywała w argumencie message konstruktora IndexOutOfBoundsException odpowiedni napis. Napisz dwa testy sprawdzające każdy z wymienionych przypadków.
    // Test sprawdzający, czy metoda atSecond rzuca wyjątek, gdy podany czas jest za duży.
    @Test
    public void test_atSecond_toMuchTime(){
        // Tworzymy nową playlistę
        Playlist playlist = new Playlist();
        // Tworzymy nowy utwór
        Song dupsko = new Song("dupa", "rzepa", 200);
        // Dodajemy utwór do playlisty
        playlist.add(dupsko);
        // Ustalamy czas na 5000 sekund
        int seconds = 5000;
        // Sprawdzamy, czy metoda atSecond rzuca wyjątek, gdy podany czas jest za duży
        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
            playlist.atSecond(seconds);
        });
        // Sprawdzamy, czy komunikat wyjątku jest prawidłowy
        assertEquals("Podano za duży czas\n", exception.getMessage());
    }

    // Test sprawdzający, czy metoda atSecond rzuca wyjątek, gdy podany czas jest ujemny.
    @Test
    public void test_atSecond_negativeTime(){
        // Tworzymy nową playlistę
        Playlist playlist = new Playlist();
        // Tworzymy nowy utwór
        Song dupsko = new Song("dupa", "rzepa", 200);
        // Dodajemy utwór do playlisty
        playlist.add(dupsko);
        // Ustalamy czas na -5 sekund
        int seconds = -5;
        // Sprawdzamy, czy metoda atSecond rzuca wyjątek, gdy podany czas jest ujemny
        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
            playlist.atSecond(seconds);
        });
        // Sprawdzamy, czy komunikat wyjątku jest prawidłowy
        assertEquals("Podano ujemny czas\n", exception.getMessage());
    }
}
