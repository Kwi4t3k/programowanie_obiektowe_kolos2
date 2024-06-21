package org.example;

// Klasa ClientReciver służy do odbierania danych od klienta
public class ClientReciver {
    // Kontroler klienta
    public static ClientController controller;
    // Wątek połączenia z klientem
    public static ConnectionThread connection;

    // Metoda do odbierania słów od klienta
    public static void reciveWord(String word){
        // Dodajemy nowe słowo do listy słów w kontrolerze
        controller.newWordInListOfWords(word);
    }
}
