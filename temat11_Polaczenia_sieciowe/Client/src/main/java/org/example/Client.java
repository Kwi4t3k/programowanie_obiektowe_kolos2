package org.example;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

// Klient:
// Ma dwa wątki: jeden do wysyłania wiadomości, drugi do odbierania.
// Zamyka połączenie po wysłaniu wiadomości "BYE".
// Zmiany do zad3:
// Wprowadzenie loginu przed rozpoczęciem czatu.
// Dodanie obsługi komendy /online.
// Zadanie 3a:
// Serwer: Obsługa logowania i powiadamiania innych użytkowników o dołączeniu i opuszczeniu czatu.
// Klient: Wprowadzenie loginu przy starcie.
// Zadanie 3b:
// Serwer: Obsługa komendy /online zwracającej listę zalogowanych użytkowników.
// Klient: Możliwość wpisania komendy /online i otrzymania listy aktualnie zalogowanych użytkowników.

public class Client {
    public static void main(String[] args) {
        try {
            // Utworzenie gniazda klienta i połączenie z serwerem
            Socket socket = new Socket("localhost", 1234);

            // Strumienie wejścia i wyjścia do komunikacji z serwerem
            InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());

            // Buforowane strumienie do odczytu i zapisu danych
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

            // Skaner do odczytu danych wprowadzanych przez użytkownika
            Scanner scanner = new Scanner(System.in);

            // Zadanie 3a: Wpisanie loginu
            System.out.print("Enter your username: ");
            String username = scanner.nextLine();
            // Wysłanie nazwy użytkownika do serwera
            bufferedWriter.write(username);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            // Wątek do wysyłania wiadomości
            Thread sendThread = new Thread(() -> {
                while (true) {
                    String msgToSend = scanner.nextLine();
                    try {
                        // Wysłanie wiadomości do serwera
                        bufferedWriter.write(msgToSend);
                        bufferedWriter.newLine();
                        bufferedWriter.flush();
                        // Zakończenie wątku po wysłaniu wiadomości "BYE"
                        if (msgToSend.equalsIgnoreCase("BYE")) {
                            break;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            // Wątek do odbierania wiadomości
            Thread receiveThread = new Thread(() -> {
                try {
                    String message;
                    // Odczytanie i wyświetlenie wiadomości od serwera
                    while ((message = bufferedReader.readLine()) != null) {
                        System.out.println(message);
                    }
                } catch (IOException e) {
                    System.out.println("Connection closed.");
                }
            });

            // Uruchomienie wątków
            sendThread.start();
            receiveThread.start();

            // Oczekiwanie na zakończenie wątku wysyłającego
            sendThread.join();
            // Zamknięcie gniazda po zakończeniu wątku wysyłającego
            socket.close();
            // Przerwanie wątku odbierającego
            receiveThread.interrupt();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
