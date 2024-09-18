package com.bookazo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Scraper {
    // Logger for error handling
    private static final Logger logger = LoggerFactory.getLogger(Scraper.class);

    // List of genre URLs to scrape
    private static final String[] GENRE_URLS = {
            "https://www.webtoons.com/en/genres/drama?sortOrder=READ_COUNT",
            "https://www.webtoons.com/en/genres/fantasy?sortOrder=READ_COUNT",
            "https://www.webtoons.com/en/genres/action?sortOrder=READ_COUNT",
            // Add more genre URLs as needed
    };

    public List<Webtoon> scrapeWebtoons() {
        List<Webtoon> webtoons = new ArrayList<>();

        for (String genreUrl : GENRE_URLS) {
            try {
                Document doc = Jsoup.connect(genreUrl).get();
                Elements elements = doc.select("a.card_item"); // Selector for each webtoon card

                for (Element element : elements) {
                    String title = element.select("p.subj").text(); // Extract title
                    String author = element.select("p.author").text(); // Extract author
                    String url = element.absUrl("href"); // Get absolute URL
                    String genre = genreUrl.substring(genreUrl.lastIndexOf('/') + 1, genreUrl.indexOf('?')); // Extract genre from the URL

                    // Create and add Webtoon object
                    webtoons.add(new Webtoon(title, author, url, genre));
                }
            } catch (Exception e) {
                logger.error("Error while scraping the URL: {}", genreUrl, e); // Log the error
            }
        }
        return webtoons;
    }
}
