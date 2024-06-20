package org.example;

import java.time.LocalTime;

public class Word {
    private String word;
    private LocalTime time;

    public Word(String word, LocalTime time) {
        this.word = word;
        this.time = time;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
}
