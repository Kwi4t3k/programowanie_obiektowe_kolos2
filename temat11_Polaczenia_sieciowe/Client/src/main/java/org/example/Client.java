package org.example;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

//Klient:
//
//Ma dwa wątki: jeden do wysyłania wiadomości, drugi do odbierania.
//Zamyka połączenie po wysłaniu wiadomości "BYE".

//Zmiany do zad3:
//Wprowadzenie loginu przed rozpoczęciem czatu.
//Dodanie obsługi komendy /online.

//Zadanie 3a:
//Serwer: Obsługa logowania i powiadamiania innych użytkowników o dołączeniu i opuszczeniu czatu.
//Klient: Wprowadzenie loginu przy starcie.
//Zadanie 3b:
//Serwer: Obsługa komendy /online zwracającej listę zalogowanych użytkowników.
//Klient: Możliwość wpisania komendy /online i otrzymania listy aktualnie zalogowanych użytkowników.

public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 1234);

            InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());

            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

            Scanner scanner = new Scanner(System.in);

            // Zadanie 3a: Wpisanie loginu
            System.out.print("Enter your username: ");
            String username = scanner.nextLine();
            bufferedWriter.write(username);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            Thread sendThread = new Thread(() -> {
                while (true) {
                    String msgToSend = scanner.nextLine();
                    try {
                        bufferedWriter.write(msgToSend);
                        bufferedWriter.newLine();
                        bufferedWriter.flush();
                        if (msgToSend.equalsIgnoreCase("BYE")) {
                            break;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            Thread receiveThread = new Thread(() -> {
                try {
                    String message;
                    while ((message = bufferedReader.readLine()) != null) {
                        System.out.println(message);
                    }
                } catch (IOException e) {
                    System.out.println("Connection closed.");
                }
            });

            sendThread.start();
            receiveThread.start();

            sendThread.join();
            socket.close();  // Zamknij gniazdo po zakończeniu wątku wysyłającego
            receiveThread.interrupt();  // Przerwij wątek odbierający
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}