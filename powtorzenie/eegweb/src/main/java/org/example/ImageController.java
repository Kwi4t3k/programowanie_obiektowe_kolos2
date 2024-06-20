package org.example;

// W drugim projekcie w aplikacji webowej napisz kontroler, który po podaniu w urlu nazwy użytkownika oraz numeru elektrody wyszuka odpowiedni wiersz w bazie i zwróci stronę z nazwą użytkownika, numerem elektrody oraz obrazkiem.

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Controller // Adnotacja wskazująca, że ta klasa jest kontrolerem w Spring MVC
public class ImageController {

    @GetMapping("/image") // Adnotacja mapująca żądania HTTP GET na konkretną metodę
    public String image(Model model) throws IOException { // Metoda obsługująca żądanie GET
        String base64; // Zmienna do przechowywania obrazu w formacie Base64

        // Utworzenie obiektu BufferedReader do odczytu danych z pliku
        BufferedReader reader = new BufferedReader(new FileReader("/tmp/data.txt"));

        base64 = reader.readLine(); // Odczytanie pierwszej linii z pliku, która zawiera obraz w formacie Base64

        model.addAttribute("image", base64); // Dodanie obrazu do modelu, który zostanie przekazany do widoku

        return "eegimage"; // Zwrócenie nazwy widoku, który ma być wyrenderowany
    }
}