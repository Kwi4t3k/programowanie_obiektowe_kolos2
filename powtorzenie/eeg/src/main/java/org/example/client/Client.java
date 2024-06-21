package org.example.client;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

//W projekcie pierwszym napisz aplikację kliencką, która będzie:
//
//wczytywać ze standardowego wejścia nazwę użytkownika oraz ścieżkę do pliku csv.
//
//Następnie wyśle na serwer informację o nazwie użytkownika, oraz zawartość pliku csv (w przykładzie są to tm00.csv i tm01.csv ) linia po linii. Po każdej wysłanej linii mają nastąpić 2 sekundy przerwy.
//
//Zakładamy, że podawane są unikatowe nazwy użytkowników.
//
//Po zakończeniu aplikacja wyśle wiadomość informującą serwer o zakończeniu przesyłania(np. bye).
//
//Wysyłanie wszystkich tych informacji odseparuj w oddzielnej funkcji: public void sendData(String name, String filepath)
// Klasa Client reprezentuje aplikację kliencką
public class Client {

    // Główna metoda programu
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Prosimy użytkownika o wprowadzenie nazwy użytkownika
        System.out.println("Enter username: ");
        String username = scanner.nextLine();

        // Prosimy użytkownika o wprowadzenie ścieżki do pliku CSV
        System.out.println("Enter CSV file path: ");
        String filepath = scanner.nextLine();

        // Wysyłamy dane do serwera
        sendData(username, filepath);
    }

    // Metoda do wysyłania danych do serwera
    public static void sendData(String name, String filepath) {
        try (
                // Tworzymy gniazdo (socket) do komunikacji z serwerem
                Socket socket = new Socket("localhost", 1234);

                // Tworzymy pisarza do wysyłania danych do serwera
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                // Tworzymy czytnika do odczytywania danych od serwera
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))
        ) {
            // Wysyłamy nazwę użytkownika do serwera
            out.println(name);

            // Wysyłamy zawartość pliku CSV do serwera, linia po linii
            Files.lines(Path.of(filepath)).forEach(line -> {
                out.println(line);
                try {
                    // Czekamy 2 sekundy po każdej wysłanej linii
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

            // Wysyłamy wiadomość do serwera, informując o zakończeniu transmisji
            out.println("bye");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}