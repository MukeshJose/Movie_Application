package com.example.movieapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.movieapplication.adapter.ViewMoreAdapter;
import com.example.movieapplication.model.Root;
import com.example.movieapplication.retrofit.APIClient;
import com.example.movieapplication.retrofit.APIInterface;
import com.facebook.shimmer.ShimmerFrameLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewMore extends AppCompatActivity {
    RecyclerView recyclerView;
    ShimmerFrameLayout shimmerFrameLayoutViewMore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_more);
        recyclerView = findViewById(R.id.viewMoreRecycler);

        shimmerFrameLayoutViewMore = findViewById(R.id.viewMoreShimmer);
        shimmerFrameLayoutViewMore.startShimmer();

        APIInterface api = APIClient.getViewMore().create(APIInterface.class);
        apiCall(Constants.apiKey, Constants.language, 1, api);
    }


    private void apiCall(String key, String language, int page, APIInterface api) {

        api.CALL_APIViewMore(key, language, page).enqueue(new Callback<Root>() {
            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {
                if (response.isSuccessful()) {
                    shimmerFrameLayoutViewMore.stopShimmer();
                    shimmerFrameLayoutViewMore.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    Root root = response.body();
                    GridLayoutManager layout = new GridLayoutManager(getApplicationContext(), 2);
                    recyclerView.setLayoutManager(layout);
                    ViewMoreAdapter object = new ViewMoreAdapter(getApplicationContext(), root);
                    recyclerView.setAdapter(object);
                }
            }

            @Override
            public void onFailure(Call<Root> call, Throwable t) {

            }
        });
    }
}