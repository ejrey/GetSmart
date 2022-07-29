package client;

import middleware.ClientData;
import middleware.Message;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class WaitingRoom implements ActionListener {

    JFrame waitingRoomFrame = new JFrame();
    JLabel usernameText;

    public WaitingRoom() {
        waitingRoomFrame.setSize(1280, 720);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        waitingRoomFrame.add(mainPanel);

        // Top Section containing the title
        JPanel topPanel = new JPanel();
        mainPanel.add(topPanel);
        topPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 25, 50));
        JLabel title = new JLabel("GetSmart");
        title.setFont(new Font("Verdana", Font.PLAIN, 100));
        topPanel.add(title);

        // Mid-Section displaying the players
        JPanel midPanel = new JPanel();
        mainPanel.add(midPanel);
        midPanel.setLayout(new BoxLayout(midPanel, BoxLayout.X_AXIS));


        // Usernames
        JPanel usernames = new JPanel();
        usernameText = new JLabel("");
        usernameText.setFont(new Font("Verdana", Font.PLAIN, 20));
        midPanel.add(usernames);
        usernames.add(usernameText);
        usernames.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 10));

        JPanel botPanel = new JPanel();
        mainPanel.add(botPanel);
        JButton startButton = new JButton("Start");
        botPanel.add(startButton);
        botPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        startButton.addActionListener(this);

        waitingRoomFrame.setVisible(true);
    }

    public void UpdateUsernames(ArrayList<ClientData> clients) {
        StringBuilder stringBuilder = new StringBuilder();
        clients.forEach((clientData -> {
            stringBuilder.append(clientData.username);
            stringBuilder.append(" ");
        }));
        usernameText.setText(stringBuilder.toString());
    }

    public void StartGame() {
        Client.Instance.SendMessageToServer(new Message(Message.Action.SEND_TO_BOARD, ""));
    }

    public void Dispose() {
        waitingRoomFrame.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Start")) {
            StartGame();
        }
    }
}
