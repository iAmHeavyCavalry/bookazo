package com.bookazo;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.Objects;

public class Window extends JFrame{

    private final JTextField usernameField;
    private final JTextField titleTextField; // Use JTextField instead of JComboBox
    private final JSpinner ratingSpinner;
    private final UserDataHandler userDataHandler;
    private final JList<String> titleSuggestionList; // List for showing suggestions
    private final DefaultListModel<String> listModel; // Model for the JList

    public Window() {
        // Initialize the UserDataHandler
        userDataHandler = new UserDataHandler(); // Initialize user data handler

        // Set up the frame
        setTitle("Webtoon Rating");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 2));

        // Username Field
        add(new JLabel("Username:"));
        usernameField = new JTextField();
        add(usernameField);

        // Webtoon Title Text Field
        add(new JLabel("Webtoon Title:"));
        titleTextField = new JTextField();
        add(titleTextField);

        // Create the suggestion list and model
        listModel = new DefaultListModel<>();
        titleSuggestionList = new JList<>(listModel);
        titleSuggestionList.setVisible(false); // Hide the suggestion list by default
        titleSuggestionList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    String selectedTitle = titleSuggestionList.getSelectedValue();
                    titleTextField.setText(selectedTitle);
                    titleSuggestionList.setVisible(false); // Hide suggestions after selection
                }
            }
        });

        // Add key listener to the title text field for suggestions
        titleTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String input = titleTextField.getText();
                updateTitleSuggestions(input); // Update suggestions based on input
            }
        });

        // Add title suggestion list to the frame
        add(new JScrollPane(titleSuggestionList)); // Add scroll pane for suggestions


        // Rating Field
        add(new JLabel("Rating (1-10):"));
        ratingSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
        add(ratingSpinner);

        // Submit Button
        JButton submitButton = new JButton("Submit Rating");
        submitButton.addActionListener(_ -> submitRating());
        add(submitButton);

        setLocationRelativeTo(null);
    }

    private void updateTitleSuggestions(String input) {
        listModel.clear(); // Clear previous suggestions
        if (input.isEmpty()) {
            titleSuggestionList.setVisible(false); // Hide if input is empty
            return;
        }

        // Get titles and filter based on input
        List<String> titles = userDataHandler.getWebtoonTitles();
        for (String title : titles) {
            if (title.toLowerCase().startsWith(input.toLowerCase())) {
                listModel.addElement(title); // Add matching titles
            }
        }

        titleSuggestionList.setVisible(!listModel.isEmpty()); // Show suggestions if not empty
        titleSuggestionList.revalidate();
        titleSuggestionList.repaint();
    }

    private void submitRating() {
        String username = usernameField.getText().trim();
        String title = (String) titleTextField.getText().trim();
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
        titleTextField.setText("");
        ratingSpinner.setValue(1); // Reset rating to default
    }
}
