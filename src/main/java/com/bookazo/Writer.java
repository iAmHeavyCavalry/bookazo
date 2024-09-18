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
                csvWriter.append("ID,Title,Author,URL,Genre\n"); // Update to include Author and Genre
            }

            // Write the webtoon data
            for (Webtoon webtoon : webtoons) {
                csvWriter.append(String.valueOf(webtoon.id())).append(",")
                        .append(escapeCsv(webtoon.title())).append(",")
                        .append(escapeCsv(webtoon.author())).append(",")
                        .append(escapeCsv(webtoon.url())).append(",")
                        .append(escapeCsv(webtoon.genre())).append("\n");
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
            // Escape double quotes by replacing them with two double quotes
            data = data.replace("\"", "\"\"");
            // Wrap the field in double quotes
            return "\"" + data + "\"";
        }
        return data;
    }
}

