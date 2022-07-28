package client;

import middleware.ClientData;
import middleware.Message;
import middleware.QuestionData;

import java.io.*;
import java.net.Socket;

import static server.Server.GSON;

public class Client implements Runnable  {
    private App app;
    private Socket socket;
    private BufferedWriter bufferedWriter; // Used to write to the server
    private BufferedReader bufferedReader; // Used to read from the server

    public String getUsername() {
        return username;
    }

    private String username;

    public Client(App app, Socket socket) {
        try {
            this.app = app;
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            Close(socket, bufferedWriter, bufferedReader);
        }
    }

    public void SendMessageToServer(Message message) {
        try {
            bufferedWriter.write(Message.Encode(message));
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
                        var clientData = GSON.fromJson(message.data, ClientData.class);
                        username = clientData.username;
                        System.out.println(username);
                        app.GoToWaitingRoom();
                        break;
                    case QUESTION_DATA_RECEIVED:
                        var questionData = GSON.fromJson(message.data, QuestionData.class);
                        app.GoToQuestionPage(questionData.question, questionData.row, questionData.col, questionData.answers, this);
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
