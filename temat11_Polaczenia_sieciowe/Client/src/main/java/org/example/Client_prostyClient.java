package org.example;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client_prostyClient {
    public static void main(String[] args) {
        // Inicjalizacja zmiennych
        Socket socket = null;
        InputStreamReader inputStreamReader = null;
        OutputStreamWriter outputStreamWriter = null;
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;

        try {
            // Tworzenie nowego gniazda z adresem "localhost" i portem 1234
            socket = new Socket("localhost", 1234);

            // Tworzenie strumieni wejścia/wyjścia
            inputStreamReader = new InputStreamReader(socket.getInputStream());
            outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());

            // Buforowanie strumieni wejścia/wyjścia
            bufferedReader = new BufferedReader(inputStreamReader);
            bufferedWriter = new BufferedWriter(outputStreamWriter);

            // Tworzenie skanera do odczytu danych wejściowych
            Scanner scanner = new Scanner(System.in);

            // Pętla komunikacji klienta z serwerem
            while (true) {
                // Odczytanie wiadomości od użytkownika
                String msgToSend = scanner.nextLine();

                // Wysłanie wiadomości do serwera
                bufferedWriter.write(msgToSend);
                bufferedWriter.newLine();
                bufferedWriter.flush();

                // Odczytanie i wyświetlenie odpowiedzi serwera
                System.out.println("Server: " + bufferedReader.readLine());

                // Jeśli wiadomość to "BYE", zakończ komunikację
                if(msgToSend.equalsIgnoreCase("BYE")){
                    break;
                }
            }
        } catch (IOException e) {
            // Obsługa wyjątków
            e.printStackTrace();
        } finally {
            // Zamknięcie zasobów w bloku finally
            try {
                if (socket != null){
                    socket.close();
                }
                if (inputStreamReader != null){
                    inputStreamReader.close();
                }
                if (outputStreamWriter != null){
                    outputStreamWriter.close();
                }
                if (bufferedReader != null){
                    bufferedReader.close();
                }
                if (bufferedWriter != null){
                    bufferedWriter.close();
                }
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
