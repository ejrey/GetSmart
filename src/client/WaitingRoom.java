package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WaitingRoom implements ActionListener {

    JFrame waitingRoomFrame = new JFrame();
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

        JPanel playerOne = new JPanel();
        JPanel playerTwo = new JPanel();
        JPanel playerThree = new JPanel();
        JPanel playerFour = new JPanel();
        JPanel playerFive = new JPanel();

        JLabel oneText = new JLabel("Ben");
        oneText.setFont(new Font("Verdana", Font.PLAIN, 20));
        JLabel twoText = new JLabel("Matthew");
        twoText.setFont(new Font("Verdana", Font.PLAIN, 20));
        JLabel threeText = new JLabel("Daniel");
        threeText.setFont(new Font("Verdana", Font.PLAIN, 20));
        JLabel fourText = new JLabel("Randy");
        fourText.setFont(new Font("Verdana", Font.PLAIN, 20));
        JLabel fiveText = new JLabel("Eric");
        fiveText.setFont(new Font("Verdana", Font.PLAIN, 20));

        midPanel.add(playerOne);
        midPanel.add(playerTwo);
        midPanel.add(playerThree);
        midPanel.add(playerFour);
        midPanel.add(playerFive);

        playerOne.add(oneText);
        playerTwo.add(twoText);
        playerThree.add(threeText);
        playerFour.add(fourText);
        playerFive.add(fiveText);

        playerOne.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 10));
        playerTwo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 10));
        playerThree.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 10));
        playerFour.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 10));
        playerFive.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 10));

        JPanel botPanel = new JPanel();
        mainPanel.add(botPanel);
        JButton startButton = new JButton("Start");
        botPanel.add(startButton);
        botPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        startButton.addActionListener(this);

        waitingRoomFrame.setVisible(true);
    }

    public void GoToBoard() {
        new Board();
        waitingRoomFrame.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Start")) {
            GoToBoard();
        }
    }
}
