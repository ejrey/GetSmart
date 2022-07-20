package client;

import javax.swing.*;
import java.awt.*;

public class QuestionPage {

    public QuestionPage(String question, String questionNumber, String[] answers) {

        String frameName = questionNumber;
        String labelName = question;
        String[] buttonAnswers = answers;
        
        buttonAnswers = formatAnswers(buttonAnswers);

        JFrame questionFrame = new JFrame(frameName);
        questionFrame.setSize(1280, 720);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        questionFrame.add(mainPanel);

        questionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel questionTitlePanel = new JPanel();
        mainPanel.add(questionTitlePanel);

        questionTitlePanel.setBorder(BorderFactory.createEmptyBorder(50,10,10,10));
        questionTitlePanel.setSize(200,200);

        JLabel questionLabel = new JLabel(labelName);
        questionLabel.setFont(new Font("Verdana", Font.PLAIN, 50));
        questionTitlePanel.add(questionLabel);

        JPanel questionAnswerPanel = new JPanel();
        questionAnswerPanel.setLayout(new BoxLayout(questionAnswerPanel, BoxLayout.Y_AXIS));
        questionAnswerPanel.setBorder(BorderFactory.createEmptyBorder(50,50,100,50));

        JPanel questionAnswerOnePanel = new JPanel();
        questionAnswerOnePanel.setLayout((new BoxLayout(questionAnswerOnePanel, BoxLayout.X_AXIS)));
        questionAnswerOnePanel.setBorder(BorderFactory.createEmptyBorder(25,10,25,10));

        JPanel questionAnswerTwoPanel = new JPanel();
        questionAnswerTwoPanel.setLayout((new BoxLayout(questionAnswerTwoPanel, BoxLayout.X_AXIS)));
        questionAnswerTwoPanel.setBorder(BorderFactory.createEmptyBorder(25,10,25,10));

        JPanel questionAnswerThreePanel = new JPanel();
        questionAnswerThreePanel.setLayout((new BoxLayout(questionAnswerThreePanel, BoxLayout.X_AXIS)));
        questionAnswerThreePanel.setBorder(BorderFactory.createEmptyBorder(25,10,25,10));

        JPanel questionAnswerFourPanel = new JPanel();
        questionAnswerFourPanel.setLayout((new BoxLayout(questionAnswerFourPanel, BoxLayout.X_AXIS)));
        questionAnswerFourPanel.setBorder(BorderFactory.createEmptyBorder(25,10,25,10));

        questionAnswerPanel.setSize(200, 100);

        JButton buttonOne = new JButton("1");
        buttonOne.setLayout(new FlowLayout(FlowLayout.LEFT, 25, 5));
        JLabel answerOne = new JLabel(buttonAnswers[0]);
        answerOne.setMaximumSize(new Dimension(800, 25));

        JButton buttonTwo = new JButton("2");
        JLabel answerTwo = new JLabel(buttonAnswers[1]);
        answerTwo.setMaximumSize(new Dimension(800, 25));

        JButton buttonThree = new JButton("3");
        JLabel answerThree = new JLabel(buttonAnswers[2]);
        answerThree.setMaximumSize(new Dimension(800, 25));

        JButton buttonFour = new JButton("4");
        JLabel answerFour = new JLabel(buttonAnswers[3]);
        answerFour.setMaximumSize(new Dimension(800, 25));

        questionAnswerOnePanel.add(buttonOne);
        questionAnswerOnePanel.add(answerOne);

        questionAnswerTwoPanel.add(buttonTwo);
        questionAnswerTwoPanel.add(answerTwo);

        questionAnswerThreePanel.add(buttonThree);
        questionAnswerThreePanel.add(answerThree);

        questionAnswerFourPanel.add(buttonFour);
        questionAnswerFourPanel.add(answerFour);

        questionAnswerPanel.add(questionAnswerOnePanel);
        questionAnswerPanel.add(questionAnswerTwoPanel);
        questionAnswerPanel.add(questionAnswerThreePanel);
        questionAnswerPanel.add(questionAnswerFourPanel);

        mainPanel.add(questionAnswerPanel);

        questionFrame.setVisible(true);
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

}
