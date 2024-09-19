package com.bookazo;
import java.io.*;
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
            String line = reader.readLine();
            if (line != null && line.contains("id,title")) {
                line = reader.readLine(); // Move to first data line
            }

            while (line != null) {
                String[] values = line.split(","); // Split the CSV line by commas

                if (values.length > 1) {
                    String title = values[1];
                    titles.add(title);
                } else {
                    logger.log(Level.WARNING, "Malformed CSV line: {0}", line);
                }
                line = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            logger.log(Level.SEVERE, "Scraped webtoons CSV file not found: {0}", SCRAPED_CSV_FILE);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error reading webtoon titles from CSV", e);
        }
        return titles;
    }

    // Inner class representing user-inputted webtoons and their ratings
        public record UserWebtoonRating(String username, String title, Integer rating) {

    }
}
