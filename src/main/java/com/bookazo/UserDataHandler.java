package com.bookazo;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDataHandler {

    private static final String CSV_FILE = "user_webtoon_ratings.csv";
    private static final String SCRAPED_CSV_FILE = "webtoons.csv";
    private static final Logger logger = Logger.getLogger(UserDataHandler.class.getName());

    // Method to save user webtoon ratings into a CSV file
    public void saveUserWebtoonRating(UserWebtoonRating webtoonRating) {
        try (FileWriter writer = new FileWriter(CSV_FILE, true)) {
            writer.append(webtoonRating.username()).append(",")
                    .append(webtoonRating.title()).append(",")
                    .append(String.valueOf(webtoonRating.rating())).append("\n");
            writer.flush();
            logger.log(Level.INFO, "Webtoon rating saved: {0}, {1}",
                    new Object[]{webtoonRating.title(), webtoonRating.rating()});
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error writing webtoon rating to CSV", e);
        }
    }
    // Method to get titles from the scraped CSV file
    public List<String> getWebtoonTitles() {
        List<String> titles = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(SCRAPED_CSV_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Assuming the title is the first value in each line
                String title = line.split(",")[0]; // Adjust index based on your CSV structure
                titles.add(title);
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error reading webtoon titles from CSV", e);
        }
        return titles;
    }

    // Inner class representing user-inputted webtoons and their ratings
        public record UserWebtoonRating(String username, String title, Integer rating) {

    }
}
