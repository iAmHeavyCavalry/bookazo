package com.bookazo;

public record Webtoon(String title, String author, String url, String genre) {

    @Override
    public String toString() {
        return "Webtoon{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", url='" + url + '\'' +
                ", genre='" + genre + '\'' +
                '}';
    }
}