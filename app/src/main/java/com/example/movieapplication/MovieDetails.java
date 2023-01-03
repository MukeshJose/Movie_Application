package com.example.movieapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.movieapplication.adapter.RecommendationAdapter;
import com.example.movieapplication.adapter.SimilarAdapter;
import com.example.movieapplication.model.Root;
import com.example.movieapplication.retrofit.APIClient;
import com.example.movieapplication.retrofit.APIInterface;
import com.facebook.shimmer.ShimmerFrameLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetails extends AppCompatActivity {
    TextView movieName, movieReleaseDate, movieLanguage, moviePopularity, movieAverageRating, movieOverview, recommendation, similar;
    ImageView moviePoster, movieBackdrop;
    RecyclerView recyclerViewRecommendation, recyclerViewSimilar;
    ShimmerFrameLayout shimmerFrameLayoutRecommend;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        movieBackdrop = findViewById(R.id.backDrop);
        movieName = findViewById(R.id.movieName);
        movieReleaseDate = findViewById(R.id.releaseDate);
        movieLanguage = findViewById(R.id.originalLanguage);
        moviePopularity = findViewById(R.id.moviePopularity);
        movieAverageRating = findViewById(R.id.averageRating);
        movieOverview = findViewById(R.id.overView);
        moviePoster = findViewById(R.id.imagePoster);
        recyclerViewRecommendation = findViewById(R.id.recommendationView);
        recyclerViewSimilar = findViewById(R.id.similarView);

        shimmerFrameLayoutRecommend = findViewById(R.id.recommendationShimmer);
        recommendation = findViewById(R.id.recommendations);
        similar = findViewById(R.id.similarMovies);

        shimmerFrameLayoutRecommend.startShimmer();

        movieName.setText(getIntent().getStringExtra("movieName"));
        movieReleaseDate.setText(getIntent().getStringExtra("releaseDate"));
        movieLanguage.setText(getIntent().getStringExtra("language"));
        moviePopularity.setText(String.valueOf(getIntent().getDoubleExtra("popularity", 0)));
        movieAverageRating.setText(String.valueOf(getIntent().getDoubleExtra("averageRating", 0)));
        movieOverview.setText(getIntent().getStringExtra("overview"));
        Glide.with(MovieDetails.this).load(getIntent().getStringExtra("moviePoster")).into(moviePoster);
        Glide.with(MovieDetails.this).load(getIntent().getStringExtra("backDrop")).into(movieBackdrop);

        APIInterface api = APIClient.getID().create(APIInterface.class);

        apiCallRecommend(Constants.apiKey, Constants.language, 1, api);
        apiCallSimilar(Constants.apiKey, Constants.language, 1, api);

        moviePoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String moviePoster = getIntent().getStringExtra("moviePoster");
                Intent intent = new Intent(getApplicationContext(), ImageViewer.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("moviePoster", moviePoster);
                startActivity(intent);
            }
        });
    }

    private void apiCallSimilar(String key, String language, int page, APIInterface api) {
        api.CALL_APISimilar(key, language, page).enqueue(new Callback<Root>() {
            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {
                if (response.isSuccessful()) {
                    Root root = response.body();
                    LinearLayoutManager newLayout = new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false);
                    recyclerViewSimilar.setLayoutManager(newLayout);
                    SimilarAdapter newObject = new SimilarAdapter(getApplicationContext(), root);
                    recyclerViewSimilar.setAdapter(newObject);
                }
            }

            @Override
            public void onFailure(Call<Root> call, Throwable t) {

            }
        });
    }

    private void apiCallRecommend(String key, String language, int page, APIInterface api) {
        api.CALL_APIRecommendations(key, language, page).enqueue(new Callback<Root>() {
            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {
                if (response.isSuccessful()) {
                    shimmerFrameLayoutRecommend.stopShimmer();
                    shimmerFrameLayoutRecommend.setVisibility(View.GONE);
                    recommendation.setVisibility(View.VISIBLE);
                    similar.setVisibility(View.VISIBLE);
                    recyclerViewRecommendation.setVisibility(View.VISIBLE);
                    recyclerViewSimilar.setVisibility(View.VISIBLE);
                    Root root = response.body();
                    LinearLayoutManager layout = new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false);
                    recyclerViewRecommendation.setLayoutManager(layout);
                    RecommendationAdapter object = new RecommendationAdapter(getApplicationContext(), root);
                    recyclerViewRecommendation.setAdapter(object);
                }
            }

            @Override
            public void onFailure(Call<Root> call, Throwable t) {

            }
        });
    }
}