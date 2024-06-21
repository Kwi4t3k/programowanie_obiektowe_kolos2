package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

// Klasa ClientApplication dziedziczy po klasie Application z JavaFX
public class ClientApplication extends Application {
    // Metoda start jest wywoływana, gdy aplikacja jest uruchamiana
    @Override
    public void start(Stage stage) throws IOException {
        // Ładujemy plik FXML, który definiuje interfejs użytkownika
        FXMLLoader fxmlLoader = new FXMLLoader(ClientApplication.class.getResource("viev.fxml"));

        // Tworzymy nową scenę i ustawiamy jej rozmiar
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);

        // Ustawiamy tytuł okna
        stage.setTitle("Hello!");

        // Dodajemy scenę do naszego okna
        stage.setScene(scene);

        // Wyświetlamy okno
        stage.show();

        // Tworzymy nowe połączenie z serwerem
        ClientReciver.connection = new ConnectionThread("localhost", 5000);

        // Uruchamiamy wątek połączenia
        ClientReciver.connection.start();
    }

    // Główna metoda programu
    public static void main(String[] args) {
        // Metoda launch uruchamia aplikację JavaFX
        launch();
    }
}
