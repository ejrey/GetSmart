package client;

import java.io.*;
import java.net.Socket;

public class Client implements Runnable  {
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
        var socket = new Socket("localhost", 5000);
        var client = new Client(socket);
        client.SendMessageToServer("A client sends a message to the server...");

        var thread = new Thread(client);
        thread.start();

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

    @Override
    public void run() {
        String incomingMessageFromClient;

        while (socket.isConnected()) {
            try {
                incomingMessageFromClient = bufferedReader.readLine();
                System.out.println(incomingMessageFromClient);
            } catch (IOException e) {
                Close(socket, bufferedWriter, bufferedReader);
            }
        }
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
