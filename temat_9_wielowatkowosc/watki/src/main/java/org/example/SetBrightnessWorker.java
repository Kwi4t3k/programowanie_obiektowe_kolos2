package org.example;

import java.awt.image.BufferedImage;

import static java.lang.Math.clamp;

// Klasa implementująca interfejs Runnable, która będzie wykorzystywana do tworzenia wątków
public class SetBrightnessWorker implements Runnable{
    private int begin, end; // Zmienne określające początek i koniec fragmentu obrazu, który ma być przetwarzany
    private int brightness; // Wartość, o którą ma być zwiększona jasność
    private BufferedImage image; // Obraz, który ma być przetwarzany

    // Konstruktor klasy
    public SetBrightnessWorker(int begin, int end, int brightness, BufferedImage image) {
        this.begin = begin;
        this.end = end;
        this.brightness = brightness;
        this.image = image;
    }

    // Metoda run() z interfejsu Runnable, która zostanie wykonana po uruchomieniu wątku
    @Override
    public void run() {
        for (int y = begin; y < end; y++){ // Iteracja przez wiersze obrazu
            for (int x = 0; x < image.getWidth(); x++) { // Iteracja przez kolumny obrazu

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
}
