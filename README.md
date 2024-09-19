<picture>
  <source media="(prefers-color-scheme: dark)" srcset="https://user-images.githubusercontent.com/25423296/163456776-7f95b81a-f1ed-45f7-b7ab-8fa810d529fa.png">
  <source media="(prefers-color-scheme: light)" srcset="https://user-images.githubusercontent.com/25423296/163456779-a8556205-d0a5-45e2-ac17-42d089e3c3f8.png">
  <img alt="Shows an illustrated sun in light mode and a moon with stars in dark mode." src="https://user-images.githubusercontent.com/25423296/163456779-a8556205-d0a5-45e2-ac17-42d089e3c3f8.png">
</picture>

## Personalized Book Recommendation System Based on Reading Habits

### Current Version: 1.0.SNAPSHOT :shipit:

  This project implements a personalized book recommendation system that analyzes 
  user reading habits to suggest titles tailored to individual preferences. 
  Using data scraping techniques, it gathers information from Webtoon’s extensive 
  library, including titles, authors, and genres, and organizes them in a 
  structured format for easy access and analysis.
  
<br />

### Key Features:

- Data scraping from Webtoon to gather book titles and author information.
- CSV file output for data storage and further analysis.
- Future enhancements planned for advanced recommendation algorithms.
<br />
<br />
<br />

> [!NOTE]
> This project scrapes data from Webtoons and saves it in a CSV file format.
> Please ensure you have stable internet connectivity while scraping data,
> as the process depends on real-time web requests.

> [!TIP]
> Customize the list of genres or add new genres by modifying the `GENRE_URLS`
> array in the `Scraper` class. This allows you to scrape different categories
> or explore new genres on Webtoons.

> [!IMPORTANT]
> The data scraped and saved is based on publicly available information
> from Webtoons. This project is intended for personal use and educational purposes.
> Ensure compliance with Webtoons' terms of service when using this tool.

> [!WARNING]
> Scraping large amounts of data from websites can sometimes cause the site to
> block your IP or slow down the process. Avoid sending too many requests in a
> short period of time to prevent being rate-limited.

> [!CAUTION]
> Be aware that the structure of the Webtoons website may change over time.
> If the website's layout or class names are updated, you may need to adjust
> the HTML selectors in the `Scraper` class accordingly.
