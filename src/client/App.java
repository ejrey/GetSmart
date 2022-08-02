package client;

import com.google.gson.Gson;
import middleware.BoardData;
import middleware.ClientData;
import middleware.Message;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class App implements ActionListener {

    public static void main(String[] args) {}
    public static final Gson GSON = new Gson();
    private static final App Instance = new App();

    JFrame titleScreen = new JFrame();
    WaitingRoom waitingRoom;
    Board board;
    JTextField usernameField;

    public App() {
        // Main Frame with Title Screen
        titleScreen.setSize(1280, 720);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        titleScreen.add(mainPanel);

        // Top Section containing the title
        JPanel topPanel = new JPanel();
        mainPanel.add(topPanel);
        topPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 25, 50));
        JLabel title = new JLabel("GetSmart");
        title.setFont(new Font("Verdana", Font.PLAIN, 100));
        topPanel.add(title);


        // Mid-Section with Username/Port Number Selection
        JPanel usernamePanel = new JPanel();
        JPanel ipPanel = new JPanel();
        JPanel portPanel = new JPanel();
        mainPanel.add(usernamePanel);
        mainPanel.add(ipPanel);
        mainPanel.add(portPanel);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        JLabel nameLabel = new JLabel("Enter a username:    ");
        usernameField = new JTextField(50);
        usernamePanel.add(nameLabel);
        usernamePanel.add(usernameField);
        usernamePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel ipLabel = new JLabel("Enter an IP address to the server:    ");
        JTextField ipField = new JTextField(50);
        ipPanel.add(ipLabel);
        ipPanel.add(ipField);
        ipPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel portLabel = new JLabel("Enter a port number to the server:    ");
        JTextField namePortField = new JTextField(10);
        portPanel.add(portLabel);
        portPanel.add(namePortField);
        portPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        usernamePanel.setLayout(new BoxLayout(usernamePanel, BoxLayout.X_AXIS));
        ipPanel.setLayout(new BoxLayout(ipPanel, BoxLayout.X_AXIS));
        portPanel.setLayout(new BoxLayout(portPanel, BoxLayout.X_AXIS));

        usernameField.setMaximumSize(new Dimension(250, 25));
        ipField.setMaximumSize(new Dimension(165, 25));
        namePortField.setMaximumSize(new Dimension(162, 25));

        // Bot-Section with start button
        JPanel botPanel = new JPanel();
        mainPanel.add(botPanel);
        JButton startButton = new JButton("Start");
        JButton exitButton = new JButton("Exit");
        botPanel.add(startButton);
        botPanel.add(exitButton);
        botPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        titleScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // TODO: We should probably close the connecting client.
        titleScreen.setTitle("GetSmart");

        startButton.addActionListener(this);
        exitButton.addActionListener(this);

        titleScreen.setVisible(true);
    }

    private void LoginToServer() {
        try {
            var socket = new Socket("localhost", 3000);
            var client = new Client(socket);
            var clientData = new ClientData();
            clientData.username = usernameField.getText();
            client.SendMessageToServer(new Message(Message.Action.SET_USERNAME, GSON.toJson(clientData)));
            var thread = new Thread(client);
            thread.start();
        } catch (IOException ignored) {}
    }

    public static void GoToQuestionPage(String question, int row, int col, String[] answers, Client client) {
        new QuestionPage(question, row, col, answers, client);
        //board.dispose(); talk to daniel and eric
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Start")) {
            LoginToServer();
        } else if (e.getActionCommand().equals("Exit")) {
            System.exit(0);
        }
    }

    // TODO: We should put every type of screen inside of this single class.
    // If you want to call any UI change, it needs to be inside of "SwingUtilities.invokeLater"!
    // You can use "Instance." to access the original App instance.
    public static void GoToWaitingRoom() {
        SwingUtilities.invokeLater(() -> {
            Instance.waitingRoom = new WaitingRoom();
            Instance.titleScreen.dispose();
        });
    }

    public static void GoToBoard(ArrayList<ClientData> clientsData) {
        SwingUtilities.invokeLater(() -> {
            Instance.board = new Board(clientsData);
            Instance.waitingRoom.Dispose();
        });
    }

    public static void SetBoardInvisible() {
        SwingUtilities.invokeLater(() -> {
            Instance.board.hideBoard();
        });
    }

    public static void setButtonOnBoardToState(BoardData boardData) {
        SwingUtilities.invokeLater(() -> {
            Instance.board.handleButtonCase(boardData);
        });
    }

    public static void UpdateWaitingRoomUserNames(ArrayList<ClientData> clients) {
        SwingUtilities.invokeLater(() -> {
            if (Instance.waitingRoom != null)
                Instance.waitingRoom.UpdateUsernames(clients);
        });
    }
}
