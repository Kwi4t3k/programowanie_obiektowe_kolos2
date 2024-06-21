package org.example;

//Zadanie 2.
//
//Utwórz klasę Rectangle składającą się z całkowitych parametrów: położenia x, położenia y, szerokości, wysokości oraz koloru wyrażonego napisem. Wygeneruj konstruktor, akcesory oraz mutatory do wszystkich pól.
//Napisz kontroler REST RectangleController posiadający metodę, której wywołanie spowoduje wyświetlenie obiektu Rectangle zmapowanego na JSON.

// Klasa Rectangle reprezentująca prostokąt
public class Rectangle {
    // Współrzędne x i y prostokąta
    private int x, y;
    // Szerokość i wysokość prostokąta
    private int width, height;
    // Kolor prostokąta
    private String color;

    // Konstruktor klasy Rectangle
    public Rectangle(int x, int y, int width, int height, String color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    // Getter dla współrzędnej x
    public int getX() {
        return x;
    }

    // Setter dla współrzędnej x
    public void setX(int x) {
        this.x = x;
    }

    // Getter dla współrzędnej y
    public int getY() {
        return y;
    }

    // Setter dla współrzędnej y
    public void setY(int y) {
        this.y = y;
    }

    // Getter dla szerokości
    public int getWidth() {
        return width;
    }

    // Setter dla szerokości
    public void setWidth(int width) {
        this.width = width;
    }

    // Getter dla wysokości
    public int getHeight() {
        return height;
    }

    // Setter dla wysokości
    public void setHeight(int height) {
        this.height = height;
    }

    // Getter dla koloru
    public String getColor() {
        return color;
    }

    // Setter dla koloru
    public void setColor(String color) {
        this.color = color;
    }
}
