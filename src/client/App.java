package client;

import javax.swing.*;
import java.awt.*;

public class App {
    public App() {
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();

        panel.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));
        panel.setLayout(new GridLayout(0, 1));

        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // TODO: We should probably close the connecting client.
        frame.setTitle("GetSmart");
        frame.pack();
        frame.setVisible(true);
    }
}
