package org.example.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Base64;

//W projekcie pierwszym napisz aplikacje serwerową, która obsługuje wielu klientów.
//
//Dla pojedynczego klienta serwer pobiera informację o nazwie usera, dla każdej otrzymanej linii tworzy wykres i zapisuje go w formacie base64, oraz dodaje wiersz do bazy sqlite z nazwą użytkownika, numerem elektrody/linii i wykresem w base64.
//
//Pamiętaj o utworzeniu bazy danych za pomocą klasy Creator.

// Klasa Server reprezentuje serwer obsługujący wielu klientów
public class Server {
    // Adres URL bazy danych
    private static final String DATABASE_URL = "jdbc:sqlite:usereeg.db";

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(1234)) {
            System.out.println("Server started on port 1234");

            // Pętla nieskończona do obsługi wielu klientów
            while (true) {
                // Akceptowanie połączeń od klientów
                Socket clientSocket = serverSocket.accept();
                // Tworzenie nowego wątku dla każdego klienta
                new Thread(new ClientHandler(clientSocket)).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Klasa ClientHandler obsługuje pojedynczego klienta
    static class ClientHandler implements Runnable {
        private Socket socket;
        private String username;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                // Pierwsza linia to nazwa użytkownika
                username = in.readLine();
                String line;
                int electrodeNumber = 0;

                // Pętla czytająca linie od klienta
                while ((line = in.readLine()) != null) {
                    if ("bye".equalsIgnoreCase(line)) {
                        break;
                    }

                    // Generowanie wykresu w formacie base64 (dla uproszczenia generujemy tylko ciąg znaków)
                    String dummyGraph = Base64.getEncoder().encodeToString(("Graph for " + line).getBytes());

                    // Zapisywanie danych do bazy danych
                    saveToDatabase(username, electrodeNumber++, dummyGraph);
                }

            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        }

        // Metoda do zapisywania danych do bazy danych
        private void saveToDatabase(String username, int electrodeNumber, String image) throws SQLException {
            String insertSQL = "INSERT INTO user_eeg(username, electrode_number, image) VALUES(?, ?, ?)";

            try (Connection conn = DriverManager.getConnection(DATABASE_URL);
                 PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
                // Ustawianie parametrów zapytania SQL
                pstmt.setString(1, username);
                pstmt.setInt(2, electrodeNumber);
                pstmt.setString(3, image);
                // Wykonywanie zapytania SQL
                pstmt.executeUpdate();
            }
        }
    }
}