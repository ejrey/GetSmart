package client;

import java.io.*;
import java.net.Socket;

public class Client {
    private Socket socket;
    private BufferedWriter bufferedWriter; // Used to write to the server
    private BufferedReader bufferedReader; // Used to read from the server

    public Client(Socket socket) {
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            Close(socket, bufferedWriter, bufferedReader);
        }
    }

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 5000);
        Client client = new Client(socket);
        client.ListenForServerMessages();
        client.SendMessageToServer("A client sends a message to the server...");

        new App();
    }

    public void SendMessageToServer(String message) {
        try {
            bufferedWriter.write(message);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            Close(socket, bufferedWriter, bufferedReader);
        }
    }

    // TODO: We may need to implement the Runnable interface like we did for Server.java
    public void ListenForServerMessages() {
        new Thread(() -> {
            String messageFromServer;

            while (socket.isConnected()) {
                try {
                    messageFromServer = bufferedReader.readLine();
                    System.out.println(messageFromServer);
                } catch (IOException e) {
                    Close(socket, bufferedWriter, bufferedReader);
                }
            }
        }).start();
    }

    private void Close(Socket socket, BufferedWriter bufferedWriter, BufferedReader bufferedReader) {
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
