package server;

public class Message {
    public enum Action {
        SET_USERNAME,
    }

    public Action action;
    public String message;
    private static final String delimiter = ":";

    public static String Encode(Action action, String message) {
        return action.toString().concat(delimiter).concat(message).concat("\n");
    }

    public static Message Decode(String encoded) {
        var split = encoded.split(delimiter, 2);
        var message = new Message();
        message.action = Action.valueOf(split[0]);
        message.message = split[1];
        return message;
    }
}
