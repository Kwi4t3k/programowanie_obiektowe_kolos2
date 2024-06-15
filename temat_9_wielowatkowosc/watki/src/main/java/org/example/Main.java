package org.example;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        ImageProcessor imageProcessor = new ImageProcessor(); // Tworzenie nowego obiektu klasy ImageProcessor

//        try {
//            imageProcessor.readImage("lake.jpg");
//            imageProcessor.setBrightness(70);
//            imageProcessor.writeImage("obrazek.jpg");
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

        // Przykład użycia metody setBrightness() do zwiększenia jasności obrazu
        try {
            imageProcessor.readImage("lake.jpg"); // Wczytanie obrazu
            imageProcessor.setBrightness(10); // Zwiększenie jasności obrazu
            imageProcessor.setBrightness(-10); // Zmniejszenie jasności obrazu
            {
                long startTime = System.currentTimeMillis(); // Pobranie czasu startu
                imageProcessor.setBrightness(10); // Zwiększenie jasności obrazu
                long endTime = System.currentTimeMillis(); // Pobranie czasu końca
                System.out.println(endTime-startTime); // Wydrukowanie czasu wykonania
            }
            {
                long startTime = System.currentTimeMillis(); // Pobranie czasu startu
                imageProcessor.setBrightness2(10); // Zwiększenie jasności obrazu za pomocą wielowątkowości
                long endTime = System.currentTimeMillis(); // Pobranie czasu końca
                System.out.println(endTime-startTime); // Wydrukowanie czasu wykonania
            }
            {
                long startTime = System.currentTimeMillis(); // Pobranie czasu startu
                imageProcessor.setBrightnessThreadPool(100); // Zwiększenie jasności obrazu za pomocą puli wątków
                long endTime = System.currentTimeMillis(); // Pobranie czasu końca
                System.out.println(endTime-startTime); // Wydrukowanie czasu wykonania
            }
            imageProcessor.writeImage("obrazek.jpg"); // Zapisanie obrazu do pliku
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Przykład użycia metody calculateHistogram() do obliczenia histogramu obrazu
        try {
            ImageProcessor processor = new ImageProcessor(); // Tworzenie nowego obiektu klasy ImageProcessor
            processor.readImage("lake.jpg"); // Wczytanie obrazu

            int[] histogram = processor.calculateHistogram("red"); // Obliczenie histogramu dla kanału czerwonego

            processor.generateHistogramImage(histogram, "histogram.jpg"); // Wygenerowanie obrazu histogramu

            System.out.println("Obraz histogramu został pomyślnie wygenerowany!"); // Wydrukowanie informacji o pomyślnym wygenerowaniu obrazu histogramu

        } catch (IOException e) {
            System.out.println("Wystąpił błąd podczas przetwarzania obrazu: " + e.getMessage()); // Wydrukowanie informacji o błędzie
        }
    }
}