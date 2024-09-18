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
        try (FileWriter csvWriter = new FileWriter(filename, true)) { // Set append mode to true
            // Write the CSV headers if the file is empty
            if (new File(filename).length() == 0) {
                csvWriter.append("Title,Author,URL,Genre\n"); // Update to include Author and Genre
            }

            // Write the webtoon data
            for (Webtoon webtoon : webtoons) {
                csvWriter.append(webtoon.title()).append(",")
                        .append(webtoon.author()).append(",") // Include author
                        .append(webtoon.url()).append(",")
                        .append(webtoon.genre()).append("\n"); // Include genre
            }

            csvWriter.flush();
            System.out.println("Data saved to " + filename);
        } catch (IOException e) {
            logger.error("Error while writing to the file: {}", filename, e);
        }
    }
}
