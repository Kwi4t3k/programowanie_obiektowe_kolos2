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
public class Client {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter username: ");
        String username = scanner.nextLine();

        System.out.println("Enter CSV file path: ");
        String filepath = scanner.nextLine();

        sendData(username, filepath);
    }

    public static void sendData(String name, String filepath) {
        try (Socket socket = new Socket("localhost", 1234);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            out.println(name); // Send username to the server

            Files.lines(Path.of(filepath)).forEach(line -> {
                out.println(line);
                try {
                    Thread.sleep(2000); // 2-second delay
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

            out.println("bye"); // Indicate end of transmission

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
