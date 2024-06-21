package org.example.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

public class Server {

    // Mapa przechowująca aktualny kolor dla każdego klienta
    private static ConcurrentHashMap<Socket, String> clientColors = new ConcurrentHashMap<>();

    public static void startServer(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                // Akceptowanie nowych połączeń klientów
                Socket clientSocket = serverSocket.accept();
                // Uruchomienie nowego wątku obsługującego klienta
                new Thread(new ClientHandler(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Klasa obsługująca połączenia klientów
    private static class ClientHandler implements Runnable {
        private Socket clientSocket;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        @Override
        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
                String line;
                while ((line = in.readLine()) != null) {
                    if (line.matches("[0-9A-Fa-f]{6}")) {
                        // Otrzymano kod koloru w formacie szesnastkowym
                        clientColors.put(clientSocket, line);
                    } else if (line.matches("-?\\d+\\.\\d+\\s-?\\d+\\.\\d+\\s-?\\d+\\.\\d+\\s-?\\d+\\.\\d+")) {
                        // Otrzymano współrzędne odcinka
                        String color = clientColors.getOrDefault(clientSocket, "000000");
                        String[] parts = line.split("\\s+");
                        double x1 = Double.parseDouble(parts[0]);
                        double y1 = Double.parseDouble(parts[1]);
                        double x2 = Double.parseDouble(parts[2]);
                        double y2 = Double.parseDouble(parts[3]);

                        // Dodanie odcinka do panelu rysowania
                        DrawPane.addLine(new LineSegment(x1, y1, x2, y2, color));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                // Usunięcie klienta z mapy po zakończeniu połączenia
                clientColors.remove(clientSocket);
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
