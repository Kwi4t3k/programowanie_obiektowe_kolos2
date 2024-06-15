package org.example;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static java.lang.Math.clamp;

public class ImageProcessor {
    // Napisz klasę posiadającą:
    //      - metodę, która otrzyma ścieżkę i wczyta obraz do pola klasy typu BufferedImage,
    //      - metodę, która zapisze obraz z tego pola do podanej ścieżki.

    private BufferedImage image; // Pole klasy przechowujące obraz

    // Metoda wczytująca obraz z podanej ścieżki do pola klasy
    public void readImage(String path) throws IOException {
        image = ImageIO.read(new File(path));
    }

    // Metoda zapisująca obraz z pola klasy do podanej ścieżki
    public void writeImage(String path) throws IOException {
        ImageIO.write(image, "jpg", new File(path));
    }

    //Dodaj metodę, która zwiększy jasność obrazu o podaną stałą.
    // Metoda zwiększająca jasność obrazu o podaną wartość
    public void setBrightness(int brightness){
        for (int y = 0; y < image.getHeight(); y++){
            for (int x = 0; x < image.getWidth(); x++) {

                int rgb = image.getRGB(x, y); // Pobranie wartości RGB piksela

                // Rozdzielenie wartości RGB na składowe R, G, B
                int b=rgb&0xFF;
                int g=(rgb&0xFF00)>>8;
                int r=(rgb&0xFF0000)>>16;

                // Zwiększenie jasności każdej składowej o podaną wartość
                b = clamp(b+brightness,0,255);
                g = clamp(g+brightness,0,255);
                r = clamp(r+brightness,0,255);

                // Złożenie składowych R, G, B z powrotem w wartość RGB
                rgb=(r<<16)+(g<<8)+b;

                // Ustawienie nowej wartości RGB dla piksela
                image.setRGB(x,y,rgb);
            }
        }
    }

    //Dodaj metodę, która wykona to samo działanie, dzieląc zadanie na określoną liczbę wątków. Liczbę wątków należy powiązać z liczbą dostępnych rdzeni procesora. Porównaj czas wykonania obu metod.
    // Metoda zwiększająca jasność obrazu o podaną wartość, wykorzystująca wielowątkowość
    public void setBrightness2(int brightness) throws InterruptedException {
        int threadsCount = Runtime.getRuntime().availableProcessors(); // Pobranie liczby dostępnych rdzeni procesora
        Thread[] threads;
        threads = new Thread[threadsCount];
        int chunk = image.getHeight()/threadsCount; // Podział obrazu na fragmenty

        for (int i = 0; i < threadsCount; i++) {
            int begin = i * chunk;
            int end;

            if (i == threadsCount - 1){
                end = image.getHeight();
            } else {
                end = (i + 1) * chunk;
            }

            threads[i] = new Thread(new SetBrightnessWorker(begin, end, brightness, image));
            threads[i].start();
        }
        for (int i = 0; i < threadsCount; i++) {
            threads[i].join();
        }
    }

    //Dodaje metodę, która wykona to samo działanie w oparciu o pulę wątków. Jeden wątek powinien obsłużyć jeden wiersz obrazu. Dodaj czas wykonania tej metody do porównania.
    // Metoda zwiększająca jasność obrazu o podaną wartość, wykorzystująca pulę wątków
    public void setBrightnessThreadPool(int brightness){
        int threadsCount = Runtime.getRuntime().availableProcessors(); // Pobranie liczby dostępnych rdzeni procesora
        ExecutorService executor = Executors.newFixedThreadPool(threadsCount); // Utworzenie puli wątków

        for (int i = 0; i < image.getHeight(); i++) {
            final int y = i;
            executor.execute(() -> {
                for(int x=0;x<image.getWidth();x++){
                    int rgb = image.getRGB(x,y);
                    int b=rgb&0xFF;
                    int g=(rgb&0xFF00)>>8;
                    int r=(rgb&0xFF0000)>>16;
                    b=clamp(b+brightness,0,255);
                    g=clamp(g+brightness,0,255);
                    r=clamp(r+brightness,0,255);
                    rgb=(r<<16)+(g<<8)+b;
                    image.setRGB(x,y,rgb);
                }
            });
        }
        executor.shutdown();
        try{
            boolean b = executor.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    //Napisz metodę, która w oparciu o pulę wątków obliczy histogram wybranego kanału obrazu.
    // Metoda obliczająca histogram wybranego kanału obrazu
    public int[] calculateHistogram(String channel) {
        int width = image.getWidth();
        int height = image.getHeight();
        int[] histogram = new int[256]; // Tablica przechowująca histogram

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int rgb = image.getRGB(i, j); // Pobranie wartości RGB piksela
                int value;

                // Wybór składowej R, G lub B w zależności od wybranego kanału
                switch (channel) {
                    case "red":
                        value = (rgb >> 16) & 0xFF;
                        break;
                    case "green":
                        value = (rgb >> 8) & 0xFF;
                        break;
                    case "blue":
                        value = rgb & 0xFF;
                        break;
                    default:
                        throw new IllegalArgumentException("Zły kanał: " + channel);
                }
                histogram[value]++; // Zwiększenie wartości histogramu dla danego kanału
            }
        }
        return histogram;
    }

    //Napisz metodę, która przyjmie histogram obrazu i wygeneruje obraz przedstawiający wykres tego histogramu.
    // Metoda generująca obraz przedstawiający wykres histogramu
    public void generateHistogramImage(int[] histogram, String path) throws IOException {
        int width = 256;
        int height = 100;
        BufferedImage histogramImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB); // Utworzenie nowego obrazu

        int max = Arrays.stream(histogram).max().getAsInt(); // Pobranie maksymalnej wartości histogramu

        for (int i = 0; i < width; i++) {
            int value = (int) (((double) histogram[i] / max) * height); // Obliczenie wysokości słupka na wykresie

            for (int j = 0; j < height; j++) {
                if (j < height - value) {
                    histogramImage.setRGB(i, j, 0xFFFFFF); // Ustawienie koloru tła
                } else {
                    histogramImage.setRGB(i, j, 0x000000); // Ustawienie koloru słupka
                }
            }
        }
        ImageIO.write(histogramImage, "png", new File(path)); // Zapisanie obrazu do pliku
    }
}
