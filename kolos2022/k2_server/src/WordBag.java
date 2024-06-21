import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// Klasa WordBag przechowuje listę słów i umożliwia losowe wybieranie słów
public class WordBag {
    private List<String> words = new ArrayList<>(); // Lista przechowująca słowa
    private Random rand = new Random(); // Obiekt Random do generowania liczb losowych

    // Metoda populate() wypełnia listę słów danymi z pliku
    public void populate() {
        String path = "slowa.txt"; // Ścieżka do pliku ze słowami
        try (Stream<String> stream = Files.lines(Paths.get(path))) {
            // Przetwarzanie każdej linii pliku na listę słów
            words = stream.collect(Collectors.toList());
        } catch (IOException e) {
            // Obsługa wyjątków
            e.printStackTrace();
        }
    }

    // Metoda get() zwraca losowe słowo z listy
    public String get() {
        return words.get(rand.nextInt(words.size()));
    }
}
