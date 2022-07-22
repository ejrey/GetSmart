package client;

import middleware.Message;

import java.io.*;
import java.net.Socket;

public class Client implements Runnable  {
    private Socket socket;
    private BufferedWriter bufferedWriter; // Used to write to the server
    private BufferedReader bufferedReader; // Used to read from the server
    private String username;

    public Client(Socket socket) {
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            Close(socket, bufferedWriter, bufferedReader);
        }
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
        String incomingMessageFromServer;

        while (socket.isConnected()) {
            try {
                incomingMessageFromServer = bufferedReader.readLine();

                var message = Message.Decode(incomingMessageFromServer);
                switch (message.action) {
                    case SEND_TO_WAITING_ROOM:
                        username = message.data;
                        System.out.println("Setting username:");
                        System.out.println(username);
                        System.out.println("This user should be sent to the waiting room.");
                        break;
                    case IGNORE:
                        break;
                }
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
