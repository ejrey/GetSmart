package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class App implements ActionListener {

    JFrame titleScreen = new JFrame();

    // TODO: We might need to pass the connecting client socket here, or maybe have a connection button in the app itself?
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
        JTextField nameTextField = new JTextField(50);

        usernamePanel.add(nameLabel);
        usernamePanel.add(nameTextField);
        usernamePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel portLabel = new JLabel("Enter a port number to the server    :    ");
        JTextField namePortField = new JTextField(10);
        portPanel.add(portLabel);
        portPanel.add(namePortField);
        portPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        usernamePanel.setLayout(new BoxLayout(usernamePanel, BoxLayout.X_AXIS));
        portPanel.setLayout(new BoxLayout(portPanel, BoxLayout.X_AXIS));

        nameTextField.setMaximumSize(new Dimension(250, 25));
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Start")) {
            new Board();
            titleScreen.dispose();
        }else if (e.getActionCommand().equals("Exit")) {
            System.exit(0);
        }
    }

}
