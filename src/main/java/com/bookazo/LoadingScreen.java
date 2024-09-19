package com.bookazo;
import javax.swing.*;
import java.awt.*;

public class LoadingScreen extends JFrame {

    public LoadingScreen() {
        setTitle("Loading...");
        setSize(300, 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center on screen
        setLayout(new BorderLayout());

        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true); // This makes the progress bar continuously animate
        add(progressBar, BorderLayout.CENTER);

        setVisible(true); // Make the loading screen visible
    }

    public void close() {
        dispose(); // Close the loading screen
    }

}
