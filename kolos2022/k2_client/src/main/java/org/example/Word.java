package org.example;

import java.time.LocalTime;

// Klasa Word reprezentuje pojedyncze słowo i czas, w którym zostało utworzone.
public class Word {
    // Prywatne pole przechowujące słowo.
    private String word;
    // Prywatne pole przechowujące czas utworzenia słowa.
    private LocalTime time;

    // Konstruktor klasy Word, który inicjalizuje słowo i czas.
    public Word(String word, LocalTime time) {
        this.word = word;
        this.time = time;
    }

    // Metoda zwracająca słowo.
    public String getWord() {
        return word;
    }

    // Metoda ustawiająca nowe słowo.
    public void setWord(String word) {
        this.word = word;
    }

    // Metoda zwracająca czas utworzenia słowa.
    public LocalTime getTime() {
        return time;
    }

    // Metoda ustawiająca nowy czas utworzenia słowa.
    public void setTime(LocalTime time) {
        this.time = time;
    }
}
