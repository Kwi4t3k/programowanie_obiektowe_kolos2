package com.example.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Client {
    public void start(String address, int port) {
        try {
            // Tworzenie nowego wątku serwera z podanym adresem i portem
            ServerThread thread = new ServerThread(address, port);
            // Uruchomienie wątku serwera
            thread.start();
            // Tworzenie obiektu BufferedReader do odczytu danych wprowadzanych przez użytkownika
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            while(true) {
                // Odczytanie wiadomości wprowadzonej przez użytkownika
                String rawMessage = reader.readLine();
                // pętla działania klienta z konsoli bez implementacji
                // TODO: Implementacja logiki klienta
            }

        } catch (IOException e) {
            // Obsługa wyjątków związanych z błędami wejścia/wyjścia
            throw new RuntimeException(e);
        }
    }
}
