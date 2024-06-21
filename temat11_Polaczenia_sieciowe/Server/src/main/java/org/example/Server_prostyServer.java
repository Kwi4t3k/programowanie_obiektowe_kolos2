package org.example;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server_prostyServer {
    public static void main(String[] args) throws IOException {
        // Inicjalizacja zmiennych
        Socket socket = null;
        InputStreamReader inputStreamReader = null;
        OutputStreamWriter outputStreamWriter = null;
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;

        // Utworzenie gniazda serwera
        ServerSocket serverSocket = null; //do komunikacji z klientem
        serverSocket = new ServerSocket(1234);

        while (true){
            try{
                // Akceptacja połączenia od klienta
                socket = serverSocket.accept(); //jeśli połączenie się powiodło program tworzy socket (do komunikacji)

                // Utworzenie strumieni wejścia i wyjścia
                inputStreamReader = new InputStreamReader(socket.getInputStream());
                outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());

                // Buforowane strumienie do odczytu i zapisu danych
                bufferedReader = new BufferedReader(inputStreamReader);
                bufferedWriter = new BufferedWriter(outputStreamWriter);

                while (true){  //do ciągłego wysyłania wiadomości
                    // Odczytanie wiadomości od klienta
                    String msgFromClient = bufferedReader.readLine();

                    // Wyświetlenie wiadomości od klienta
                    System.out.println("Client: " + msgFromClient);

                    // Wysłanie potwierdzenia odbioru wiadomości
                    bufferedWriter.write("message recived");
                    bufferedWriter.newLine();
                    bufferedWriter.flush();

                    // Zakończenie połączenia po otrzymaniu wiadomości "BYE"
                    if (msgFromClient.equalsIgnoreCase("BYE")){
                        break;
                    }
                }

                // Zamknięcie strumieni i gniazda
                socket.close();
                inputStreamReader.close();
                outputStreamWriter.close();
                bufferedWriter.close();
                bufferedReader.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
