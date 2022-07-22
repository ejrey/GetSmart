package middleware;

public class Message {
    public enum Action {
        IGNORE,
        SET_USERNAME,
        SEND_TO_WAITING_ROOM,
    }

    public Action action;
    public String data;
    private static final String delimiter = ":";

    public static String Encode(Action action, String message) {
        return action.name().concat(delimiter).concat(message).concat("\n");
    }

    public static Message Decode(String encoded) {
        var message = new Message();
        message.action = Action.IGNORE;
        message.data = "";

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
