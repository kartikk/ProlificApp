package com.kartikk.prolificapp.prolificapp.util;

import com.kartikk.prolificapp.prolificapp.models.Book;
import com.kartikk.prolificapp.prolificapp.models.UpdateBook;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Kartikk on 3/21/2017.
 */

public interface Endpoints {

    @GET(Constants.urlPart1 + "/books")
    Call<List<Book>> getBooks();

    @POST(Constants.urlPart1 + "/books")
    Call<Void> postBook(@Body UpdateBook updateBook);

    @DELETE(Constants.urlPart1 + "/books/{id}/")
    Call<Void> deleteBook(@Path("id") String id);

    @DELETE(Constants.urlPart1 + "/clean/")
    Call<Void> deleteAllBooks();

}
