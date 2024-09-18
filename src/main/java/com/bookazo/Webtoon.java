package com.bookazo;

record Webtoon(int id, String title, String author, String url, String state, String genre, String likes) {

    @Override
    public String toString() {
        return "Webtoon{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", url='" + url + '\'' +
                ", state='" + state + '\'' +
                ", genre='" + genre + '\'' +
                ", likes='" + likes + '\'' +
                '}';
    }
}