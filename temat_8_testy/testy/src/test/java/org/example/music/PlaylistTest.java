package org.example.music;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlaylistTest {
    //Nazwa testu -> test _ when _ then -> test _ co robimy _ czego oczekujemy
    //Napisz test sprawdzający, czy nowo utworzona playlista jest pusta.
    @Test
    public void testNewPlaylistIsEmpty(){
        Playlist playlist = new Playlist();
        assertTrue(playlist.isEmpty());
    }

    //Napisz test sprawdzający, czy po dodaniu jednego utworu playlista ma rozmiar 1.
    @Test
    public void testIsPlaylistLengthOne(){
        Playlist playlist = new Playlist();
        playlist.add(new Song("dupa", "rzepa", 200));
        assertTrue(playlist.size() == 1);
        // assertEquals(1, playlist.size()); (to samo co wyzej)
    }

    //Napisz jest sprawdzający, czy po dodaniu jednego utworu, jest w nim ten sam utwór.
    @Test
    public void testIsSongAddedToPlaylist(){
        Playlist playlist = new Playlist();
        Song dupsko = new Song("dupa", "rzepa", 200);
        playlist.add(dupsko);
        assertTrue(playlist.get(0) == dupsko);
    }

    //W klasie Playlist napisz metodę atSecond, która przyjmie całkowitą liczbę sekund i zwróci obiekt Song, który jest odtwarzany po tylu sekundach odtwarzania playlisty. Napisz test sprawdzający działanie tej metody.
    @Test
    public void test_atSecond(){
        Playlist playlist = new Playlist();
        Song dupsko = new Song("dupa", "rzepa", 200);
        playlist.add(dupsko);

        assertTrue(dupsko == playlist.atSecond(200));
    }
}
