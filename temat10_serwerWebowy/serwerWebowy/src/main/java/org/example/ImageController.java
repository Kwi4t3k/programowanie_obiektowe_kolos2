package org.example;

import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

@RestController()
public class ImageController {
    // Pomocnicza metoda do dekodowania obrazu z base64
    private BufferedImage decodeBase64ToImage(String base64Image) throws IOException {
        byte[] imageBytes = Base64.getDecoder().decode(base64Image);
        return ImageIO.read(new ByteArrayInputStream(imageBytes));
    }

    // Pomocnicza metoda do kodowania obrazu do base64
    private String encodeImageToBase64(BufferedImage image) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "png", outputStream);
        byte[] imageBytes = outputStream.toByteArray();
        return Base64.getEncoder().encodeToString(imageBytes);
    }

    // Pomocnicza metoda do ograniczania wartości RGB do zakresu 0-255
    private int clamp(int value) {
        return Math.max(0, Math.min(value, 255));
    }

    // Pomocnicza metoda do zmiany jasności obrazu
    private BufferedImage changeBrightness(BufferedImage image, int brightness) {
        BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                Color color = new Color(image.getRGB(x, y));
                int r = clamp(color.getRed() + brightness);
                int g = clamp(color.getGreen() + brightness);
                int b = clamp(color.getBlue() + brightness);
                Color newColor = new Color(r, g, b);
                result.setRGB(x, y, newColor.getRGB());
            }
        }
        return result;
    }

    //    Zadanie 6.
    //Napisz kontroler REST ImageController, w którym znajdzie się metoda zawracająca obraz ze zmodyfikowaną jasnością. Metoda typu GET powinna przyjąć obraz w formacie base64 oraz liczbę całkowitą określającą jasność. Metoda powinna rozjaśnić obraz o podaną wartość i zwrócić go w formacie base64.

    @GetMapping("/adjustBrightness")
    public String adjustBrightness(@RequestParam String imageBase64, @RequestParam int brightness) throws IOException {
        BufferedImage image = decodeBase64ToImage(imageBase64);
        BufferedImage brightenedImage = changeBrightness(image, brightness);
        return encodeImageToBase64(brightenedImage);
    }

//  curl -X GET "http://localhost:8080/adjustBrightness?imageBase64=BASE64_STRING&brightness=VALUE"

    //Zadanie 7.
    //
    //Napisz kolejną, zbliżoną metodę, w której wyniku znajdzie się niezakodowany obraz.

    @GetMapping(value = "/adjustBrightnessRaw", produces = "image/png")
    public byte[] adjustBrightnessRaw(@RequestParam String imageBase64, @RequestParam int brightness) throws IOException {
        BufferedImage image = decodeBase64ToImage(imageBase64);
        BufferedImage brightenedImage = changeBrightness(image, brightness);
        return convertImageToByteArray(brightenedImage);
    }

    // Pomocnicza metoda do konwersji obrazu do tablicy bajtów
    private byte[] convertImageToByteArray(BufferedImage image) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "png", outputStream);
        return outputStream.toByteArray();
    }

//    curl -X GET "http://localhost:8080/adjustBrightnessRaw?imageBase64=BASE64_STRING&brightness=VALUE" -o output.png
}