package server;

import middleware.AnswerData;
import middleware.ClientData;
import middleware.Message;
import middleware.QuestionData;

import java.io.*;
import java.net.Socket;

import static server.Server.*;

// This class represents a current client connection to a server running on a separate thread.
public class ClientConnection implements Runnable {
    public BufferedWriter bufferedWriter; // Used to write to the client
    public BufferedReader bufferedReader; // Used to read data from the client
    public String username; // Client's username
    public Integer score = 0; // Client's score
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
    // the client socket. This is also the place that we handle the shared object within our game.
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
                        // The question that is being requested.
                        var questionRequest = GSON.fromJson(message.data, AnswerData.class);
                        Question q = Questions.tryGetQuestion(questionRequest.col, questionRequest.row, questionRequest.username);

                        // If null, this means that the question is locked, and we shouldn't proceed any further.
                        if (q == null){
                            Broadcast(new Message(Message.Action.UPDATE_BOARD, GSON.toJson(BoardData)));
                            break;
                        }

                        // If we reach this point, the question is locked to the specific username answering the question.
                        QuestionData questionData = new QuestionData(q.getColumn(), q.getRow(), q.getQuestion(), q.getAnswers());
                        // Send the question to the specific user.
                        To(username, new Message(Message.Action.QUESTION_DATA_RECEIVED, GSON.toJson(questionData)));
                        // Update the button as locked.
                        BoardData.buttonStates[q.getColumn()][q.getRow()] = middleware.BoardData.ButtonState.LOCKED;
                        // Let everyone know that the button is now locked.
                        Broadcast(new Message(Message.Action.UPDATE_BOARD, GSON.toJson(BoardData)));
                        break;

                    case SEND_ANSWER_TO_SERVER: // Client to Server, occurs when client wants to attempt a guess at the right answer
                        //check if the answer is correct, if so award this player points based on the question row.
                        //update the question to be unlocked if wrong or answered if correct, and broadcast the new board state.
                        //TODO: the above
                        var guess = GSON.fromJson(message.data, AnswerData.class);
                        Question questionToAnswer = Questions.getQuestion(guess.col, guess.row,guess.username);

                        if(questionToAnswer.isAnswerCorrect(guess.username, guess.answer)){
                            //the right answer
                            //give the player points
                            score += (questionToAnswer.getRow()+1)*100;
                            BoardData.clients = Server.GetClientsData();
                            BoardData.buttonStates[guess.col][guess.row] = middleware.BoardData.ButtonState.ANSWERED;
                            Broadcast(new Message(Message.Action.UPDATE_BOARD, GSON.toJson(BoardData)));

                            if (BoardData.IsGameOver())
                                Broadcast(new Message(Message.Action.GAME_FINISHED, GSON.toJson(BoardData.clients)));
                        }
                        else{
                            //wrong answer
                            //Unlock the question so others can try it.
                            BoardData.buttonStates[guess.col][guess.row] = middleware.BoardData.ButtonState.UNLOCKED;
                            Broadcast(new Message(Message.Action.UPDATE_BOARD, GSON.toJson(BoardData)));
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
