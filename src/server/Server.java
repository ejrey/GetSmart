package server;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
    private final ServerSocket serverSocket;

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public static void main(String[] args) throws IOException {
        var serverSocket = new ServerSocket(5000); // Might want to pass in port as command arg
        var server = new Server(serverSocket);
        server.Start();
    }

    public void Start() {
        try {
            while (!serverSocket.isClosed()) {
                var socket = serverSocket.accept();
                var clientHandler = new ClientHandler(socket);
                System.out.println("Client has connected.");

                var thread = new Thread(clientHandler);
                thread.start();
            }
        } catch (IOException e) {
            Close();
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
