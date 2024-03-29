package client;

import com.google.gson.Gson;
import middleware.AnswerData;
import middleware.Message;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuestionPage implements ActionListener{

    public static final Gson GSON = new Gson();

    JFrame questionFrame;
    String frameName;
    String labelName;
    String[] buttonAnswers;
    String[] originalAnswers;
    int row;
    int col;

    public QuestionPage(String question, int row, int col, String[] answers) {

        frameName = "";
        labelName = question;
        originalAnswers = answers;
        buttonAnswers = formatAnswers(answers);
        this.row = row;
        this.col = col;

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
        questionTitlePanel.setSize(1000,100);
        JTextArea questionTextArea = new JTextArea(labelName);
        questionTextArea.setLineWrap(true);
        questionTextArea.setFont(new Font("Verdana", Font.PLAIN, 20));
        questionTextArea.setSize(1000,100);
        questionTitlePanel.add(questionTextArea);
        mainPanel.add(questionTitlePanel);
    }

    private String[] formatAnswers(String[] buttonAnswers) {
        String[] tempArray = new String[buttonAnswers.length];
        for(int i = 0; i < buttonAnswers.length; i++) {
            tempArray[i] = "   " + buttonAnswers[i];
        }
        return tempArray;
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if (e.getActionCommand().equals("1")){
            questionFrame.dispose();
            var answerData = new AnswerData(originalAnswers[0], row, col, Client.Instance.getUsername());
            sendAnswer(answerData);
        } else if (e.getActionCommand().equals("2")){
            questionFrame.dispose();
            var answerData = new AnswerData(originalAnswers[1], row, col, Client.Instance.getUsername());
            sendAnswer(answerData);
        } else if (e.getActionCommand().equals("3")){
            questionFrame.dispose();
            var answerData = new AnswerData(originalAnswers[2], row, col, Client.Instance.getUsername());
            sendAnswer(answerData);
        } else if (e.getActionCommand().equals("4")){
            questionFrame.dispose();
            var answerData = new AnswerData(originalAnswers[3], row, col, Client.Instance.getUsername());
            sendAnswer(answerData);
        }
    }

    private void sendAnswer(AnswerData answerData){
        Client.Instance.SendMessageToServer(new Message(Message.Action.SEND_ANSWER_TO_SERVER, GSON.toJson(answerData)));
        showBoard();
    }
    public void showBoard() {
        App.Instance.board.mainFrame.setVisible(true);
    }

}
