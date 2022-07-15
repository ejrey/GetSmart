package server;

import java.io.*;
import java.net.Socket;

import static server.Server.Broadcast;
import static server.Server.ConnectedClients;

public class ClientConnection implements Runnable {

    // Talk from Server to a client.
    public BufferedWriter bufferedWriter; // Used to write to the client
    public BufferedReader bufferedReader; // Used to read data from the client
    private Socket socket;

    public ClientConnection(Socket socket) {
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            ConnectedClients.add(this);
            Broadcast("The server is broadcasting a message to the clients...");
        } catch (IOException e) {
            Close();
        }
    }

    // Loop to get message from Client that is running on a thread.
    @Override
    public void run() {
        String incomingMessageFromClient;

        while (socket.isConnected()) {
            try {
                incomingMessageFromClient = bufferedReader.readLine();
                Broadcast(incomingMessageFromClient);
            } catch (IOException e) {
                Close();
                break;
            }
        }
    }

    public void Close() {
        ConnectedClients.remove(this);
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
