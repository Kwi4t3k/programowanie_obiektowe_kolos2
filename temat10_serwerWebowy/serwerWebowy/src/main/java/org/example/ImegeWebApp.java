package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//Zadanie 1.
//
//Napisz aplikację, która uruchomi serwer webowy, który po połączeniu wyświetli napis “Hello World”.

// Adnotacja @SpringBootApplication jest wygodnym sposobem dodania wszystkich poniższych:
// - @Configuration: Taguje klasę jako źródło definicji beanów dla kontekstu aplikacji.
// - @EnableAutoConfiguration: Mówi Spring Bootowi, aby zaczął dodawać beanów na podstawie ustawień classpath, innych beanów i różnych ustawień właściwości.
// - @ComponentScan: Mówi Springowi, aby szukał innych komponentów, konfiguracji i usług w pakiecie aplikacji, umożliwiając mu znalezienie kontrolerów.

@SpringBootApplication
public class ImegeWebApp {
    // Główna metoda uruchamiająca aplikację Spring Boot.
    // SpringApplication.run() to statyczna metoda, która uruchamia aplikację Spring.
    public static void main(String[] args) {
        SpringApplication.run(ImegeWebApp.class, args);
    }
}
