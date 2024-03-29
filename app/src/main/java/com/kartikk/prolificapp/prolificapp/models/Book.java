package com.kartikk.prolificapp.prolificapp.models;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by Kartikk on 3/21/2017.
 */

public class Book implements Parcelable {

    private int id;
    private String author;
    private String title;
    private Date lastCheckedOut;
    private String lastCheckedOutBy;
    private String categories;
    private String url;
    private String publisher;

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public Date getLastCheckedOut() {
        return lastCheckedOut;
    }

    public void setLastCheckedOut(Date lastCheckedOut) {
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
                ", lastCheckedOut=" + lastCheckedOut +
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
        parcel.writeInt(id);
        parcel.writeString(author);
        parcel.writeString(title);
        parcel.writeValue(lastCheckedOut);
        parcel.writeString(lastCheckedOutBy);
        parcel.writeString(categories);
        parcel.writeString(url);
        parcel.writeString(publisher);
    }

    public Book(Parcel in) {
        id = in.readInt();
        author = in.readString();
        title = in.readString();
        lastCheckedOut = (Date) in.readValue(Date.class.getClassLoader());
        lastCheckedOutBy = in.readString();
        categories = in.readString();
        url = in.readString();
        publisher = in.readString();
    }

    public Boolean isCheckedOutByValid() {
        return lastCheckedOutBy != null;
    }

    public Boolean isCheckedOutValid() {
        return lastCheckedOut != null;
    }

    public Boolean isPublisherValid() {
        return publisher != null;
    }

    public Boolean isTitleValid() {
        return title != null;
    }

    public Boolean isCategoriesValid() {
        return categories != null;
    }

    public Boolean isURLValid() {
        return url != null;
    }

    public Boolean isAuthorValid() {
        return author != null;
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
