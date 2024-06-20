package org.example;

import java.io.*;
import java.net.Socket;

public class ConnectionThread extends Thread {
    Socket socket;
    PrintWriter writer;

    public ConnectionThread(String address, int port) throws IOException {
        socket = new Socket(address, port);
    }

    @Override
    public void run() {
        try (InputStream inputStream = socket.getInputStream();
             OutputStream outputStream = socket.getOutputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream)))
        {

            writer = new PrintWriter(outputStream, true);

            String rawMessage;

            while ((rawMessage = reader.readLine()) != null){
                ClientReciver.reciveWord(rawMessage);
            }

        } catch (IOException e) {
            System.err.println("Connection error: " + e.getMessage());
        }
    }
}
