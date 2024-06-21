package org.example;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ClientController {

    public Label wordCountLabel; // Etykieta do wyświetlania liczby słów
    public ListView wordList; // Lista do wyświetlania słów
    public TextField filterField; // Pole tekstowe do wprowadzania filtrów

    public ClientController() {
        ClientReciver.controller = this; // Ustawienie kontrolera dla odbiorcy klienta
    }

    List<Word> listOfWords = new ArrayList<>(); // Lista przechowująca słowa

    // Metoda dodająca nowe słowo do listy słów
    public void newWordInListOfWords(String word) {
        listOfWords.add(new Word(word, LocalTime.now())); // Dodanie nowego słowa do listy
        Platform.runLater(() -> {
            wordCountLabel.setText(Integer.toString(listOfWords.size())); // Aktualizacja etykiety z liczbą słów
            update(); // Aktualizacja listy słów
        });
    }

    // Metoda aktualizująca listę słów
    public void update() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss "); // Formatter do formatowania czasu
        wordList.setItems(FXCollections.observableArrayList(listOfWords.stream()
                .filter((word) -> word.getWord().startsWith(filterField.getText())) // Filtracja słów zaczynających się od tekstu wprowadzonego w polu filtru
                .sorted(Comparator.comparing(word -> word.getWord())) // Sortowanie słów alfabetycznie
                .map((item) -> item.getTime().format(formatter) + item.getWord()) // Mapowanie słów do formatu "czas: słowo"
                .toList()
        ));
    }

    // Metoda wywoływana po naciśnięciu klawisza Enter
    public void onEnter() {
        Platform.runLater(this::update); // Aktualizacja listy słów
    }
}
