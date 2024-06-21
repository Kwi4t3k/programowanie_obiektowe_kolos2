package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Adnotacja @SpringBootApplication jest używana do oznaczenia klasy konfiguracyjnej, która uruchamia aplikację Spring Boot
@SpringBootApplication
public class SpringApp {

	public static void main(String[] args) {
		// Metoda run() klasy SpringApplication uruchamia aplikację Spring Boot
		SpringApplication.run(SpringApp.class, args);

		// Tworzenie nowego obiektu klasy Client
		Client client = new Client();
		// Uruchomienie klienta z adresem "localhost" i portem 5001
		client.start("localhost", 5001);
	}

}
