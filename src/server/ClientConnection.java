package server;

import middleware.ClientData;
import middleware.Message;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

import static server.Server.*;

public class ClientConnection implements Runnable {

    // Talk from Server to a client.
    public BufferedWriter bufferedWriter; // Used to write to the client
    public BufferedReader bufferedReader; // Used to read data from the client
    public String username;
    private Socket socket;
    public Questions Questions;

    public ClientConnection(Socket socket, Questions qs) {
        this.Questions = qs;
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            ConnectedClients.add(this);
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
                var message = Message.Decode(incomingMessageFromClient);
                switch (message.action) {
                    case SET_USERNAME:
                        var clientData = GSON.fromJson(message.data, ClientData.class);

                        username = clientData.username;
                        To(username, new Message(Message.Action.SEND_TO_WAITING_ROOM, GSON.toJson(clientData)));

                        Broadcast(new Message(Message.Action.WAITING_ROOM_UPDATE_USERNAMES, GSON.toJson(Server.GetClientsData())));
                        break;
                    case START_GAME:
                        Broadcast(new Message(Message.Action.SEND_TO_BOARD, GSON.toJson(Server.GetClientsData())));
                        break;
                    case GET_QUESTION:
                        //this query from the client passes in an int array of the column then the row of a desired question
                        //The response to the client is a Question object.
                        var rowAndColumn = GSON.fromJson(message.data, Integer[].class);
                        To(username, new Message(Message.Action.SEND_TO_QUESTION_PAGE, GSON.toJson(Questions.getQuestion(rowAndColumn[0],rowAndColumn[1]))));

                    case REQUEST_QUESTION_COLUMNS:
                        To(username, new Message(Message.Action.SEND_TO_QUESTION_BOARD, GSON.toJson(new String[]{
                                "Column1", "Column2", "Column3", "Column4", "Column5", "Column6",
                    }
                    )));
                        break;
                    case IGNORE:
                        break;
                }
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
