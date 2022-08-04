package client;

import com.google.gson.Gson;
import middleware.AnswerData;
import middleware.BoardData;
import middleware.ClientData;
import middleware.Message;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Board implements ActionListener {

    JFrame mainFrame = new JFrame();
    JLabel usernameText;
    JPanel boardPanel = new JPanel(new GridBagLayout());
    ArrayList<JButton> buttons = new ArrayList<>();

    public static final Gson GSON = new Gson();

    public Board(ArrayList<ClientData> clientsData) {
        mainFrame.setSize(1280, 720);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainFrame.add(mainPanel);

        //Top Panel
        JPanel titlePanel = new JPanel();
        JLabel title = new JLabel("Players");
        title.setFont(new Font("Verdana", Font.PLAIN, 50));
        titlePanel.add(title);
        mainPanel.add(titlePanel);

        JPanel usernamePanel = new JPanel();
        titlePanel.add(usernamePanel, BorderLayout.AFTER_LAST_LINE);

        // ADDING A USERNAME
        for (int i = 0; i < clientsData.size(); i++) {
            JLabel usernameText = new JLabel(clientsData.get(i).username);
            usernameText.setFont(new Font("Verdana", Font.PLAIN, 40));
            JPanel player = new JPanel();
            usernamePanel.add(player);
            player.add(usernameText);
        }
        mainPanel.add(usernamePanel);

        //Board Components
//        JPanel boardPanel = new JPanel(new GridBagLayout());
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


        addColumnToBoardPanel(colOne,1, "Networking");
        addColumnToBoardPanel(colTwo , 2, "Sports");
        addColumnToBoardPanel(colThree, 3, "Canada");
        addColumnToBoardPanel(colFour, 4, "Music");
        addColumnToBoardPanel(colFive, 5, "Geography");
        addColumnToBoardPanel(colSix, 6, "Animal Trivia");

        mainPanel.add(boardPanel);
    }

    private void addColumnToBoardPanel(JPanel column, int columnNumber, String columnName) {
//        boardPanel.setLayout(new BoxLayout(boardPanel, BoxLayout.X_AXIS));

        // Column Name
        JLabel columnTitle = new JLabel("" + columnName);
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

            column.add(buttonQuestion);
            buttons.add(buttonQuestion);
            buttonQuestion.addActionListener((ActionListener) this);
        }

        column.setLayout(new BoxLayout(column, BoxLayout.Y_AXIS));
    }

    public void handleButtonCase(BoardData boardData) {
        for(int column=0; column<6; column++) {
            for(int row=0; row<5; row++){
                switch (boardData.buttonStates[column][row]) {
                    case LOCKED:
                        setButtonToLock(column, row);
                        break;
                    case UNLOCKED:
                        setButtonToUnlock(column, row);
                        break;
                    case ANSWERED:
                        setButtonToAnswered(column, row);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    public void setButtonToLock(int column, int row) {
        int chosenColumn = column + 1;
        int chosenRow = (row + 1) * 100;

        String buttonChosen = "" + chosenColumn + "-" + chosenRow;

        for (int i = 0; i < buttons.size(); i++) {
            if (buttons.get(i).getActionCommand().equals(buttonChosen)) {
                Component component = buttons.get(i);
                component.setBackground(Color.RED);
                component.setEnabled(false);
            }
        }
    }

    public void setButtonToUnlock(int column, int row) {
        int chosenColumn = column + 1;
        int chosenRow = (row + 1) * 100;

        String buttonChosen = "" + chosenColumn + "-" + chosenRow;

        for (int i = 0; i < buttons.size(); i++) {
            if (buttons.get(i).getActionCommand().equals(buttonChosen)) {
                Component component = buttons.get(i);
                component.setBackground(null);
                component.setEnabled(true);
            }
        }
    }

    public void setButtonToAnswered(int column, int row) {
        int chosenColumn = column + 1;
        int chosenRow = (row + 1) * 100;

        String buttonChosen = "" + chosenColumn + "-" + chosenRow;

        for (int i = 0; i < buttons.size(); i++) {
            if (buttons.get(i).getActionCommand().equals(buttonChosen)) {
                Component component = buttons.get(i);
                component.setBackground(Color.DARK_GRAY);
                component.setEnabled(false);
            }
        }
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

        int coordinates[] = {col,row};
        AnswerData desiredQuestion = new AnswerData(row, col, Client.Instance.getUsername());
        Client.Instance.SendMessageToServer(new Message(Message.Action.GET_QUESTION, GSON.toJson(desiredQuestion)));
    }

    public void hideBoard() {
        mainFrame.setVisible(false);
    }
}
