package com.example.movieapplication.retrofit;

import com.example.movieapplication.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
    private static Retrofit retrofit = null;
    private static Retrofit retrofit3 = null;
    private static Retrofit retrofit2 = null;

    static String id;

    public APIClient(String id) {
        this.id = id;
    }


    public static Retrofit getClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.baseUrlLatest)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

    public static Retrofit getID() {
        retrofit2 = new Retrofit.Builder()
                .baseUrl(Constants.baseUrlLatest + id + "/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit2;
    }

    public static Retrofit getViewMore() {
        retrofit3 = new Retrofit.Builder()
                .baseUrl(Constants.baseUrlViewMore)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit3;
    }
}