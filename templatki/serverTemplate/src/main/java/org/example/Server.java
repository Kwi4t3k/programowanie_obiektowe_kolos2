package org.example;


import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server {
    private ServerSocket socket;  // Socket serverowy
    private List<ClientThread> clients = new CopyOnWriteArrayList<>(); // Lista klientów podłącząnych do servera

    // Konstruktor klasy Server
    public Server(int port) throws IOException {
        // Utworzenie nowego gniazda serwera na podanym porcie
        socket = new ServerSocket(port);
    }

    // Metoda listen() nasłuchująca na połączenia od klientów
    public void listen() throws IOException {
        while (true) {
            // Akceptowanie połączenia od klienta
            Socket client = socket.accept();
            // Utworzenie nowego wątku dla klienta
            ClientThread thread = new ClientThread(client, this);
            // Dodanie wątku klienta do listy klientów
            clients.add(thread);
            // Uruchomienie wątku klienta
            thread.start();
        }
    }

    // Przykładowe wysyłanie wiadomości do kazdego z klientów - do obsłużenia przez klienta

//    public void broadcast(Message message) throws JsonProcessingException {
//        for(ClientThread client : clients) {
//            client.send(message);
//        }
//    }
}