package client;

import javax.swing.*;
import java.awt.*;

public class Board {

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

    }

    private void addColumnToMainPanel(JFrame gameBoard, JPanel mainPanel, JPanel column, int colNumber) {
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));

        JLabel columnTitle = new JLabel("Col " + colNumber);
        columnTitle.setFont(new Font("Verdana", Font.PLAIN, 20));
        column.add(columnTitle);
        for (int i = 0; i < 5; i++) {
            JButton buttonQuestion = new JButton("C" + colNumber + ": " + i);
            buttonQuestion.setMaximumSize(new Dimension (250, 100));
            buttonQuestion.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.CYAN, 20),
                    BorderFactory.createEmptyBorder(20, 0, 20, 10)));
            column.add(buttonQuestion);
        }
        column.setLayout(new BoxLayout(column, BoxLayout.Y_AXIS));
        gameBoard.add(mainPanel);
//        column.setBackground(Color.BLACK);
        column.setBorder(BorderFactory.createEmptyBorder(50,45,50,45));
    }
}
