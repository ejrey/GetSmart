package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class Server {

    public static ArrayList<ClientConnection> ConnectedClients = new ArrayList<>();

    private final ServerSocket serverSocket;

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public static void main(String[] args) throws IOException {
        var serverSocket = new ServerSocket(3000); // Might want to pass in port as command arg
        var server = new Server(serverSocket);
        server.Start();
    }

    public void Start() {
        try {
            while (!serverSocket.isClosed()) {
                var socket = serverSocket.accept();
                var clientConnection = new ClientConnection(socket);
                System.out.println("Client has connected.");

                var thread = new Thread(clientConnection);
                thread.start();
            }
        } catch (IOException e) {
            Close();
        }
    }

    // Sends a message to every connected client.
    public static void Broadcast(String message) {
        ConnectedClients.forEach((client -> {
            try {
                client.bufferedWriter.write(message);
                client.bufferedWriter.newLine();
                client.bufferedWriter.flush();
            } catch (IOException e) {
                client.Close();
            }
        }));
    }

    private void Close() {
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
