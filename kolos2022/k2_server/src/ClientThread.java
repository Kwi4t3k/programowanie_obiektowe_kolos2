import java.io.*;
import java.net.Socket;

// Klasa ClientThread dziedziczy po klasie Thread
public class ClientThread extends Thread {
    // Metoda zwracająca gniazdo (socket)
    public Socket getSocket() {
        return socket;
    }

    private Socket socket;
    private PrintWriter writer;
    private Server server;

    // Konstruktor klasy ClientThread
    public ClientThread(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    // Metoda run jest wywoływana, gdy wątek jest uruchamiany
    public void run(){
        try {
            // Tworzymy strumienie wejściowe i wyjściowe
            InputStream input = socket.getInputStream();
            OutputStream output = socket.getOutputStream();

            // Tworzymy czytnik i pisarz
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            writer = new PrintWriter(output, true);

            // Czytamy dane ze strumienia wejściowego, dopóki są dostępne
            while (reader.readLine() != null) { }

            System.out.println("closed");

            // Usuwamy klienta z serwera
            server.removeClient(this);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Metoda do wysyłania wiadomości
    public void send(String message){
        writer.println(message);
    }

}
