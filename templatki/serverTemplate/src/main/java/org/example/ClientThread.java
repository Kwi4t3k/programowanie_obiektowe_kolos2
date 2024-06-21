package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.Socket;

// Klasa ClientThread dziedzicząca po klasie Thread, reprezentuje wątek klienta
public class ClientThread extends Thread {
    private Socket client; // Gniazdo dla połączenia klienta
    private Server server; // Referencja do serwera
    private PrintWriter writer; // Obiekt PrintWriter do wysyłania wiadomości do serwera

    // Konstruktor klasy ClientThread
    public ClientThread(Socket socket, Server server) {
        this.client = socket;
        this.server = server;
    }

    // Metoda run() uruchamiana przy starcie wątku
    @Override
    public void run() {
        try {
            // Utworzenie strumieni wejścia/wyjścia
            InputStream input = client.getInputStream();
            OutputStream output = client.getOutputStream();
            // Reader do odczytywania wiadomości od serwera
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            // Inicjalizacja writera
            writer = new PrintWriter(output,true);

            String rawMessage;
            // Pętla odczytująca wiadomości od serwera
            while((rawMessage = reader.readLine()) != null) {
                // TODO: Implementacja logiki obsługi wiadomości od serwera
            }


        } catch (IOException e) {
            // Obsługa wyjątków związanych z błędami wejścia/wyjścia
            throw new RuntimeException(e);
        }
    }

    // Przykład metody do wysyłania wiadomości do serwera
//    public void send(Message message) throws JsonProcessingException {
//        // Serializacja obiektu klasy Message do formatu JSON
//        String rawMessage = new ObjectMapper().writeValueAsString(message);
//        // Wysłanie sformatowanej wiadomości do serwera
//        writer.println(rawMessage);
//    }
}
