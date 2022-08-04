package middleware;

public class Message {
    public enum Action {
        IGNORE, // empty string
        SET_USERNAME, // ClientData
        SEND_TO_WAITING_ROOM, // ClientData
        WAITING_ROOM_UPDATE_USERNAMES, // ArrayList of ClientData

        GET_QUESTION, //Client-to-Server, used when client clicks a question button. invokes SEND_TO_QUESTION_PAGE
        SEND_TO_BOARD, // ArrayList of ClientData of usernames
        START_GAME, // empty
        SEND_ANSWER_TO_SERVER,
        QUESTION_DATA_RECEIVED, // From server to client, client should render question page from body data
        UPDATE_BOARD, // From server to client, client should rerender button states and scores
    }

    public Action action;
    public String data;
    private static final String delimiter = ":";

    public Message(Action action, String data) {
        this.action = action;
        this.data = data;
    }

    public static String Encode(Message message) {
        return message.action.name().concat(delimiter).concat(message.data).concat("\n");
    }

    public static Message Decode(String encoded) {
        var message = new Message(Action.IGNORE, "");

        var split = encoded.split(delimiter, 2);
        if (split.length != 2)
            return message;

        // Hopefully this doesn't mess with JSON, but we want to clean up any weird whitespace.
        var actionParsed = split[0].replaceAll("\\s|\\r|\\n","");
        var messageParsed = split[1];

        message.action = Action.valueOf(actionParsed);
        message.data = messageParsed;
        return message;
    }
}
