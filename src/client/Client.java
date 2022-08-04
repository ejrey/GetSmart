package client;

import com.google.gson.reflect.TypeToken;
import middleware.BoardData;
import middleware.ClientData;
import middleware.Message;
import middleware.QuestionData;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

import static server.Server.GSON;

public class Client implements Runnable  {
    public static Client Instance;
    private Socket socket;
    private BufferedWriter bufferedWriter; // Used to write to the server
    private BufferedReader bufferedReader; // Used to read from the server
    private String username;

    public String getUsername() {
        return username;
    }

    public Client(Socket socket) {
        Instance = this;
        try {
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
                        App.GoToWaitingRoom();
                        break;
                    case SEND_TO_BOARD:
                        ArrayList<ClientData> boardClientData = GSON.fromJson(message.data, new TypeToken<ArrayList<ClientData>>(){}.getType());
                        App.GoToBoard(boardClientData);
                        break;
                    case WAITING_ROOM_UPDATE_USERNAMES:
                        ArrayList<ClientData> waitingClientData = GSON.fromJson(message.data, new TypeToken<ArrayList<ClientData>>(){}.getType());
                        App.UpdateWaitingRoomUserNames(waitingClientData);
                        break;
                    case QUESTION_DATA_RECEIVED:
                        var questionData = GSON.fromJson(message.data, QuestionData.class);
                        System.out.println(questionData);
                        App.GoToQuestionPage(questionData.question, questionData.row, questionData.col, questionData.answers);
                        break;
                    case UPDATE_BOARD:
                        // Listen to whenever the server tells us to update this board.
                        var boardData = GSON.fromJson(message.data, BoardData.class);
                        // Re-render the jeopardy board and people scores
                        // button status, usernames and score
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
