package org.example;

import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

@RestController() // Adnotacja wskazująca, że ta klasa jest kontrolerem REST
public class ImageController {
    // Pomocnicza metoda do dekodowania obrazu z base64
    private BufferedImage decodeBase64ToImage(String base64Image) throws IOException {
        byte[] imageBytes = Base64.getDecoder().decode(base64Image); // Dekodowanie base64 do tablicy bajtów
        return ImageIO.read(new ByteArrayInputStream(imageBytes)); // Czytanie obrazu z tablicy bajtów
    }

    // Pomocnicza metoda do kodowania obrazu do base64
    private String encodeImageToBase64(BufferedImage image) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(); // Strumień do przechowywania obrazu
        ImageIO.write(image, "png", outputStream); // Zapis obrazu do strumienia
        byte[] imageBytes = outputStream.toByteArray(); // Konwersja strumienia do tablicy bajtów
        return Base64.getEncoder().encodeToString(imageBytes); // Kodowanie tablicy bajtów do base64
    }

    // Pomocnicza metoda do ograniczania wartości RGB do zakresu 0-255
    private int clamp(int value) {
        return Math.max(0, Math.min(value, 255)); // Ograniczenie wartości do zakresu 0-255
    }

    // Pomocnicza metoda do zmiany jasności obrazu
    private BufferedImage changeBrightness(BufferedImage image, int brightness) {
        BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB); // Tworzenie nowego obrazu o tych samych wymiarach
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                Color color = new Color(image.getRGB(x, y)); // Pobieranie koloru piksela
                int r = clamp(color.getRed() + brightness); // Zwiększanie jasności kanału czerwonego
                int g = clamp(color.getGreen() + brightness); // Zwiększanie jasności kanału zielonego
                int b = clamp(color.getBlue() + brightness); // Zwiększanie jasności kanału niebieskiego
                Color newColor = new Color(r, g, b); // Tworzenie nowego koloru
                result.setRGB(x, y, newColor.getRGB()); // Ustawianie nowego koloru piksela
            }
        }
        return result; // Zwracanie obrazu z zmienioną jasnością
    }

    // Metoda GET do dostosowywania jasności obrazu
    @GetMapping("/adjustBrightness")
    public String adjustBrightness(@RequestParam String imageBase64, @RequestParam int brightness) throws IOException {
        BufferedImage image = decodeBase64ToImage(imageBase64); // Dekodowanie obrazu z base64
        BufferedImage brightenedImage = changeBrightness(image, brightness); // Zmiana jasności obrazu
        return encodeImageToBase64(brightenedImage); // Kodowanie obrazu do base64
    }

    // Metoda GET do dostosowywania jasności obrazu i zwracania go jako surowych danych
    @GetMapping(value = "/adjustBrightnessRaw", produces = "image/png")
    public byte[] adjustBrightnessRaw(@RequestParam String imageBase64, @RequestParam int brightness) throws IOException {
        BufferedImage image = decodeBase64ToImage(imageBase64); // Dekodowanie obrazu z base64
        BufferedImage brightenedImage = changeBrightness(image, brightness); // Zmiana jasności obrazu
        return convertImageToByteArray(brightenedImage); // Konwersja obrazu do tablicy bajtów
    }

    // Pomocnicza metoda do konwersji obrazu do tablicy bajtów
    private byte[] convertImageToByteArray(BufferedImage image) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(); // Strumień do przechowywania obrazu
        ImageIO.write(image, "png", outputStream); // Zapis obrazu do strumienia
        return outputStream.toByteArray(); // Konwersja strumienia do tablicy bajtów
    }
}
