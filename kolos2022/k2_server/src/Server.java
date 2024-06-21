import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.stream.Collectors;

// Klasa Server dziedziczy po klasie Thread i reprezentuje serwer
public class Server extends Thread {
    // Gniazdo serwera
    private ServerSocket serverSocket;
    // Lista klientów połączonych z serwerem
    private List<ClientThread> clients = new ArrayList<>();

    // Torba na słowa
    private WordBag wordBag;

    // Konstruktor serwera
    public Server(int port, WordBag wordBag) {
        this.wordBag = wordBag;
        try {
            // Inicjalizacja gniazda serwera
            this.serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Metoda do rozpoczęcia wysyłania danych
    public void startSending() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                // Wysyłanie wiadomości do wszystkich klientów
                broadcast(wordBag.get());
            }
        }, 0, 5000);
    }

    // Metoda uruchamiana przy starcie wątku
    public void run(){
        while(true){
            Socket clientSocket;
            try {
                // Akceptowanie połączeń od klientów
                clientSocket = serverSocket.accept();
                // Tworzenie nowego wątku dla klienta
                ClientThread thread = new ClientThread(clientSocket, this);
                // Dodawanie klienta do listy klientów
                clients.add(thread);
                // Uruchamianie wątku klienta
                thread.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Metoda do usuwania klienta
    public void removeClient(ClientThread client) {
        // Usuwanie klienta z listy klientów
        clients.remove(client);
        System.out.println("removed");
    }

    // Metoda do wysyłania wiadomości do wszystkich klientów
    public void broadcast(String message){
        for(var client : clients)
            // Wysyłanie wiadomości do klienta
            client.send(message);
    }
}
