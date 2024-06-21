package org.example.demo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        // Uruchomienie serwera w osobnym wątku
        new Thread(() -> Server.startServer(1234)).start();
        // Uruchomienie aplikacji JavaFX
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Tworzenie głównego panelu do rysowania
        DrawPane drawPane = new DrawPane();
        Scene scene = new Scene(drawPane, 500, 500);

        // Konfiguracja okna aplikacji
        primaryStage.setTitle("Drawing Canvas");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Ustawienie fokusu na panel, aby mógł obsługiwać zdarzenia klawiatury
        drawPane.requestFocus();
    }
}
