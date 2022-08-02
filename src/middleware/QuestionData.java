package middleware;

public class QuestionData {
    public enum ButtonState {
        LOCKED,
        UNLOCKED,
        ANSWERED,
    }

    public String question;
    public int row;
    public int col;
    public String[] answers;
}
