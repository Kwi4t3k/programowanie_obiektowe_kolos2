package org.example;

import java.io.*;
import java.net.Socket;

// Klasa ConnectionThread dziedziczy po klasie Thread
public class ConnectionThread extends Thread {
    Socket socket; // Gniazdo dla połączenia
    PrintWriter writer; // Pisarz do wysyłania danych

    // Konstruktor klasy ConnectionThread
    public ConnectionThread(String address, int port) throws IOException {
        socket = new Socket(address, port); // Tworzenie nowego gniazda
    }

    // Metoda run() jest wywoływana, gdy wątek jest uruchamiany
    @Override
    public void run() {
        try (
                // Tworzenie strumieni wejścia i wyjścia
                InputStream inputStream = socket.getInputStream();
                OutputStream outputStream = socket.getOutputStream();
                // Czytnik do odczytywania danych
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))
        ) {
            // Inicjalizacja pisarza
            writer = new PrintWriter(outputStream, true);

            String rawMessage;

            // Odczytywanie wiadomości od klienta
            while ((rawMessage = reader.readLine()) != null){
                ClientReciver.reciveWord(rawMessage);
            }

        } catch (IOException e) {
            // Obsługa wyjątków
            System.err.println("Connection error: " + e.getMessage());
        }
    }
}
