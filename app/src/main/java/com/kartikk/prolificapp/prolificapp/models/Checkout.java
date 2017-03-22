package com.kartikk.prolificapp.prolificapp.models;

/**
 * Created by Kartikk on 3/22/2017.
 */

public class Checkout {
    private String lastCheckedOutBy;

    public Checkout(String lastCheckedOutBy) {
        this.lastCheckedOutBy = lastCheckedOutBy;
    }

    public String getLastCheckedOutBy() {
        return lastCheckedOutBy;
    }

    public void setLastCheckedOutBy(String lastCheckedOutBy) {
        this.lastCheckedOutBy = lastCheckedOutBy;
    }

    @Override
    public String toString() {
        return "Checkout{" +
                "lastCheckedOutBy='" + lastCheckedOutBy + '\'' +
                '}';
    }
}
