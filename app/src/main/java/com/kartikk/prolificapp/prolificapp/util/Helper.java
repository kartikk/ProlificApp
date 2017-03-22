package com.kartikk.prolificapp.prolificapp.util;

import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Kartikk on 3/21/2017.
 */

public class Helper {

    private static Retrofit retrofit;

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(Constants.baseURL)
                    .addConverterFactory(GsonConverterFactory
                            .create(new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create())).build();
        }
        return retrofit;
    }

    public static Endpoints getRetrofitEndpoints() {
        return getRetrofitInstance().create(Endpoints.class);
    }

}
