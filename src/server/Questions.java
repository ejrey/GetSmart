package server;

public class Questions {
    public Question[][] currentQuestions;//starting from bottom left (0,0), i is column, j is row
    public int rows;
    public int columns;
    Questions(int columns,int rows ){
        currentQuestions = new Question[columns][rows];
    }
    public Question getQuestion(int column, int row){
        return currentQuestions[column][row];
    }

    public void initializeQuestions(){
        //TODO: pass in actual questions
        for(int column=0; column<columns; column++)
            for(int row=0; row<rows; row++)
                currentQuestions[column][row] = new Question(row, column, "This is a test question.Can you get it right?", new String[]{"wrong1","wrong2","wrong3","rightAnswer"}, "rightAnswer");
    }
}
