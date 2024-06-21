package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

// Adnotacja @SpringBootTest jest używana do oznaczenia klasy testowej, która powinna uruchomić cały kontekst aplikacji Spring
@SpringBootTest
class DemoApplicationTests {

	// Adnotacja @Test jest używana do oznaczenia metody jako metody testowej
	@Test
	// Test sprawdzający, czy kontekst aplikacji Spring poprawnie się ładuje
	void contextLoads() {
	}

	//W tym kodzie masz klasę testową DemoApplicationTests, która uruchamia cały kontekst aplikacji Spring
	// dzięki adnotacji @SpringBootTest. Metoda contextLoads() jest oznaczona adnotacją @Test, co oznacza, że
	// jest to metoda testowa. Ta metoda testuje, czy kontekst aplikacji Spring poprawnie się ładuje. Jeśli
	// kontekst nie ładuje się poprawnie, test nie powiedzie się. Jest to przydatne do szybkiego sprawdzenia,
	// czy podstawowa konfiguracja aplikacji jest prawidłowa. Pamiętaj, że testy jednostkowe i integracyjne są
	// kluczowym elementem tworzenia niezawodnych aplikacji Spring Boot. Dzięki nim możesz szybko wykryć i
	// naprawić błędy w swoim kodzie. Możesz dodać więcej metod testowych do tej klasy, aby przetestować różne
	// aspekty swojej aplikacji. Możesz również użyć różnych narzędzi i bibliotek do pisania testów, takich
	// jak Mockito, JUnit, AssertJ itp. Pamiętaj, że dobrze napisane testy powinny być małe, niezależne, łatwe
	// do zrozumienia i powinny dokładnie określać, co mają testować. Dobre praktyki pisania testów mogą
	// znacznie ułatwić utrzymanie i rozwijanie Twojej aplikacji.
}
