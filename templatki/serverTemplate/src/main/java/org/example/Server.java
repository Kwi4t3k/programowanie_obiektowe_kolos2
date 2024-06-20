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

    public Server(int port) throws IOException {
        socket = new ServerSocket(port);
    }

    public void listen() throws IOException {
        while (true) {
            Socket client = socket.accept();
            ClientThread thread = new ClientThread(client, this);
            clients.add(thread);
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