package client;

import middleware.ClientData;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

public class Results {
    JFrame resultsScreen = new JFrame();

    public Results(ArrayList<ClientData> clientsData) {
        clientsData.sort(Comparator.comparing(o -> o.score));
        resultsScreen.setSize(1280, 720);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        resultsScreen.add(mainPanel);

        JPanel topPanel = new JPanel();
        mainPanel.add(topPanel);
        topPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 25, 50));
        JLabel winner = new JLabel("Winner!");
        JPanel winnerPanel = new JPanel();
        winnerPanel.add(winner);
        winner.setFont(new Font("Verdana", Font.PLAIN, 100));
        topPanel.add(winnerPanel);

        JLabel title = new JLabel(clientsData.get(0).username + ": " + clientsData.get(0).score);
        JPanel titlePanel = new JPanel();
        titlePanel.add(title);
        title.setFont(new Font("Verdana", Font.PLAIN, 100));
        topPanel.add(titlePanel);

        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        winnerPanel.setLayout(new BoxLayout(winnerPanel, BoxLayout.Y_AXIS));
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));

        JPanel botPanel = new JPanel();
        mainPanel.add(botPanel);
        JButton exitButton = new JButton("Exit");
        botPanel.add(exitButton);
        botPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        resultsScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        resultsScreen.setTitle("GetSmart");

        exitButton.addActionListener(e -> System.exit(0));

        resultsScreen.setVisible(true);
    }
}
