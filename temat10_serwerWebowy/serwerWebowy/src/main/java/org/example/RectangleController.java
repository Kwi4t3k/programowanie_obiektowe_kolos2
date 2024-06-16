package org.example;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

//Zadanie 1.
//
//Napisz aplikację, która uruchomi serwer webowy, który po połączeniu wyświetli napis “Hello World”.

@RestController
public class RectangleController {
    @GetMapping("/")
    public String hello(){
        return "Hello World";
    }

//    Zadanie 2.
//
//Utwórz klasę Rectangle składającą się z całkowitych parametrów: położenia x, położenia y, szerokości, wysokości oraz koloru wyrażonego napisem. Wygeneruj konstruktor, akcesory oraz mutatory do wszystkich pól.
//Napisz kontroler REST RectangleController posiadający metodę, której wywołanie spowoduje wyświetlenie obiektu Rectangle zmapowanego na JSON.

    @GetMapping("/rectangleCall")
    public Rectangle rectangleCall(){
        return new Rectangle(50, 30, 100, 200, "blue");
    }

    //Zadanie 3.
    //
    //W kontrolerze:
    //
    //Stwórz prywatną listę prostokątów.
    //
    //Napisz metodę, która dodaje prostokąt o określonych w kodzie parametrach.
    //
    //Napisz metodę, która zwróci listę prostokątów zmapowaną na JSON.
    //
    //Napisz metodę, która wygeneruje napis zawierający kod SVG z prostokątami znajdującymi się na liście.

    private List<Rectangle> rectangles = new ArrayList<>();

    @GetMapping("/add1")
    public void addRectangle(){
        rectangles.add(new Rectangle(5,8,150,300, "red"));
    }
    @GetMapping("/add2")
    public void addRectangle1(){
        rectangles.add(new Rectangle(60,150,150,300, "blue"));
    }

    @GetMapping("/returnRectangleList")
    public List<Rectangle> returnRectangleList(){
        return rectangles;
    }

    @GetMapping("/rectanglesSVG")
    public String rectanglesSVG(){
        StringBuilder sb = new StringBuilder();
        sb.append("<svg width=\"300\" height=\"500\" xmlns=\"http://www.w3.org/2000/svg\">\n");

        for (Rectangle rect : rectangles){
            sb.append(String.format("<rect width=\"%d\" height=\"%d\" x=\"%d\" y=\"%d\" fill=\"%s\"/>", rect.getWidth(), rect.getHeight(), rect.getX(), rect.getY(), rect.getColor()));
        }

        sb.append("</svg>\n");
        return sb.toString();
    }

    //Zadanie 4.
    //
    //Zmodyfikuj metodę dodającą prostokąt, by odpowiadała na żądanie HTTP POST. Niech metoda przyjmuje prostokąt, który zostanie zdefiniowany w ciele żądania HTTP.

    @PostMapping("/addRectangle")
    public void addRectangle(@RequestBody Rectangle rectangle){
        rectangles.add(rectangle);

        //curl -X POST http://localhost:8080/addRectangle -H "Content-Type: application/json" -d '{"x":10,"y":20,"width":100,"height":200,"color":"green"}'
        //w konsoli BASHA
    }

    //Zadanie 5.
    //
    //Napisz metody:
    //
    //GET z argumentem typu int,  zwracającą prostokąt w liście o podanym indeksie.
    //
    //PUT z argumentem typu int i argumentem typu Rectangle, modyfikującą istniejący na liście pod tym indeksem prostokąt na prostokąt przekazany argumentem.
    //
    //DELETE  z argumentem typu int, usuwającą prostokąt z listy z miejsca o podanym indeksie.

    @GetMapping("/GET/{index}")
    public Rectangle GET(@PathVariable int index){
        if (index >= 0 && index < rectangles.size()) {
            return rectangles.get(index);
        } else {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
    }

    //    http://localhost:8080/GET/1  to w przeglądarce
    //    curl -X GET http://localhost:8080/GET/0

//    @GetMapping("GET/{index}")
//    public Rectangle GET(@PathVariable Long index) {
//        if(index < 0 || index > rectangles.size()) {
//            throw new IndexOutOfBoundsException();
//        }
//        return rectangles.get(index.intValue());
//    }

    @PutMapping("/PUTrect/{index}")
    public void PUT(@PathVariable int index, @RequestBody Rectangle rectangle){
        if (index >= 0 && index < rectangles.size()) {
            rectangles.set(index, rectangle);
        } else {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
    }
    //  curl -X PUT http://localhost:8080/PUTrect/0 -H "Content-Type: application/json" -d '{"x":10,"y":20,"width":100,"height":200,"color":"green"}'

    @DeleteMapping("DELETErect/{index}")
    public void DELETE(@PathVariable int index){
        if (index >= 0 && index < rectangles.size()) {
            rectangles.remove(index);
        } else {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
    }
    //  curl -X DELETE http://localhost:8080/DELETErect/0
}