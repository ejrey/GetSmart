package client;

import javax.swing.*;
import java.awt.*;

public class QuestionPage {

    public QuestionPage() {

        String frameName = "Test Page";
        String buttonText = "Click me";
        String labelName = "label text here";

        // Create frame and define the size, holds components
        JFrame questionFrame = new JFrame(frameName);
        questionFrame.setSize(1280, 720);
        // Defines behaviour of frame upon being closed
        questionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // JTextField allows editing of a single line of text
        JTextField questionText = new JTextField(50);
        questionFrame.add(questionText, BorderLayout.NORTH);

        // JLabel displays information (can be text and/or image) and does not accept user input
        JLabel answerText = new JLabel(labelName, SwingConstants.CENTER);
        questionFrame.add(answerText, BorderLayout.CENTER);

        // JButton allows users to “ push”/“click” to signify an action
        JButton button = new JButton(buttonText);
        questionFrame.add(button, BorderLayout.SOUTH);

        // By default, it is set to false, need to set it to true in order to see
        questionFrame.setVisible(true);
    }

    public static void main(String[] args){
        new QuestionPage();
    }

}
