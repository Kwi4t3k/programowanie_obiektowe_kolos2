package com.example.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.Socket;

public class ServerThread extends Thread {
    // Gniazdo dla połączenia z serwerem
    Socket socket;
    // Writer do wysyłania wiadomości do serwera
    PrintWriter writer;

    // Konstruktor tworzący nowe gniazdo z podanym adresem i portem
    public ServerThread(String address, int port) throws IOException {
        socket = new Socket(address, port);
    }

    // Metoda uruchamiana przy starcie wątku
    public void run() {
        try {
            // Utworzenie strumieni wejścia/wyjścia
            InputStream input = socket.getInputStream();
            OutputStream output = socket.getOutputStream();
            // Reader do odczytywania wiadomości od serwera
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            // Inicjalizacja writera
            writer = new PrintWriter(output,true);

            String rawMessage;

            // Pętla odczytująca wiadomości od serwera
            while((rawMessage = reader.readLine()) != null) {
                // Deserializacja wiadomości JSON do obiektu klasy Message
                // Message message = new ObjectMapper().readValue(rawMessage, Message.class);

                // TODO: Implementacja logiki odbierania wiadomości
            }
        } catch (IOException e) {
            // Obsługa wyjątków związanych z błędami wejścia/wyjścia
            throw new RuntimeException(e);
        }

    }

    // Metoda do wysyłania wiadomości do serwera
//    public void send(Message message) throws JsonProcessingException {
//        // Serializacja obiektu klasy Message do formatu JSON
//        String rawMessage = new ObjectMapper().writeValueAsString(message);
//        // Wysłanie sformatowanej wiadomości do serwera
//        writer.println(rawMessage);
//    }
}
