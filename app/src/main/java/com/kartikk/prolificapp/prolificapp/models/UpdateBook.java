package com.kartikk.prolificapp.prolificapp.models;

/**
 * Created by Kartikk on 3/22/2017.
 */

public class UpdateBook {

    private String title;
    private String author;
    private String publisher;
    private String categories;

    public UpdateBook(String title, String author, String publisher, String categories) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.categories = categories;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    @Override
    public String toString() {
        return "UpdateBook{" +
                "author='" + author + '\'' +
                ", categories='" + categories + '\'' +
                ", title='" + title + '\'' +
                ", publisher='" + publisher + '\'' +
                '}';
    }
}
