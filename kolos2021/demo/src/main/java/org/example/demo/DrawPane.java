package org.example.demo;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class DrawPane extends Pane {

    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;

    private Canvas canvas;
    private GraphicsContext gc;

    // Lista przechowująca wszystkie narysowane odcinki
    private static List<LineSegment> lines = new ArrayList<>();
    private double offsetX = 0;
    private double offsetY = 0;

    public DrawPane() {
        // Tworzenie kanwy do rysowania
        canvas = new Canvas(WIDTH, HEIGHT);
        gc = canvas.getGraphicsContext2D();
        this.getChildren().add(canvas);

        // Obsługa zdarzeń klawiatury
        this.setOnKeyPressed(this::handleKeyPress);

        draw();
    }

    private void handleKeyPress(KeyEvent event) {
        // Przesuwanie układu współrzędnych na podstawie naciśniętych klawiszy strzałek
        if (event.getCode() == KeyCode.UP) {
            offsetY += 10;
        } else if (event.getCode() == KeyCode.DOWN) {
            offsetY -= 10;
        } else if (event.getCode() == KeyCode.LEFT) {
            offsetX += 10;
        } else if (event.getCode() == KeyCode.RIGHT) {
            offsetX -= 10;
        }
        draw();
    }

    public static void addLine(LineSegment line) {
        // Dodawanie odcinka do listy i odświeżanie rysunku w wątku JavaFX
        Platform.runLater(() -> {
            lines.add(line);
            draw();
        });
    }

    private static void draw() {
        // Czyszczenie kanwy
        GraphicsContext gc = instance.gc;
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, WIDTH, HEIGHT);

        // Rysowanie aktualnego przesunięcia układu współrzędnych
        gc.setFill(Color.BLACK);
        gc.fillText("Offset: (" + instance.offsetX + ", " + instance.offsetY + ")", 10, 490);

        // Rysowanie wszystkich odcinków z uwzględnieniem przesunięcia
        for (LineSegment line : lines) {
            gc.setStroke(Color.web(line.getColor()));
            gc.strokeLine(line.getX1() + instance.offsetX, line.getY1() + instance.offsetY,
                    line.getX2() + instance.offsetX, line.getY2() + instance.offsetY);
        }
    }

    private static DrawPane instance;

    {
        // Inicjalizacja instancji DrawPane
        instance = this;
    }
}
