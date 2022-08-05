package server;

public class Question {
    private int row; // row the question appears on
    private int column; // row the question appears on
    private final String question;
    private final String[] answers;
    private final String correctAnswer;

    // Null => the question isn't being answered by anyone
    // USERNAME => the username of the person answering the question
    private String currentAnswerer = null;

    public Question(int row, int column, String question, String[] answers, String correctAnswer) {
        this.row = row;
        this.column = column;
        this.question = question;
        this.answers = answers;
        this.correctAnswer = correctAnswer;
    }

    //tryGetQuestion
    //if this thread has this question's lock or no one does, return the question
    public Question tryGetQuestion(String userName) {
        if(currentAnswerer == null || currentAnswerer.equals(userName)) {
            currentAnswerer = userName;
            return this;
        }
        return null;
    }

    public Boolean isAnswerCorrect(String username, String answer) {
        if(currentAnswerer.equals(username) ){
            if (answer.equals(correctAnswer)){
                currentAnswerer = null;
                return true;
            } else {
                currentAnswerer = null;
                return false;
            }
        }
        return false;
    }

    //Individual getters and setters mostly for debugging:
    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public String getQuestion() {
        return question;
    }

    public String[] getAnswers() {
        return answers;
    }
}
