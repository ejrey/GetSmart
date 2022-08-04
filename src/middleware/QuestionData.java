package middleware;

public class QuestionData {
    public enum ButtonState {
        LOCKED,
        UNLOCKED,
        ANSWERED,
    }

    public QuestionData(int col, int row, String question, String[] answers) {
        this.col = col;
        this.row = row;
        this.question = question;
        this.answers = answers;
    }

    public String question;
    public int row;
    public int col;
    public String[] answers;
}
