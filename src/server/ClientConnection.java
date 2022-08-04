package server;

import middleware.ClientData;
import middleware.Message;

import java.io.*;
import java.net.Socket;

import static server.Server.*;

// This class represents a current client connection to a server running on a separate thread.
public class ClientConnection implements Runnable {
    public BufferedWriter bufferedWriter; // Used to write to the client
    public BufferedReader bufferedReader; // Used to read data from the client
    public String username; // Client's username
    public Integer score; // Client's score
    private Socket socket;

    // We pass in the socket connection of the client and setup bufferedWriter and bufferedReader to facilitate
    // communication to and from the server.
    public ClientConnection(Socket socket) {
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Let the server know that we have a new client to track.
            ConnectedClients.add(this);
        } catch (IOException e) {
            Close();
        }
    }

    // The thread that is running for the connected client. It's an infinite while loop that listens to message from
    // the client socket.
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

                        var rowAndColumn = GSON.fromJson(message.data, int[].class);
                        var row = rowAndColumn[1];
                        var column = rowAndColumn[0];
                        var questions = new Questions(6,5);
                        Question q = questions.getQuestion(column, row);


                        // Send the question to user
                        To(username, new Message(Message.Action.QUESTION_DATA_RECEIVED, GSON.toJson(q)));
                        //Set button state to LOCKED
                        BoardData.buttonStates[column][row] = middleware.BoardData.ButtonState.LOCKED;
                        // Set the column and row of the button pressed by user
                        BoardData.setColumn(column);
                        BoardData.setRow(row);
                        // Send data to board
                        Broadcast(new Message(Message.Action.UPDATE_BOARD, GSON.toJson(BoardData)));

//                        if (BoardData.buttonStates[column][row] == middleware.BoardData.ButtonState.UNLOCKED) {
//                            To(username, new Message(Message.Action.QUESTION_DATA_RECEIVED, GSON.toJson(q)));
//                            BoardData.buttonStates[column][row] = middleware.BoardData.ButtonState.LOCKED;
//                            Broadcast(new Message(Message.Action.UPDATE_BOARD, GSON.toJson(BoardData)));
//                        }
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

    // If everything goes wrong let's close down the client correctly and print the stack trace.
    public void Close() {
        // We need to remove the client from the list of active clients.
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
