package com.kartikk.prolificapp.prolificapp.models;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

import java.util.Objects;

/**
 * Created by Kartikk on 3/21/2017.
 */

public class Book implements Parcelable {

    @Expose
    private String id;
    @Expose
    private String author;
    @Expose
    private String title;
    @Expose
    private String lastCheckedOut;
    @Expose
    private String lastCheckedOutBy;
    @Expose
    private String categories;
    @Expose
    private String url;
    @Expose
    private String publisher;

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLastCheckedOut() {
        return lastCheckedOut;
    }

    public void setLastCheckedOut(String lastCheckedOut) {
        this.lastCheckedOut = lastCheckedOut;
    }

    public String getLastCheckedOutBy() {
        return lastCheckedOutBy;
    }

    public void setLastCheckedOutBy(String lastCheckedOutBy) {
        this.lastCheckedOutBy = lastCheckedOutBy;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", lastCheckedOut='" + lastCheckedOut + '\'' +
                ", lastCheckedOutBy='" + lastCheckedOutBy + '\'' +
                ", categories='" + categories + '\'' +
                ", url='" + url + '\'' +
                ", publisher='" + publisher + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(author);
        parcel.writeString(title);
        parcel.writeString(lastCheckedOut);
        parcel.writeString(lastCheckedOutBy);
        parcel.writeString(categories);
        parcel.writeString(url);
        parcel.writeString(publisher);
    }

    public Book(Parcel in) {
        id = in.readString();
        author = in.readString();
        title = in.readString();
        lastCheckedOut = in.readString();
        lastCheckedOutBy = in.readString();
        categories = in.readString();
        url = in.readString();
        publisher = in.readString();
    }

    public Boolean isCheckedOutValid() {
        return  !((lastCheckedOutBy == "null") && (lastCheckedOut == "null"));
    }

    public Boolean isPublisherValid() {
        return publisher!="null";
    }

    public static final Parcelable.Creator<Book> CREATOR = new Parcelable.Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

}
