package client;

import com.google.gson.Gson;
import middleware.ClientData;
import middleware.Message;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;

public class App implements ActionListener {

    public static void main(String[] args) {
        new App();
    }
    public static final Gson GSON = new Gson();

    JFrame titleScreen = new JFrame();
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
        JPanel portPanel = new JPanel();

        mainPanel.add(usernamePanel);
        mainPanel.add(portPanel);

        mainPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        //midPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        JLabel nameLabel = new JLabel("Enter a username:    ");
        usernameField = new JTextField(50);

        usernamePanel.add(nameLabel);
        usernamePanel.add(usernameField);
        usernamePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel portLabel = new JLabel("Enter a port number to the server    :    ");
        JTextField namePortField = new JTextField(10);
        portPanel.add(portLabel);
        portPanel.add(namePortField);
        portPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        usernamePanel.setLayout(new BoxLayout(usernamePanel, BoxLayout.X_AXIS));
        portPanel.setLayout(new BoxLayout(portPanel, BoxLayout.X_AXIS));

        usernameField.setMaximumSize(new Dimension(250, 25));
        namePortField.setMaximumSize(new Dimension(150, 25));

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

    // TODO: This should go to the waiting room instead of the Board();
    public void GoToWaitingRoom() {
        new Board();
        titleScreen.dispose();
    }

    private void LoginToServer() {
        try {
            var socket = new Socket("localhost", 3000);
            var client = new Client(this, socket);
            var clientData = new ClientData();
            clientData.username = usernameField.getText();
            client.SendMessageToServer(new Message(Message.Action.SET_USERNAME, GSON.toJson(clientData)));
            var thread = new Thread(client);
            thread.start();
        } catch (IOException ignored) {}
    }

    public void GoToQuestionPage(String question, int row, int col, String[] answers) {
        new QuestionPage(question, row, col, answers);
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
}
