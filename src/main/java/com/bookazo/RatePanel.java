package com.bookazo;
import javax.swing.*;
import java.awt.*;

public class RatePanel extends JPanel{

    private final JTextField usernameField;
    private final JTextField TitleField;
    private final JTextField ratingField;
    private final UserDataHandler userDataHandler; // Instance of UserDataHandler

    public RatePanel() {
        setLayout(new GridLayout(4, 2));

        // Initialize the UserDataHandler
        userDataHandler = new UserDataHandler();

        // Add components to the panel
        add(new JLabel("Username:"));
        usernameField = new JTextField();
        add(usernameField);

        add(new JLabel("Webtoon Title:"));
        TitleField = new JTextField();
        add(TitleField);

        add(new JLabel("Your Rating (1-10):"));
        ratingField = new JTextField();
        add(ratingField);

        JButton submitButton = new JButton("Submit Rating");
        add(submitButton);

        // Add action listener to the button
        submitButton.addActionListener(_ -> {
            String username = usernameField.getText().trim();
            String title = TitleField.getText();
            String ratingStr = ratingField.getText();

            // Validate rating input
            if (username.isEmpty() || title.isEmpty()) {
                JOptionPane.showMessageDialog(RatePanel.this,
                        "Username and Title cannot be empty.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!validateRating(ratingStr)) {
                JOptionPane.showMessageDialog(RatePanel.this,
                        "Please enter a valid rating between 1 and 10.", "Invalid Rating", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int rating = Integer.parseInt(ratingStr);
            UserDataHandler.UserWebtoonRating webtoonRating = new UserDataHandler.UserWebtoonRating(username, title, rating);
            userDataHandler.saveUserWebtoonRating(webtoonRating);

            // Clear the input fields after submission
            usernameField.setText("");
            TitleField.setText("");
            ratingField.setText("");
        });
    }

    // Method to validate the rating input
    private boolean validateRating(String ratingStr) {
        try {
            int rating = Integer.parseInt(ratingStr);
            return rating >= 1 && rating <= 10; // Rating should be between 1 and 10
        } catch (NumberFormatException e) {
            return false; // If parsing fails, return false
        }
    }
}
