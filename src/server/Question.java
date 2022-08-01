package server;
import com.google.gson.Gson;

public class Question {
    private int row; // row the question appears on
    private int column; // row the question appears on
    private String question;
    private String[] answers;

    private String correctAnswer;

    private String currentAnswerer = ""; //When false, one process may get this question's info.
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
    public String tryGetQuestion(String threadId) {
        if(currentAnswerer.equals(threadId) || currentAnswerer.equals("")){
            currentAnswerer = threadId;
            Gson gson = new Gson();
            String json = gson.toJson(this);
            System.out.println(json);
            return json;
        }
        return null;
    }

    public Boolean isAnswerCorrect(String threadId, String answer) {
        if(currentAnswerer.equals(threadId) ){
            if (answer.equals(correctAnswer)){
                currentAnswerer = ""; //after a right or wrong answer, lose the lock.
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

    public void setQuestion(String question) {
        this.question = question;
    }

    public String[] getAnswers() {
        return answers;
    }

    public void setAnswers(String[] answers) {
        this.answers = answers;
    }

    //make sure setCorrectAnswer is ONLY used when initializing the right answers
    //TODO: maybe refactor the right answers to be completely separate from the Question class.
    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}
