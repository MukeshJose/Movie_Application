package com.example.movieapplication.retrofit;

import com.example.movieapplication.model.Root;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIInterface {
    @GET("popular")
    Call<Root> CALL_APIPopular(@Query("api_key") String key, @Query("language") String language, @Query("page") int page);

    @GET("top_rated")
    Call<Root> CALL_APITopRated(@Query("api_key") String key, @Query("language") String language, @Query("page") int page);

    @GET("popular")
    Call<Root> CALL_APISliderImage(@Query("api_key") String key, @Query("language") String language, @Query("page") int page);

    @GET("recommendations")
    Call<Root> CALL_APIRecommendations(@Query("api_key") String key, @Query("language") String language, @Query("page") int page);

    @GET("similar")
    Call<Root> CALL_APISimilar(@Query("api_key") String key, @Query("language") String language, @Query("page") int page);

    @GET("similar")
    Call<Root> CALL_APIViewMore(@Query("api_key") String key, @Query("language") String language, @Query("page") int page);
}
