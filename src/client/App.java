package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class App implements ActionListener {

    // TODO: We might need to pass the connecting client socket here, or maybe have a connection button in the app itself?
    JFrame titleScreen;
    public App() {

        // Main Frame with Title Screen
        titleScreen = new JFrame();
//        titleScreen.setSize();
        titleScreen.setExtendedState(JFrame.MAXIMIZED_BOTH);
        // Top Section containing the title
        JPanel topPanel = new JPanel();
        titleScreen.add(topPanel, BorderLayout.NORTH);
        topPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 25, 50));
        JLabel title = new JLabel("GetSmart");
        title.setFont(new Font("Verdana", Font.PLAIN, 30));
        topPanel.add(title);


        // Mid-Section with Username Selection
        JPanel midPanel = new JPanel();
        titleScreen.add(midPanel, BorderLayout.CENTER);
        midPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        //panel.setLayout(new GridLayout(0, 1));
        //midPanel.setLayout(new BoxLayout(midPanel, BoxLayout.X_AXIS));
        JLabel nameLabel = new JLabel("Enter a username:    ");
        JTextField nameTextField = new JTextField();
        midPanel.add(nameLabel);
        midPanel.add(nameTextField);
        midPanel.setLayout(new BoxLayout(midPanel, BoxLayout.X_AXIS));
        nameTextField.setMaximumSize(new Dimension(250, 25));


        // Bot-Section with start button
        JPanel botPanel = new JPanel();
        titleScreen.add(botPanel, BorderLayout.SOUTH);
        JButton startButton = new JButton("Start");
        JButton exitButton = new JButton("Exit");
        botPanel.add(startButton);
        botPanel.add(exitButton);


        titleScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // TODO: We should probably close the connecting client.
        titleScreen.setTitle("GetSmart");
        //frame.pack();
        startButton.addActionListener(this);
        exitButton.addActionListener(this);
        titleScreen.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Start")) {
            new Board();
            titleScreen.dispose();
        }else {
            System.exit(0);
        }
    }

}
