package com.bookazo;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Objects;

public class Window extends JFrame{

    private final JTextField usernameField;
    private final JComboBox<String> titleComboBox;
    private final JSpinner ratingSpinner;
    private final UserDataHandler userDataHandler;

    public Window() {
        // Initialize the UserDataHandler
        userDataHandler = new UserDataHandler(); // Initialize user data handler

        setTitle("Webtoon Rating");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 2));

        // Username Field
        add(new JLabel("Username:"));
        usernameField = new JTextField();
        add(usernameField);

        // Webtoon Title ComboBox
        add(new JLabel("Webtoon Title:"));
        titleComboBox = new JComboBox<>();
        populateTitleComboBox(); // Populate the JComboBox with titles from the CSV
        add(titleComboBox);

        // Rating Field
        add(new JLabel("Rating (1-5):"));
        ratingSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 5, 1));
        add(ratingSpinner);

        // Submit Button
        JButton submitButton = new JButton("Submit Rating");
        submitButton.addActionListener(_ -> submitRating());
        add(submitButton);

        setLocationRelativeTo(null);
    }

    private void populateTitleComboBox() {
        List<String> titles = userDataHandler.getWebtoonTitles();
        for (String title : titles) {
            titleComboBox.addItem(title);
        }
    }

    private void submitRating() {
        String username = usernameField.getText().trim();
        String title = (String) titleComboBox.getSelectedItem();
        Integer rating = (Integer) ratingSpinner.getValue();

        // Validate user input
        if (username.isEmpty() || Objects.requireNonNull(title).isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username and Title cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Create a new UserWebtoonRating object and save it
        UserDataHandler.UserWebtoonRating webtoonRating = new UserDataHandler.UserWebtoonRating(username, title, rating);
        userDataHandler.saveUserWebtoonRating(webtoonRating);

        // Show success message and clear fields
        JOptionPane.showMessageDialog(this, "Rating submitted successfully!");
        usernameField.setText("");
        titleComboBox.setSelectedIndex(0);
        ratingSpinner.setValue(1); // Reset rating to default
    }
}
