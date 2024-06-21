package org.example;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// Serwer:
// Obsługuje wielu klientów za pomocą wątków.
// Umożliwia rozsyłanie wiadomości do wszystkich połączonych klientów.
// Zmiany do zad 3:
// Przechowywanie listy zalogowanych użytkowników.
// Powiadamianie wszystkich klientów o dołączeniu i opuszczeniu czatu przez użytkownika.
// Obsługa komendy /online, która zwraca listę aktualnie zalogowanych użytkowników.
// Zadanie 3a:
// Serwer: Obsługa logowania i powiadamiania innych użytkowników o dołączeniu i opuszczeniu czatu.
// Klient: Wprowadzenie loginu przy starcie.
// Zadanie 3b:
// Serwer: Obsługa komendy /online zwracającej listę zalogowanych użytkowników.
// Klient: Możliwość wpisania komendy /online i otrzymania listy aktualnie zalogowanych użytkowników.
// Zadanie 4a: W serwerze, wszystkie wiadomości od użytkowników zaczynają się teraz od ich loginów. Zmiana w metodzie broadcastMessage(String message).
// Zadanie 4b: W serwerze, dodano obsługę komendy /w recipient message do wysyłania prywatnych wiadomości. Zmiany w metodzie sendPrivateMessage(String recipient, String privateMessage).

public class Server {
    // Lista do przechowywania handlerów klientów
    private static List<ClientHandler> clientHandlers = new ArrayList<>();
    // Zbiór do przechowywania zalogowanych użytkowników
    private static Set<String> loggedUsers = new HashSet<>();

    public static void main(String[] args) throws IOException {
        // Utworzenie gniazda serwera
        ServerSocket serverSocket = new ServerSocket(1234);

        while (true) {
            try {
                // Akceptacja połączenia od klienta
                Socket socket = serverSocket.accept();
                // Utworzenie nowego handlera dla klienta
                ClientHandler clientHandler = new ClientHandler(socket);
                // Dodanie handlera do listy
                synchronized (clientHandlers) {
                    clientHandlers.add(clientHandler);
                }
                // Uruchomienie wątku handlera
                clientHandler.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static class ClientHandler extends Thread {
        private Socket socket;
        private BufferedWriter bufferedWriter;
        private BufferedReader bufferedReader;
        private String username;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                // Utworzenie strumieni wejścia i wyjścia
                InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());

                // Buforowane strumienie do odczytu i zapisu danych
                bufferedReader = new BufferedReader(inputStreamReader);
                bufferedWriter = new BufferedWriter(outputStreamWriter);

                // Zadanie 3a: Wpisanie loginu
                username = bufferedReader.readLine();
                // Dodanie nazwy użytkownika do zbioru zalogowanych użytkowników
                synchronized (loggedUsers) {
                    loggedUsers.add(username);
                }
                // Powiadomienie wszystkich użytkowników o dołączeniu nowego użytkownika
                broadcastMessage("User " + username + " has joined the chat.");

                String message;
                while ((message = bufferedReader.readLine()) != null) {
                    if (message.startsWith("/w ")) {
                        // Zadanie 4b: Wiadomość prywatna
                        String[] messageParts = message.split(" ", 3);
                        if (messageParts.length == 3) {
                            String recipient = messageParts[1];
                            String privateMessage = messageParts[2];
                            // Wysłanie wiadomości prywatnej
                            sendPrivateMessage(recipient, privateMessage);
                        }
                    } else if (message.equalsIgnoreCase("/online")) {
                        // Zadanie 3b: Wysłanie listy zalogowanych użytkowników
                        sendOnlineUsers();
                    } else {
                        // Zadanie 4a: Wszystkie wiadomości rozpoczynają się od loginu
                        broadcastMessage(username + ": " + message);
                    }
                    // Zakończenie wątku po otrzymaniu wiadomości "BYE"
                    if (message.equalsIgnoreCase("BYE")) {
                        break;
                    }
                }

                // Usunięcie nazwy użytkownika ze zbioru zalogowanych użytkowników
                synchronized (loggedUsers) {
                    loggedUsers.remove(username);
                }
                // Usunięcie handlera z listy
                synchronized (clientHandlers) {
                    clientHandlers.remove(this);
                }
                // Powiadomienie wszystkich użytkowników o opuszczeniu czatu przez użytkownika
                broadcastMessage("User " + username + " has left the chat.");

                // Zamknięcie gniazda
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    // Zamknięcie gniazda w bloku finally, aby upewnić się, że zostanie zamknięte nawet w przypadku wystąpienia wyjątku
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // Metoda do rozsyłania wiadomości do wszystkich użytkowników
        private void broadcastMessage(String message) {
            System.out.println(message); // Wyświetlanie wiadomości na serwerze
            synchronized (clientHandlers) {
                for (ClientHandler handler : clientHandlers) {
                    try {
                        // Wysłanie wiadomości do każdego użytkownika
                        handler.bufferedWriter.write(message);
                        handler.bufferedWriter.newLine();
                        handler.bufferedWriter.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        // Metoda do wysyłania wiadomości prywatnej
        private void sendPrivateMessage(String recipient, String privateMessage) {
            boolean userFound = false;
            synchronized (clientHandlers) {
                for (ClientHandler handler : clientHandlers) {
                    if (handler.username.equals(recipient)) {
                        try {
                            // Wysłanie wiadomości prywatnej do odbiorcy
                            handler.bufferedWriter.write("Private message from " + username + ": " + privateMessage);
                            handler.bufferedWriter.newLine();
                            handler.bufferedWriter.flush();
                            userFound = true;
                            break;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            if (!userFound) {
                try {
                    // Informacja o tym, że odbiorca nie jest online
                    bufferedWriter.write("User " + recipient + " is not online.");
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // Metoda do wysyłania listy zalogowanych użytkowników
        private void sendOnlineUsers() {
            try {
                StringBuilder usersList = new StringBuilder("Online users: ");
                synchronized (loggedUsers) {
                    for (String user : loggedUsers) {
                        usersList.append(user).append(", ");
                    }
                }
                if (usersList.length() > 0) {
                    usersList.setLength(usersList.length() - 2); // Usunięcie końcowego przecinka i spacji
                }
                // Wysłanie listy zalogowanych użytkowników
                bufferedWriter.write(usersList.toString());
                bufferedWriter.newLine();
                bufferedWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
