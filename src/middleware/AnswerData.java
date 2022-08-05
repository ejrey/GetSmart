package middleware;

public class AnswerData {
    public String answer;
    public int row;
    public int col;
    public String username;

    public AnswerData(String answer, int row, int col, String username){
        this.answer = answer;
        this.row = row;
        this.col = col;
        this.username = username;
    }
    public AnswerData(int row, int col, String username){
        this.answer = null;
        this.row = row;
        this.col = col;
        this.username = username;
    } // Use this version to request a Question from the server.

}
