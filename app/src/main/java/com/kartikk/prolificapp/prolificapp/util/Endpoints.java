package com.kartikk.prolificapp.prolificapp.util;

import com.kartikk.prolificapp.prolificapp.models.Book;
import com.kartikk.prolificapp.prolificapp.models.UpdateBook;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Kartikk on 3/21/2017.
 */

public interface Endpoints {

    @GET(Constants.urlPart1 + "/books")
    Call<List<Book>> getBooks();

    @POST(Constants.urlPart1 + "/books")
    Call<Void> postBook(@Body UpdateBook updateBook);

}
