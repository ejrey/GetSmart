package server;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

// Implements runnable in order to use Java threads ( @Override run() )
public class ClientHandler implements Runnable {
    public static ArrayList<ClientHandler> Clients = new ArrayList<>();

    private Socket socket;
    private BufferedWriter bufferedWriter; // Used to write to the client
    private BufferedReader bufferedReader; // Used to read data from the client

    public ClientHandler(Socket socket) {
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            Clients.add(this);
            Broadcast("The server is broadcasting a message to the clients...");
        } catch (IOException e) {
            Close(socket, bufferedWriter, bufferedReader);
        }
    }

    @Override
    public void run() {
        String incomingMessageFromClient;

        while (socket.isConnected()) {
            try {
                incomingMessageFromClient = bufferedReader.readLine();
                Broadcast(incomingMessageFromClient);
            } catch (IOException e) {
                Close(socket, bufferedWriter, bufferedReader);
                break;
            }
        }
    }

    // Sends a message to every connected client.
    private void Broadcast(String message) {
        Clients.forEach((client -> {
            try {
                client.bufferedWriter.write(message);
                client.bufferedWriter.newLine();
                client.bufferedWriter.flush();
            } catch (IOException e) {
                Close(socket, bufferedWriter, bufferedReader);
            }
        }));
    }

    private void Close(Socket socket, BufferedWriter bufferedWriter, BufferedReader bufferedReader) {
        Clients.remove(this);
        try {
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }

            if (bufferedReader != null) {
                bufferedReader.close();
            }

            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
