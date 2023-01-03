package com.example.movieapplication;

import android.content.Context;
import android.content.Intent;

import com.example.movieapplication.model.Root;
import com.example.movieapplication.retrofit.APIClient;

public class Constants {
    public static String baseUrlLatest = "https://api.themoviedb.org/3/movie/";
    public static String baseUrlViewMore = "https://api.themoviedb.org/3/movie/960704/";
    public static String baseUrlImage = "https://image.tmdb.org/t/p/w500";
    public static String apiKey = "f9df0333c1731d8a95fbe239a07e83ce";
    public static String language = "en-US";

    public static void goToMovieDetails(int position, Context context, Root root) {
        Intent intent = new Intent(context, MovieDetails.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("backDrop", baseUrlImage + root.results.get(position).backdrop_path);
        intent.putExtra("movieName", root.results.get(position).title);
        intent.putExtra("moviePoster", baseUrlImage + root.results.get(position).poster_path);
        intent.putExtra("releaseDate", root.results.get(position).release_date);
        intent.putExtra("language", root.results.get(position).original_language);
        intent.putExtra("popularity", root.results.get(position).popularity);
        intent.putExtra("averageRating", root.results.get(position).vote_average);
        intent.putExtra("overview", root.results.get(position).overview);
        context.startActivity(intent);
    }

}

