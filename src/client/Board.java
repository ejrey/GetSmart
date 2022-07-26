package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Board implements ActionListener {

    public Board() {

        JFrame gameBoard = new JFrame("GetSmart");
//        gameBoard.setSize(1280, 720);
        gameBoard.setExtendedState(JFrame.MAXIMIZED_BOTH);
        gameBoard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameBoard.setVisible(true);

        JPanel mainPanel = new JPanel();
        JPanel colOne = new JPanel();
        JPanel colTwo = new JPanel();
        JPanel colThree = new JPanel();
        JPanel colFour = new JPanel();
        JPanel colFive = new JPanel();
        JPanel colSix = new JPanel();

        mainPanel.add(colOne);
        mainPanel.add(colTwo);
        mainPanel.add(colThree);
        mainPanel.add(colFour);
        mainPanel.add(colFive);
        mainPanel.add(colSix);

        // For at least one column

        addColumnToMainPanel(gameBoard, mainPanel, colOne, 1);
        addColumnToMainPanel(gameBoard, mainPanel, colTwo, 2);
        addColumnToMainPanel(gameBoard, mainPanel, colThree, 3);
        addColumnToMainPanel(gameBoard, mainPanel, colFour, 4);
        addColumnToMainPanel(gameBoard, mainPanel, colFive, 5);
        addColumnToMainPanel(gameBoard, mainPanel, colSix, 6);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(50,175,0,100));



    }

    private void addColumnToMainPanel(JFrame gameBoard, JPanel mainPanel, JPanel column, int colNumber) {
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));

        JLabel columnTitle = new JLabel("Col " + colNumber);
        columnTitle.setFont(new Font("Verdana", Font.PLAIN, 20));
        column.add(columnTitle);
        int pointValue = 100;
        for (int i = 0; i < 5; i++) {
//            JButton buttonQuestion = new JButton("C" + colNumber + ": " + i);
            JLabel buttonLabel = new JLabel("" + pointValue);
            JButton buttonQuestion = new JButton(colNumber + "-" + pointValue);
            pointValue = pointValue + 100;
            buttonQuestion.setMaximumSize(new Dimension (250, 100));
            buttonQuestion.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.DARK_GRAY, 20),
                    BorderFactory.createEmptyBorder(20, 0, 20, 0)));
            column.add(buttonQuestion);
            buttonQuestion.addActionListener((ActionListener) this);
        }
        column.setLayout(new BoxLayout(column, BoxLayout.Y_AXIS));
        gameBoard.add(mainPanel);
//        column.setBackground(Color.BLACK);
//        column.setBorder(BorderFactory.createEmptyBorder(50,45,50,45));

    }

    // To identify which button is pressed
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("1-100")) {
            String[] test = {"yo","yo","yo","yo"};
            new QuestionPage("Hi", "1", test);
        } else if (e.getActionCommand().equals("Exit")) {
//            System.exit(0);
        }
        System.out.println(e.getActionCommand());
    }

}
