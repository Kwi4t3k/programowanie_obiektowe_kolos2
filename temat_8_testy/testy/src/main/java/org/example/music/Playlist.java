package org.example.music;

import java.util.ArrayList;

// Klasa Playlist dziedziczy po ArrayList<Song>
public class Playlist extends ArrayList<Song> {
    //W klasie Playlist napisz metodę atSecond, która przyjmie całkowitą liczbę sekund i zwróci obiekt Song, który jest odtwarzany po tylu sekundach odtwarzania playlisty. Napisz test sprawdzający działanie tej metody.

    public Song atSecond(int seconds){
        // Jeśli liczba sekund jest ujemna, rzucany jest wyjątek
        if (seconds < 0){
            throw new IndexOutOfBoundsException("Podano ujemny czas\n");
        }

        // Zmienna do przechowywania całkowitej liczby sekund piosenki
        int totalSecondsOfSong = 0;

        // Pętla przechodząca przez wszystkie piosenki w playliście
        for (Song song : this){
            // Dodawanie czasu trwania piosenki do całkowitej liczby sekund
            totalSecondsOfSong += song.timeInSeconds();

            // Jeśli całkowita liczba sekund jest większa lub równa podanej liczbie sekund,
            // zwracana jest aktualna piosenka
            if (totalSecondsOfSong >= seconds){
                return song;
            }
        }
        // Jeśli podana liczba sekund przekracza całkowity czas trwania playlisty,
        // rzucany jest wyjątek
        throw new IndexOutOfBoundsException("Podano zły czas\n");
    }
}