package com.bookazo;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        // Create an instance of the scraper
        Scraper scraper = new Scraper();

        // Start the scraping process and get the results
        List<Webtoon> webtoons = scraper.scrapeWebtoons();

        // Print the scraped data to the console (for debugging)
        for (Webtoon webtoon : webtoons) {
            System.out.println(webtoon);
        }

        // Save the results to a file (Optionally)
        Writer writer = new Writer();
        writer.writeToFile(webtoons, "webtoons.csv");

    }
}