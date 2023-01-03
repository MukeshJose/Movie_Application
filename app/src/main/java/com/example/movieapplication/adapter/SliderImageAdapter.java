package com.example.movieapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.movieapplication.Constants;
import com.example.movieapplication.MovieDetails;
import com.example.movieapplication.R;
import com.example.movieapplication.model.Root;

import java.util.Objects;

public class SliderImageAdapter extends PagerAdapter {

    Context context;
    Root root;

    public SliderImageAdapter(Context context, Root root) {
        this.context = context;
        this.root = root;
    }

    @Override
    public int getCount() {
        return root.results.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        // inflating the item.xml
        View itemView = LayoutInflater.from(container.getContext()).inflate(R.layout.imagesliderwindow, container,false);

        // referencing the image view from the item.xml file
        ImageView imageView = (ImageView) itemView.findViewById(R.id.sliderImage);

        // setting the image in the imageView
        Glide.with(context).load(Constants.baseUrlImage+root.results.get(position).backdrop_path).into(imageView);

        // Adding the View
        Objects.requireNonNull(container).addView(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             Constants.goToMovieDetails(position,context,root);
            }
        });
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView((View) object);
    }
}

