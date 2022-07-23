package server;

import com.google.gson.Gson;
import middleware.Message;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class Server {
    public static final Gson GSON = new Gson();
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

    // Sends a message to a specific user.
    public static void To(String username, Message.Action action, String data) {
        for (ClientConnection client : ConnectedClients) {
            if (client.username.equals(username)) {
                try {
                    client.bufferedWriter.write(Message.Encode(action, data));
                    client.bufferedWriter.newLine();
                    client.bufferedWriter.flush();
                } catch (IOException ignore) {
                }
                break;
            }
        }
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
