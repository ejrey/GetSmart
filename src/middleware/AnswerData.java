package middleware;

public class AnswerData {
    public String answer;
    public int row;
    public int col;
    public String playerUsername;

    public AnswerData(String answer, int row, int col, String playerUsername){
        this.answer = answer;
        this.row = row;
        this.col = col;
        this.playerUsername = playerUsername;
    }

}
