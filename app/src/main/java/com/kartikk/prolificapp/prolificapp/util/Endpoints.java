package com.kartikk.prolificapp.prolificapp.util;

import com.kartikk.prolificapp.prolificapp.models.Book;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Kartikk on 3/21/2017.
 */

public interface Endpoints {

    @GET
    Call<Book> getBooks();

}
