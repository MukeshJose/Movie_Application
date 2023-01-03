package com.example.movieapplication.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movieapplication.Constants;
import com.example.movieapplication.MovieDetails;
import com.example.movieapplication.R;
import com.example.movieapplication.model.Root;
import com.example.movieapplication.retrofit.APIClient;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder> {
    Context context;

    public MovieAdapter(Context context, Root root) {
        this.context = context;
        this.root = root;
    }

    Root root;


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customlayout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        String id = root.results.get(position).id;
        APIClient api = new APIClient(id);

        Glide.with(context).load(Constants.baseUrlImage + root.results.get(position).poster_path).into(holder.moviePoster);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constants.goToMovieDetails(position,context,root);
            }
        });
    }


    @Override
    public int getItemCount() {
        return root.results.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView moviePoster;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            moviePoster = itemView.findViewById(R.id.moviePoster);
        }
    }


}
