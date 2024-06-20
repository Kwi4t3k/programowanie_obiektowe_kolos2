package org.example;

import java.io.IOException;

// Main tylko odpala serwer
public class Main {
    public static void main(String[] args) {
        try {
            Server server = new Server(8000);
            server.listen();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}