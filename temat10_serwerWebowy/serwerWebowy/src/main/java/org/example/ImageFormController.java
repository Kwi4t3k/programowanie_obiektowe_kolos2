package org.example;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.codec.binary.Base64;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
public class ImageFormController {
//    Zadanie 8.
//
//Napisz kontroler ImageFormController. Skorzystaj z udostępnionych szablonów w plikach index.html i image.html. W kontrolerze napisz:
//
//Metodę, która wyświetli plik index.html.
//
//Metodę upload, która zostanie wyzwolona przez naciśnięcie przycisku Upload. Metoda powinna wyświetlić plik image.html, wyświetlając w nim przesłany obraz.

    @GetMapping("/")
    public String showIndex() {
        return "index";
    }

    @PostMapping("/upload")
    public String uploadImage(@RequestParam("image") MultipartFile file, @RequestParam("brightness") int brightness, Model model) throws IOException {
        BufferedImage image = ImageIO.read(file.getInputStream());
        BufferedImage brightenedImage = changeBrightness(image, brightness);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(brightenedImage, "png", outputStream);
        byte[] imageBytes = outputStream.toByteArray();
        String imageBase64 = Base64.encodeBase64String(imageBytes);

        model.addAttribute("imageBase64", imageBase64);
        return "image";
    }

    private BufferedImage changeBrightness(BufferedImage image, int brightness) {
        BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int rgb = image.getRGB(x, y);
                int r = clamp(((rgb >> 16) & 0xFF) + brightness);
                int g = clamp(((rgb >> 8) & 0xFF) + brightness);
                int b = clamp((rgb & 0xFF) + brightness);
                result.setRGB(x, y, (r << 16) | (g << 8) | b);
            }
        }
        return result;
    }

    private int clamp(int value) {
        return Math.max(0, Math.min(value, 255));
    }
}