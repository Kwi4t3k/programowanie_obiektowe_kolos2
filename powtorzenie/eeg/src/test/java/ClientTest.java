import org.example.client.Client;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Base64;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClientTest {

    @ParameterizedTest
    @CsvFileSource(resources = "/test.csv", numLinesToSkip = 1)
    void testSendData(String username, String filepath, String expectedImageBase64) throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        ServerThread serverThread = new ServerThread(latch);
        Thread thread = new Thread(serverThread);
        thread.start();

        latch.await(); // Ensure server is ready before client sends data

        Client.sendData(username, filepath);

        thread.join(); // Wait for server to finish processing

        String receivedImageBase64 = serverThread.getReceivedImageBase64();
        assertEquals(expectedImageBase64, receivedImageBase64);
    }

    static class ServerThread implements Runnable {
        private final CountDownLatch latch;
        private String receivedImageBase64;

        ServerThread(CountDownLatch latch) {
            this.latch = latch;
        }

        @Override
        public void run() {
            try (ServerSocket serverSocket = new ServerSocket(1234)) {
                latch.countDown(); // Signal that the server is ready

                try (Socket clientSocket = serverSocket.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

                    String username = in.readLine(); // Read username
                    StringBuilder receivedData = new StringBuilder();
                    String line;
                    while ((line = in.readLine()) != null) {
                        if ("bye".equalsIgnoreCase(line)) {
                            break;
                        }
                        receivedData.append(line).append("\n");
                    }

                    // Simulate processing and generating base64 image
                    receivedImageBase64 = Base64.getEncoder().encodeToString(receivedData.toString().getBytes());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String getReceivedImageBase64() {
            return receivedImageBase64;
        }
    }
}