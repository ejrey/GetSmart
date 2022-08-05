package server;

public class Question {
    private int row; // row the question appears on
    private int column; // row the question appears on
    private final String question;
    private final String[] answers;
    private final String correctAnswer;
    private String currentAnswerer = "unlocked"; //When false, one process may get this question's info.
    //When true, no thread may get this question's info


    public Question(int row, int column, String question, String[] answers, String correctAnswer) {
        this.row = row;
        this.column = column;
        this.question = question;
        this.answers = answers;
        this.correctAnswer = correctAnswer;
    }

    //tryGetQuestion
    //if this thread has this question's lock or no one does, return the question
    public Question tryGetQuestion(String threadId) {
        if(currentAnswerer.equals(threadId) || currentAnswerer.equals("unlocked")){
            currentAnswerer = threadId;
            return this;
        }
        return null;
    }

    public Boolean isAnswerCorrect(String threadId, String answer) {
        if(currentAnswerer.equals(threadId) ){
            if (answer.equals(correctAnswer)){
                currentAnswerer = "unlocked"; //after a right or wrong answer, lose the lock.
                return true;
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
