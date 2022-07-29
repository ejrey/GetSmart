package client;

import middleware.ClientData;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Board implements ActionListener {

    JFrame mainFrame = new JFrame();
    JLabel usernameText;

    public Board() {
        mainFrame.setSize(1280, 720);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);

//        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel mainPanel = new JPanel();
//        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainFrame.add(mainPanel);

        //Top Panel
        JPanel titlePanel = new JPanel();
        JLabel title = new JLabel("Players");
        title.setFont(new Font("Verdana", Font.PLAIN, 50));
        titlePanel.add(title);
//        mainPanel.add(titlePanel, BorderLayout.NORTH);
//        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        mainPanel.add(titlePanel);

        JPanel usernamePanel = new JPanel();
        titlePanel.add(usernamePanel, BorderLayout.AFTER_LAST_LINE);
        usernamePanel.setLayout(new BoxLayout(usernamePanel, BoxLayout.Y_AXIS));

//        usernameText = new JLabel("");
//        usernameText.setFont(new Font("Verdana", Font.PLAIN, 20));
//        mainPanel.add(usernamePanel);
//        usernamePanel.add(usernameText);


        // FOR DEBUGGING: to test and add 5 players
//        JPanel playerOne = new JPanel();
//        JPanel playerTwo = new JPanel();
//        JPanel playerThree = new JPanel();
//        JPanel playerFour = new JPanel();
//        JPanel playerFive = new JPanel();
//
//        JLabel oneText = new JLabel("Ben: 0 |");
//        oneText.setFont(new Font("Verdana", Font.PLAIN, 20));
//        JLabel twoText = new JLabel("Matthew: 0 |");
//        twoText.setFont(new Font("Verdana", Font.PLAIN, 20));
//        JLabel threeText = new JLabel("Daniel: 0 |");
//        threeText.setFont(new Font("Verdana", Font.PLAIN, 20));
//        JLabel fourText = new JLabel("Randy: 0 |");
//        fourText.setFont(new Font("Verdana", Font.PLAIN, 20));
//        JLabel fiveText = new JLabel("Eric: 0 |");
//        fiveText.setFont(new Font("Verdana", Font.PLAIN, 20));
//
//        usernamePanel.add(playerOne);
//        usernamePanel.add(playerTwo);
//        usernamePanel.add(playerThree);
//        usernamePanel.add(playerFour);
//        usernamePanel.add(playerFive);
//
//        playerOne.add(oneText);
//        playerTwo.add(twoText);
//        playerThree.add(threeText);
//        playerFour.add(fourText);
//        playerFive.add(fiveText);

        //FOR DEBUGGING: To test and add 100 players
//        for (int i = 0; i < 100; i++) {
//            JPanel player = new JPanel();
//            JLabel text = new JLabel("" + i);
//            text.setFont(new Font("Verdana", Font.PLAIN, 20));
//            usernamePanel.add(player);
//            player.add(text);
//        }


        //Board Components
        JPanel boardPanel = new JPanel(new GridBagLayout());
        JPanel colOne = new JPanel();
        JPanel colTwo = new JPanel();
        JPanel colThree = new JPanel();
        JPanel colFour = new JPanel();
        JPanel colFive = new JPanel();
        JPanel colSix = new JPanel();

        boardPanel.add(colOne);
        boardPanel.add(colTwo);
        boardPanel.add(colThree);
        boardPanel.add(colFour);
        boardPanel.add(colFive);
        boardPanel.add(colSix);

        addColumnToBoardPanel(boardPanel, colOne, 1);
        addColumnToBoardPanel(boardPanel, colTwo, 2);
        addColumnToBoardPanel(boardPanel, colThree, 3);
        addColumnToBoardPanel(boardPanel, colFour, 4);
        addColumnToBoardPanel(boardPanel, colFive, 5);
        addColumnToBoardPanel(boardPanel, colSix, 6);

        mainPanel.add(boardPanel);
    }

    private void addColumnToBoardPanel(JPanel boardPanel, JPanel column, int columnNumber) {
//        boardPanel.setLayout(new BoxLayout(boardPanel, BoxLayout.X_AXIS));


        // Column Name
        JLabel columnTitle = new JLabel("Col " + columnNumber);
        columnTitle.setFont(new Font("Verdana", Font.PLAIN, 20));
        columnTitle.setHorizontalAlignment(JLabel.CENTER);
        column.add(columnTitle);


        // Assigning buttons onto columns
        int pointValue = 100;
        for (int i = 0; i < 5; i++) {;
            JButton buttonQuestion = new JButton("" + pointValue);
            buttonQuestion.setActionCommand(columnNumber + "-" + pointValue);
            pointValue = pointValue + 100;

            // To adjust size of buttons
            buttonQuestion.setFont(new Font("Arial", Font.PLAIN, 70));
            buttonQuestion.setMaximumSize(new Dimension (1280, 720));
            buttonQuestion.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.DARK_GRAY, 10),
                    BorderFactory.createEmptyBorder(0, 30, 0, 30)));

//            buttonQuestion.setMaximumSize(new Dimension (400, 400));
//            buttonQuestion.setBorder(BorderFactory.createCompoundBorder(
//                    BorderFactory.createLineBorder(Color.DARK_GRAY, 10),
//                    BorderFactory.createEmptyBorder(0, 50, 0, 50)));
            column.add(buttonQuestion);
            buttonQuestion.addActionListener((ActionListener) this);
        }

        column.setLayout(new BoxLayout(column, BoxLayout.Y_AXIS));

    }

    public void UpdateBoardUsernames(ArrayList<ClientData> clients) {
        StringBuilder stringBuilder = new StringBuilder();
        clients.forEach((clientData -> {
            stringBuilder.append(clientData.username);
            stringBuilder.append(" ");
        }));
        usernameText.setText(stringBuilder.toString());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Starting the indexes
        String buttonPressed = e.getActionCommand();
        int col = Integer.parseInt(String.valueOf(buttonPressed.charAt(0))) - 1;
        int row = Integer.parseInt(String.valueOf(buttonPressed.charAt(2))) - 1;

        System.out.println("Col = " + col + " Row = " + row);
        System.out.println(e.getActionCommand());
//        App.GoToQuestionPage("hello", row, col, "HELLO", Client.this);
    }
}
