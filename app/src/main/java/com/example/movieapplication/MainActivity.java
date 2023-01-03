package com.example.movieapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movieapplication.adapter.MovieAdapter;
import com.example.movieapplication.adapter.MovieAdapter2;
import com.example.movieapplication.adapter.SliderImageAdapter;
import com.example.movieapplication.model.Root;
import com.example.movieapplication.retrofit.APIClient;
import com.example.movieapplication.retrofit.APIInterface;
import com.facebook.shimmer.ShimmerDrawable;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashMap;
import java.util.Stack;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView, recyclerView2;
    ViewPager viewPager;
    TextView button, subHeading, subHeading2, applicationTitle;
    ImageView searchButton;
    //    ShimmerFrameLayout shimmerFrameLayout,shimmerFrameLayout2,shimmerFrameLayoutSlider;
    ShimmerFrameLayout shimmerFrameLayout;


    private HashMap<String, Stack<Fragment>> mStacks;
    public static final String TAB_HOME = "tab_home";
    public static final String TAB_DASHBOARD = "tab_dashboard";
    public static final String TAB_NOTIFICATIONS = "tab_notifications";

    private String mCurrentTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView2 = findViewById(R.id.recyclerView2);
        viewPager = findViewById(R.id.imageSlider);
        button = findViewById(R.id.button);
//        shimmerFrameLayout = findViewById(R.id.shimmerView);
//        shimmerFrameLayout2 = findViewById(R.id.shimmerView2);
//        shimmerFrameLayoutSlider= findViewById(R.id.sliderImageShimmer);
        subHeading = findViewById(R.id.subHeading);
        subHeading2 = findViewById(R.id.subHeading2);
        applicationTitle = findViewById(R.id.applicationTitle);
        searchButton = findViewById(R.id.searchButton);
        shimmerFrameLayout = findViewById(R.id.fullScreenShimmer);

//        shimmerFrameLayout.startShimmer();
//        shimmerFrameLayout2.startShimmer();
//        shimmerFrameLayoutSlider.startShimmer();
        shimmerFrameLayout.startShimmer();

        APIInterface api = APIClient.getClient().create(APIInterface.class);
        apiCallPopular(Constants.apiKey, Constants.language, 1, api);
        apiCallTopRated(Constants.apiKey, Constants.language, 1, api);
        apiCallSliderImage(Constants.apiKey, Constants.language, 1, api);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ViewMore.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });
    }

    private void apiCallSliderImage(String key, String language, int page, APIInterface api) {
        api.CALL_APISliderImage(key, language, page).enqueue(new Callback<Root>() {
            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {
                if (response.isSuccessful()) {
//                    shimmerFrameLayoutSlider.stopShimmer();
//                    shimmerFrameLayoutSlider.setVisibility(View.GONE);

                    Root root = response.body();
                    SliderImageAdapter sliderImageAdapter = new SliderImageAdapter(getApplicationContext(), root);
                    viewPager.setAdapter(sliderImageAdapter);
                }
            }

            @Override
            public void onFailure(Call<Root> call, Throwable t) {

            }
        });
    }

    private void apiCallTopRated(String key, String language, int page, APIInterface api) {
        api.CALL_APITopRated(key, language, page).enqueue(new Callback<Root>() {
            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {
                if (response.isSuccessful()) {
//                    shimmerFrameLayout2.stopShimmer();
//                    shimmerFrameLayout2.setVisibility(View.GONE);
//                    subHeading.setVisibility(View.VISIBLE);

                    Root root = response.body();
                    LinearLayoutManager layoutNew = new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false);
                    recyclerView2.setLayoutManager(layoutNew);
                    MovieAdapter2 object = new MovieAdapter2(getApplicationContext(), root);
                    recyclerView2.setAdapter(object);
                }
            }

            @Override
            public void onFailure(Call<Root> call, Throwable t) {

            }
        });
    }

    private void apiCallPopular(String key, String language, int page, APIInterface api) {

        api.CALL_APIPopular(key, language, page).enqueue(new Callback<Root>() {
            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {
                if (response.isSuccessful()) {
//                    shimmerFrameLayout.stopShimmer();
//                    subHeading2.setVisibility(View.VISIBLE);
//                    button.setVisibility(View.VISIBLE);
//                    shimmerFrameLayout.setVisibility(View.GONE);
//                    recyclerView.setVisibility(View.VISIBLE);
                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);
                    applicationTitle.setVisibility(View.VISIBLE);
                    searchButton.setVisibility(View.VISIBLE);
                    subHeading.setVisibility(View.VISIBLE);
                    subHeading2.setVisibility(View.VISIBLE);
                    button.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.VISIBLE);
                    recyclerView2.setVisibility(View.VISIBLE);
                    viewPager.setVisibility(View.VISIBLE);
                    Root root = response.body();
                    LinearLayoutManager layout = new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false);
                    recyclerView.setLayoutManager(layout);
                    MovieAdapter obj = new MovieAdapter(getApplicationContext(), root);
                    recyclerView.setAdapter(obj);
                } else {
                    Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Root> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}