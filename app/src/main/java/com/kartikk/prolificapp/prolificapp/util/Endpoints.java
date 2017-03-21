package com.kartikk.prolificapp.prolificapp.util;

import com.kartikk.prolificapp.prolificapp.models.Book;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Kartikk on 3/21/2017.
 */

public interface Endpoints {

    @GET("/58c954f8942167000a8f77e4/books")
    Call<List<Book>> getBooks1();

}
