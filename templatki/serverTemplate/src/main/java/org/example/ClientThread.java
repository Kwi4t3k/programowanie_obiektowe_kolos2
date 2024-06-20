package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.Socket;

public class ClientThread extends Thread {
    private Socket client; // Socket klienta
    private Server server; // Zmiena serwera
    private PrintWriter writer; // Klasa do komunikacji klient-server

    public ClientThread(Socket socket, Server server) {
        this.client = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            InputStream input = client.getInputStream();
            OutputStream output = client.getOutputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            writer = new PrintWriter(output,true);

            String rawMessage;
            while((rawMessage = reader.readLine()) != null) {
                // W tej pętli wywołania serwerowe
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Przykład wysyłania wiadomości typu jackson/json

//    public void send(Message message) throws JsonProcessingException {
//        String rawMessage = new ObjectMapper().writeValueAsString(message);
//        writer.println(rawMessage);
//    }
}