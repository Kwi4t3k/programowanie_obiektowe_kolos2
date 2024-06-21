package org.example.kolos2023;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;

public class ImageServer extends Application {

    // Domyślna wielkość jądra splotu
    private static int kernelSize = 3;
    // URL bazy danych SQLite
    private static final String DB_URL = "jdbc:sqlite:index.db";
    // Port, na którym serwer będzie nasłuchiwał
    private static final int PORT = 1234;

    public static void main(String[] args) {
        // Uruchomienie aplikacji JavaFX
        launch(args);

        // Uruchomienie serwera w osobnym wątku
        Executors.newSingleThreadExecutor().submit(() -> {
            try (ServerSocket serverSocket = new ServerSocket(PORT)) {
                while (true) {
                    try (Socket socket = serverSocket.accept()) {
                        // Obsługa połączenia z klientem
                        handleClient(socket);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Server Control Panel");

        // Tworzenie suwaka do ustawiania wielkości jądra splotu
        Slider slider = new Slider(1, 15, 3);
        slider.setBlockIncrement(2);
        slider.setMajorTickUnit(2);
        slider.setMinorTickCount(1);
        slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);
        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            kernelSize = newValue.intValue();
            if (kernelSize % 2 == 0) {
                kernelSize++;
            }
        });

        // Etykieta wyświetlająca aktualną wielkość jądra splotu
        Label label = new Label("Kernel Size: " + kernelSize);
        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            kernelSize = newValue.intValue();
            if (kernelSize % 2 == 0) {
                kernelSize++;
            }
            label.setText("Kernel Size: " + kernelSize);
        });

        // Ustawienie interfejsu użytkownika
        VBox vbox = new VBox(label, slider);
        Scene scene = new Scene(vbox, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Funkcja do obsługi klienta
    private static void handleClient(Socket socket) {
        try (InputStream in = socket.getInputStream();
             OutputStream out = socket.getOutputStream()) {
            // Odbieranie pliku graficznego od klienta
            Path imageDir = Paths.get("images");
            if (!Files.exists(imageDir)) {
                Files.createDirectories(imageDir);
            }

            // Tworzenie nazwy pliku ze znacznikiem czasowym
            String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
            Path filePath = imageDir.resolve("image_" + timestamp + ".png");
            try (FileOutputStream fos = new FileOutputStream(filePath.toFile())) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) != -1) {
                    fos.write(buffer, 0, bytesRead);
                }
            }

            // Wczytanie obrazu z pliku
            File imageFile = filePath.toFile();
            Image image = new Image(imageFile.toURI().toString());

            // Zastosowanie algorytmu box blur
            long startTime = System.currentTimeMillis();
            Image blurredImage = applyBoxBlur(image, kernelSize);
            long endTime = System.currentTimeMillis();
            long delay = endTime - startTime;

            // Zapisanie przetworzonego obrazu
            File blurredFile = imageDir.resolve("blurred_" + timestamp + ".png").toFile();
            ImageIO.write(SwingFXUtils.fromFXImage(blurredImage, null), "png", blurredFile);

            // Logowanie do bazy danych
            logToDatabase(filePath.toString(), kernelSize, delay);

            // Wysłanie przetworzonego obrazu z powrotem do klienta
            try (FileInputStream fis = new FileInputStream(blurredFile)) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = fis.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Funkcja do zastosowania algorytmu box blur
    private static Image applyBoxBlur(Image image, int kernelSize) {
        // Implementacja algorytmu box blur (tutaj należy dodać rzeczywistą implementację)
        return image; // Placeholder: zamień na rzeczywiście przetworzony obraz
    }

    // Funkcja do logowania informacji o przetworzonym obrazie do bazy danych
    private static void logToDatabase(String path, int size, long delay) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            // Tworzenie tabeli, jeśli nie istnieje
            String sql = "CREATE TABLE IF NOT EXISTS images (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "path TEXT," +
                    "size INTEGER," +
                    "delay INTEGER)";
            try (Statement stmt = conn.createStatement()) {
                stmt.execute(sql);
            }

            // Wstawianie danych do tabeli
            sql = "INSERT INTO images (path, size, delay) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, path);
                pstmt.setInt(2, size);
                pstmt.setLong(3, delay);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
