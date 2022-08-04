package server;

import com.google.gson.Gson;
import middleware.BoardData;
import middleware.ClientData;
import middleware.Message;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

// This class is the main thread that runs the server.
public class Server {
    public static final Gson GSON = new Gson(); // GSON instance to assist us with converting to and from JSON
    public static ArrayList<ClientConnection> ConnectedClients = new ArrayList<>(); // List of all currently connected clients
    public static Questions Questions = new Questions(5,6); // The questions that the clients will be served
    public static BoardData BoardData = new BoardData(); // The current state of the board

    private final ServerSocket serverSocket;

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    // This is the entry point to start the server. If you'd like to provide a custom port, you can provide a
    // program argument. Otherwise, the server will by default host on the port 3000.
    public static void main(String[] args) throws IOException {
        var port = args.length == 0 ? 3000 : Integer.parseInt(args[0]);
        var serverSocket = new ServerSocket(port);
        var server = new Server(serverSocket);
        server.Start();
    }

    // The server starts and begins to listen to new socket connections. If we receive a new connection, let's start a
    // new thread for the connecting client.
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

    // Broadcast sends a message to every single connect client. We use our middleware "Message" class to standardize
    // the way we send messages between the server and client.
    public static void Broadcast(Message message) {
        ConnectedClients.forEach((client -> {
            try {
                client.bufferedWriter.write(Message.Encode(message));
                client.bufferedWriter.newLine();
                client.bufferedWriter.flush();
            } catch (IOException e) {
                client.Close();
            }
        }));
    }

    // Send a message to a specific client. Each client has a unique username that is used as the identifier.
    public static void To(String username, Message message) {
        for (ClientConnection client : ConnectedClients) {
            if (client.username.equals(username)) {
                try {
                    client.bufferedWriter.write(Message.Encode(message));
                    client.bufferedWriter.newLine();
                    client.bufferedWriter.flush();
                } catch (IOException ignore) {
                }
                break;
            }
        }
    }

    // Get the current data of every single client that is currently connected. We use this to send down information
    // such as client usernames and their current scores.
    public static ArrayList<ClientData> GetClientsData() {
        var clientsData = new ArrayList<ClientData>();
        ConnectedClients.forEach((clientConnection -> {
            var data = new ClientData();
            data.username = clientConnection.username;
            data.score = clientConnection.score;
            clientsData.add(data);
        }));
        return clientsData;
    }

    // If everything goes wrong, let's just close and print the stack trace.
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
