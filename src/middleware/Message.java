package middleware;

public class Message {
    public enum Action {
        IGNORE, // empty string
        SET_USERNAME, // ClientData
        SEND_TO_WAITING_ROOM, // ClientData
        WAITING_ROOM_UPDATE_USERNAMES, // ArrayList of ClientData
        REQUEST_QUESTION_COLUMNS,
        SEND_TO_QUESTION_BOARD, //Server-To-Client, sent once to set up question board's column names.

        GET_QUESTION, //Client-to-Server, used when client clicks a question button. invokes SEND_TO_QUESTION_PAGE
        SEND_TO_QUESTION_PAGE, //Server-To-Client, sent once to set up question page with one question's Data.

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
        var messageParsed = split[1].replaceAll("\\s|\\r|\\n","");

        message.action = Action.valueOf(actionParsed);
        message.data = messageParsed;
        return message;
    }
}
