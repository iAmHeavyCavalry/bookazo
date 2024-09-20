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

    private final List<Webtoon> webtoons = new ArrayList<>();

    public UserDataHandler() {
        loadWebtoons(); // Load webtoons from the CSV on initialization
    }

    // Load webtoons from CSV file
    private void loadWebtoons() {
        try (BufferedReader reader = new BufferedReader(new FileReader(SCRAPED_CSV_FILE))) {
            String line = reader.readLine();
            if (line == null || !line.contains("ID,Title")) {
                logger.log(Level.SEVERE, "CSV file does not contain the expected header.");
                return;
            }

            // Continue reading from the first data line
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");

                if (values.length >= 7) {
                    try {
                        int id = Integer.parseInt(values[0].trim());
                        String title = values[1].trim();
                        String author = values[2].trim();
                        String url = values[3].trim();
                        String state = values[4].trim();
                        String genre = values[5].trim();
                        String likes = values[6].trim();

                        // Create and add Webtoon object to the list
                        webtoons.add(new Webtoon(id, title, author, url, state, genre, likes));
                    } catch (NumberFormatException e) {
                        logger.log(Level.SEVERE, "Error parsing ID from CSV: {0}", values[0]);
                    }
                } else {
                    logger.log(Level.WARNING, "Malformed CSV line: {0}", line);
                }
            }
        } catch (FileNotFoundException e) {
            logger.log(Level.SEVERE, "Scraped webtoons CSV file not found: {0}", SCRAPED_CSV_FILE);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error reading webtoon titles from CSV", e);
        }
    }

    // Method to save user webtoon ratings into a CSV file
    public void saveUserWebtoonRating(UserWebtoonRating webtoonRating) {
        try (FileWriter writer = new FileWriter(CSV_FILE, true)) {
            writer.append(webtoonRating.username()).append(",")
                    .append(String.valueOf(webtoonRating.id())).append(",")
                    .append(webtoonRating.title()).append(",")
                    .append(String.valueOf(webtoonRating.rating())).append("\n");
            writer.flush();
            logger.log(Level.INFO, "Webtoon rating saved: {0}, {1}",
                    new Object[]{webtoonRating.title(), webtoonRating.rating()});
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error writing webtoon rating to CSV", e);
        }
    }
    // Method to get a list of webtoons
    public List<Webtoon> getWebtoonTitles() {
        return webtoons; // Return the list of Webtoon objects
    }

    // Method to get a Webtoon by title
    public Webtoon getWebtoonByTitle(String title) {
        for (Webtoon webtoon : webtoons) {
            if (webtoon.title().equalsIgnoreCase(title)) {
                return webtoon;
            }
        }
        return null;
    }

        // Inner class representing user-inputted webtoons and their ratings
        public record UserWebtoonRating(String username, int id, String title, Integer rating) {

    }
}
