package com.example.demo;

import org.springframework.web.bind.annotation.RestController;

// Adnotacja @RestController informuje Springa, że ta klasa jest kontrolerem REST
// Kontroler REST to komponent, który przyjmuje żądania HTTP od klienta i zwraca odpowiedź HTTP
@RestController
public class SpringController {
    // Tutaj możesz dodać metody obsługujące różne ścieżki i operacje HTTP

    //W tym kodzie tworzysz nowy kontroler REST w Springu. Kontroler REST to komponent, który obsługuje
    // żądania HTTP od klienta i zwraca odpowiedź HTTP. Adnotacja @RestController informuje Springa, że ta
    // klasa jest kontrolerem REST. Możesz dodać metody do tej klasy, które obsługują różne ścieżki i operacje
    // HTTP, takie jak GET, POST, PUT, DELETE itp. Każda z tych metod będzie odpowiadać na określone żądanie
    // HTTP wysłane na określoną ścieżkę. Metody te mogą zwracać dane, które następnie są konwertowane na
    // format JSON i wysyłane z powrotem do klienta jako odpowiedź HTTP. Pamiętaj, że musisz skonfigurować
    // routing w Springu, aby określić, które ścieżki są obsługiwane przez który kontroler. Możesz to zrobić
    // za pomocą adnotacji @RequestMapping lub @GetMapping, @PostMapping itp. na poziomie metody. Możesz
    // również użyć adnotacji @PathVariable i @RequestParam do obsługi zmiennych ścieżki i parametrów żądania
    // odpowiednio. Pamiętaj, że kontrolery REST są kluczowym elementem tworzenia API REST w Springu. Dzięki
    // nim możesz tworzyć potężne, skalowalne i elastyczne usługi internetowe.
}
