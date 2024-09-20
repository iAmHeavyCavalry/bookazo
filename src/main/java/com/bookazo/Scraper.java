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
            "https://www.webtoons.com/en/genres/comedy?sortOrder=READ_COUNT",
            "https://www.webtoons.com/en/genres/slice_of_life?sortOrder=READ_COUNT",
            "https://www.webtoons.com/en/genres/romance?sortOrder=READ_COUNT",
            "https://www.webtoons.com/en/genres/super_hero?sortOrder=READ_COUNT",
            "https://www.webtoons.com/en/genres/sf?sortOrder=READ_COUNT",
            "https://www.webtoons.com/en/genres/thriller?sortOrder=READ_COUNT",
            "https://www.webtoons.com/en/genres/supernatural?sortOrder=READ_COUNT",

    };

    public List<Webtoon> scrapeWebtoons() {
        List<Webtoon> webtoons = new ArrayList<>();
        int id = 1;

        for (String genreUrl : GENRE_URLS) {
            try {
                Document doc = Jsoup.connect(genreUrl).get();
                Elements elements = doc.select("a.card_item");

                for (Element element : elements) {
                    // Extract title and author with robustness
                    String title = getTextSafe(element.select("p.subj"));
                    String author = getTextSafe(element.select("p.author"));
                    String url = element.absUrl("href");
                    String genre = genreUrl.substring(genreUrl.lastIndexOf('/') + 1, genreUrl.indexOf('?'));
                    String likes = getTextSafe(element.select("em.grade_num"));

                    // Extract state based on the class names
                    String state;
                    if (!element.select("span.txt_ico_completed").isEmpty()) {
                        state = "END"; // Completed series
                    } else if (!element.select("span.txt_ico_hiatus").isEmpty()) {
                        state = "PAUSED"; // On hiatus
                    } else if (!element.select("span.txt_ico_up").isEmpty()) {
                        state = "UP"; // Recently new chapter added today
                    } else {
                        state = "AIR"; // Ongoing series
                    }

                    // Debug output to check the values along with the ID
                    System.out.println("ID: " + id);
                    System.out.println("Title: " + title);
                    System.out.println("Author: " + author);
                    System.out.println("URL: " + url);
                    System.out.println("State: " + state);
                    System.out.println("Genre: " + genre);
                    System.out.println("Likes: " + likes);

                    // Log the full element HTML if title or author is empty
                    if (title.isEmpty() || author.isEmpty() || url.isEmpty()) {
                        logger.warn("Missing information for entry - ID: {}, Title: {}, Author: {}, URL: {}", id, title, author, url);
                        logger.warn("Element HTML: {}", element.outerHtml());
                        continue;
                    }

                    // Create and add Webtoon object with the ID
                    webtoons.add(new Webtoon(id++, title, author, url, state, genre, likes));
                }
            } catch (Exception e) {
                logger.error("Error while scraping the URL: {}", genreUrl, e);
            }
        }
        return webtoons;
    }

    private String getTextSafe(Elements elements) {
        if (elements.isEmpty()) {
            return "";
        }
        // Join the text from all matching elements to handle cases where the title might be split
        return String.join(" ", elements.eachText()).trim();
    }
}




