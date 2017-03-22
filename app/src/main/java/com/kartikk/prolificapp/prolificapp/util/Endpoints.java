package com.kartikk.prolificapp.prolificapp.util;

import com.kartikk.prolificapp.prolificapp.models.Book;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Kartikk on 3/21/2017.
 */

public interface Endpoints {

    @GET(Constants.urlPart1 + "/books")
    Call<List<Book>> getBooks();

}
