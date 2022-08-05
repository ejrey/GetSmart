package client;

import middleware.ClientData;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Results {
    JFrame resultsScreen = new JFrame();

    public Results(ArrayList<ClientData> clientsData) {

        Collections.sort(clientsData, new Comparator<ClientData>() {
            public int compare(ClientData s1, ClientData s2) {
                return s2.score.compareTo(s1.score);
            }
        });

        resultsScreen.setSize(1280, 720);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        resultsScreen.add(mainPanel);

        JPanel winnerPanel = new JPanel();
        winnerPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        mainPanel.add(winnerPanel);


        JLabel winner = new JLabel("Winner!");
        winnerPanel.add(winner);
        winnerPanel.setLayout(new GridBagLayout());
        winner.setFont(new Font("Verdana", Font.PLAIN, 100));

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        mainPanel.add(titlePanel);

        for(int i = 0; i < 3; i++) {
            if (i < clientsData.toArray().length) {
                JPanel firstPanel = new JPanel();
                JLabel first = new JLabel( i + 1 + ". " + clientsData.get(i).username + ": " + clientsData.get(i).score);
                first.setFont(new Font("Verdana", Font.PLAIN, 25));
                first.setMaximumSize(new Dimension(250, 25));
                firstPanel.add(first);
                firstPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                titlePanel.add(firstPanel);
            }
        }

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