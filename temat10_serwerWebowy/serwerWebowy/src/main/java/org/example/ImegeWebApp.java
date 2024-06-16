package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//Zadanie 1.
//
//Napisz aplikację, która uruchomi serwer webowy, który po połączeniu wyświetli napis “Hello World”.

@SpringBootApplication
public class ImegeWebApp {
    public static void main(String[] args) {
        SpringApplication.run(ImegeWebApp.class, args);
    }
}