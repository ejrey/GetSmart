package server;

import client.Board;
import middleware.AnswerData;
import middleware.BoardData;
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
    public Integer score;
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
                        var questionRequest = GSON.fromJson(message.data, AnswerData.class);
                        Question q = Questions.tryGetQuestion(questionRequest.col, questionRequest.row, questionRequest.username);
                        if (q == null){
                            //the question is locked. Simply broadcast the board's current state.
                            Broadcast(new Message(Message.Action.UPDATE_BOARD, GSON.toJson(BoardData)));
                            break;
                        }
                        // Otherwise, send the question to user
                        To(username, new Message(Message.Action.QUESTION_DATA_RECEIVED, GSON.toJson(q)));

                        //Then, set button state to LOCKED since a user is now in the question
                        BoardData.buttonStates[q.getColumn()][q.getRow()] = middleware.BoardData.ButtonState.LOCKED;
                        // Update everyone's board
                        Broadcast(new Message(Message.Action.UPDATE_BOARD, GSON.toJson(BoardData)));
                        break;

                    case SEND_ANSWER_TO_SERVER: // Client to Server, occurs when client wants to attempt a guess at the right answer
                        //check if the answer is correct, if so award this player points based on the question row.
                        //update the question to be unlocked if wrong or answered if correct, and broadcast the new board state.
                        //TODO: the above
                        var guess = GSON.fromJson(message.data, AnswerData.class);
                        var  player = BoardData.getClient(guess.username);
                        if(player == null){
                            //If the player doesn't exist, they can never get a question right. unlock the question and broadcast the update
                            BoardData.buttonStates[guess.col][guess.row] = middleware.BoardData.ButtonState.UNLOCKED;
                            Broadcast(new Message(Message.Action.UPDATE_BOARD, GSON.toJson(BoardData)));
                            break;
                        }
                        Question questionToAnswer = Questions.getQuestion(guess.col, guess.row,guess.username);
                        if(questionToAnswer.isAnswerCorrect(guess.username, guess.answer)){
                            //the right answer
                            //give the player points
                            player.gainPoints(questionToAnswer.getRow());
                            //Set the question to Answered, unclickable
                            BoardData.buttonStates[guess.col][guess.row] = middleware.BoardData.ButtonState.ANSWERED;
                            Broadcast(new Message(Message.Action.UPDATE_BOARD, GSON.toJson(BoardData)));
                            System.out.println("RIGHT");
                            System.out.println(BoardData);
                        }
                        else{
                            //wrong answer
                            //Unlock the question so others can try it.
                            BoardData.buttonStates[guess.col][guess.row] = middleware.BoardData.ButtonState.UNLOCKED;
                            Broadcast(new Message(Message.Action.UPDATE_BOARD, GSON.toJson(BoardData)));
                            System.out.println("WRONG");
                            System.out.println(BoardData);
                        }

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
