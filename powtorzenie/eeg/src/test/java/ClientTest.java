import org.example.client.Client;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Base64;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Klasa testowa dla klienta
public class ClientTest {

    // Test parametryczny z danymi z pliku CSV
    @ParameterizedTest
    @CsvFileSource(resources = "/test.csv", numLinesToSkip = 1)
    void testSendData(String username, String filepath, String expectedImageBase64) throws Exception {
        CountDownLatch latch = new CountDownLatch(1); // Zasuwa do synchronizacji wątków
        ServerThread serverThread = new ServerThread(latch); // Wątek serwera
        Thread thread = new Thread(serverThread); // Uruchomienie wątku serwera
        thread.start();

        latch.await(); // Czekanie na gotowość serwera

        Client.sendData(username, filepath); // Wysyłanie danych przez klienta

        thread.join(); // Czekanie na zakończenie przetwarzania przez serwer

        // Sprawdzenie, czy otrzymany obrazek w formacie Base64 jest taki sam jak oczekiwany
        String receivedImageBase64 = serverThread.getReceivedImageBase64();
        assertEquals(expectedImageBase64, receivedImageBase64);
    }

    // Klasa wewnętrzna reprezentująca wątek serwera
    static class ServerThread implements Runnable {
        private final CountDownLatch latch; // Zasuwa do synchronizacji
        private String receivedImageBase64; // Otrzymany obrazek w formacie Base64

        ServerThread(CountDownLatch latch) {
            this.latch = latch;
        }

        // Metoda run() jest wywoływana, gdy wątek jest uruchamiany
        @Override
        public void run() {
            try (ServerSocket serverSocket = new ServerSocket(1234)) { // Tworzenie gniazda serwera
                latch.countDown(); // Sygnalizacja gotowości serwera

                try (Socket clientSocket = serverSocket.accept(); // Akceptowanie połączenia od klienta
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) { // Czytnik do odczytywania danych

                    String username = in.readLine(); // Odczytanie nazwy użytkownika
                    StringBuilder receivedData = new StringBuilder(); // Otrzymane dane
                    String line;
                    while ((line = in.readLine()) != null) { // Odczytywanie danych
                        if ("bye".equalsIgnoreCase(line)) {
                            break;
                        }
                        receivedData.append(line).append("\n");
                    }

                    // Symulacja przetwarzania i generowania obrazka w formacie Base64
                    receivedImageBase64 = Base64.getEncoder().encodeToString(receivedData.toString().getBytes());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Getter dla otrzymanego obrazka w formacie Base64
        String getReceivedImageBase64() {
            return receivedImageBase64;
        }
    }
}
