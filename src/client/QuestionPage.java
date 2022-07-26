// QuestionPage takes in a string that is a question, a string that is the question number, and then an array of answers
// Array size should be 4 & and should have only 4 answers.
// If we want to change the amount of answers then we will need to change some numbers but it's not a huge issue

package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuestionPage implements ActionListener{

    JFrame questionFrame;

    public QuestionPage(String question, String questionNumber, String[] answers) {
        String frameName = questionNumber;
        String labelName = question;
        String[] buttonAnswers = formatAnswers(answers);

        questionFrame = new JFrame(frameName);

        questionFrame.setSize(1280, 720);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        questionFrame.add(mainPanel);
        questionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        createQuestionPanel(labelName, mainPanel);
        createMainAnswerPanel(buttonAnswers, mainPanel);

        questionFrame.setVisible(true);
    }

    private void createMainAnswerPanel(String[] buttonAnswers, JPanel mainPanel) {
        JPanel questionAnswerPanel = new JPanel();
        questionAnswerPanel.setLayout(new BoxLayout(questionAnswerPanel, BoxLayout.Y_AXIS));
        questionAnswerPanel.setBorder(BorderFactory.createEmptyBorder(50,50,100,50));

        for(int i = 0; i<buttonAnswers.length; i++){
            String number = Integer.toString(i+1);
                createAnswerPanel(questionAnswerPanel, buttonAnswers, number, i);
        }

        questionAnswerPanel.setSize(200, 100);
        mainPanel.add(questionAnswerPanel);
    }

    private void createAnswerPanel(JPanel mainAnswerPanel, String[] buttonAnswers, String s, int i) {
        JPanel answerPanel = new JPanel();
        answerPanel.setLayout((new BoxLayout(answerPanel, BoxLayout.X_AXIS)));
        answerPanel.setBorder(BorderFactory.createEmptyBorder(25, 10, 25, 10));
        JButton button = new JButton(s);
        button.addActionListener(this);
        JLabel answer = new JLabel(buttonAnswers[i]);
        answer.setMaximumSize(new Dimension(800, 25));
        answerPanel.add(button);
        answerPanel.add(answer);
        mainAnswerPanel.add(answerPanel);
    }

    private void createQuestionPanel(String labelName, JPanel mainPanel) {
        JPanel questionTitlePanel = new JPanel();
        questionTitlePanel.setBorder(BorderFactory.createEmptyBorder(50,10,10,10));
        questionTitlePanel.setSize(200,200);
        JLabel questionLabel = new JLabel(labelName);
        questionLabel.setFont(new Font("Verdana", Font.PLAIN, 50));
        questionTitlePanel.add(questionLabel);
        mainPanel.add(questionTitlePanel);
    }

    private String[] formatAnswers(String[] buttonAnswers) {
        String[] tempArray = new String[buttonAnswers.length];
        for(int i = 0; i < buttonAnswers.length; i++) {
            tempArray[i] = "   " + buttonAnswers[i];
        }
        return tempArray;
    }

    public static void main(String[] args){
        String[] arr = new String[]{"Yo", "Hi", "Sup", "Hey"};
        new QuestionPage("Question here", "1", arr);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if (e.getActionCommand().equals("1")){
            questionFrame.dispose();
            emptyFunction();
        } else if (e.getActionCommand().equals("2")){
            questionFrame.dispose();
            emptyFunction();
        } else if (e.getActionCommand().equals("3")){
            questionFrame.dispose();
            emptyFunction();
        } else if (e.getActionCommand().equals("4")){
            questionFrame.dispose();
            emptyFunction();
        }
    }

    private void emptyFunction(){

    }

}
