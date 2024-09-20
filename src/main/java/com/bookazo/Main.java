package com.bookazo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import javax.swing.*;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        // Launch the loading screen
        LoadingScreen loadingScreen = new LoadingScreen();

        // Use SwingWorker to perform scraping in the background
        SwingWorker<List<Webtoon>, Integer> worker = new SwingWorker<>() {
            @Override
            protected List<Webtoon> doInBackground() {
                // Create an instance of the scraper
                Scraper scraper = new Scraper();

                // Retrieve the webtoons in batches
                return scraper.scrapeWebtoons();
            }

            @Override
            protected void done() {
                try {
                    // Retrieve the result of the scraping
                    List<Webtoon> webtoons = get();
                    // Print the scraped data to the console (for debugging)
                    for (Webtoon webtoon : webtoons) {
                        System.out.println(webtoon);
                    }

                    // Save the results to a file
                    Writer writer = new Writer();
                    writer.writeToFile(webtoons, "webtoons.csv");

                } catch (Exception e) {
                    logger.error("An error occurred while retrieving the webtoons: ", e);
                } finally {
                    loadingScreen.close();
                    // Launch the UI for webtoon management
                    SwingUtilities.invokeLater(() -> {
                        Window window = new Window();
                        window.setVisible(true);
                    });
                }
            }
        };

        worker.execute();
    }
}
