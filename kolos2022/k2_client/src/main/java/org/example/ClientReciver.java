package org.example;

public class ClientReciver {
    public static ClientController controller;
    public static ConnectionThread connection;

    public static void reciveWord(String word){
        controller.newWordInListOfWords(word);
    }
}
