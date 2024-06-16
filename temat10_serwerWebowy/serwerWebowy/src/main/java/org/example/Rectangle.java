package org.example;

//Zadanie 2.
//
//Utwórz klasę Rectangle składającą się z całkowitych parametrów: położenia x, położenia y, szerokości, wysokości oraz koloru wyrażonego napisem. Wygeneruj konstruktor, akcesory oraz mutatory do wszystkich pól.
//Napisz kontroler REST RectangleController posiadający metodę, której wywołanie spowoduje wyświetlenie obiektu Rectangle zmapowanego na JSON.

public class Rectangle {
    private int x, y, width, height;
    private String color;

    public Rectangle(int x, int y, int width, int height, String color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
