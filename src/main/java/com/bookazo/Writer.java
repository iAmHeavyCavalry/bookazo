package com.bookazo;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Writer {
    // Logger for error handling
    private static final Logger logger = LoggerFactory.getLogger(Writer.class);

    public void writeToFile(List<Webtoon> webtoons, String filename) {
        try (FileWriter csvWriter = new FileWriter(filename, false)) {
            // Write the CSV headers
            if (new File(filename).length() == 0) {
                csvWriter.append("ID,Title,Author,URL,Genre,Likes\n");
            }

            // Write the webtoon data
            for (Webtoon webtoon : webtoons) {

                // Get the original likes to log
                String originalLikes = webtoon.likes();

                // Convert likes for CSV output
                String formattedLikes = convertLikesForCsv(originalLikes);

                // Prepare the CSV line for each webtoon
                String csvLine = String.join(",",
                        String.valueOf(webtoon.id()),
                        escapeCsv(webtoon.title()),
                        escapeCsv(webtoon.author()),
                        escapeCsv(webtoon.url()),
                        escapeCsv(webtoon.genre()),
                        escapeCsv(formattedLikes)
                );

                csvWriter.append(csvLine).append("\n");

            }

            csvWriter.flush();
            System.out.println("Data saved to " + filename);
        } catch (IOException e) {
            logger.error("Error while writing to the file: {}", filename, e);
        }
    }

    // Helper method to escape special characters in CSV fields
    private String escapeCsv(String data) {
        if (data.contains(",") || data.contains("\"") || data.contains("\n")) {
            data = data.replace("\"", "\"\"");
            return "\"" + data + "\"";
        }
        return data;
    }

    // Helper method to convert likes for CSV output
    private String convertLikesForCsv(String likes) {
        if (likes.isEmpty()) {
            return "0";
        }

        // Remove any leading or trailing whitespace
        likes = likes.trim();

        // Check if likes ends with "M" and convert accordingly
        if (likes.toUpperCase().endsWith("M")) {
            // Remove 'M' and convert to numeric
            likes = likes.substring(0, likes.length() - 1).trim();
            try {
                double numericLikes = Double.parseDouble(likes) * 1_000_000;
                return String.format("%,d", (int) numericLikes); // Format with commas and return
            } catch (NumberFormatException e) {
                logger.error("Failed to parse likes '{}': {}", likes, e.getMessage());
                return "0";
            }
        }

        // If not in "M" format, return the likes as is
        return likes;
    }
}

