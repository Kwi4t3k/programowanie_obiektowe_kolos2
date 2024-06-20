package com.example.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Client {
    public void start(String address, int port) {
        try {
            ServerThread thread = new ServerThread(address, port);
            thread.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            while(true) {
                String rawMessage = reader.readLine();
                // pętla działania klienta z konsoli bez implementacji
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
