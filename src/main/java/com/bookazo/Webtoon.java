package com.bookazo;

record Webtoon(int id, String title, String author, String url, String genre) {

    @Override
    public String toString() {
        return "Webtoon{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", url='" + url + '\'' +
                ", genre='" + genre + '\'' +
                '}';
    }
}