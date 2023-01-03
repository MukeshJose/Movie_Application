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

public class ViewMoreAdapter extends RecyclerView.Adapter<ViewMoreAdapter.MyViewHolder> {
    Context context;

    public ViewMoreAdapter(Context context, Root root) {
        this.context = context;
        this.root = root;
    }

    Root root;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewmorecustomlayout, parent, false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Glide.with(context).load(Constants.baseUrlImage + root.results.get(position).poster_path).into(holder.image1);

        holder.image1.setOnClickListener(new View.OnClickListener() {
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
        ImageView image1, image2;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image1 = itemView.findViewById(R.id.image1);
        }
    }

    private void goToMovieDetails(String url, int position) {
        Intent intent = new Intent(context, MovieDetails.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("backDrop", url + root.results.get(position).backdrop_path);
        intent.putExtra("movieName", root.results.get(position).title);
        intent.putExtra("moviePoster", url + root.results.get(position).poster_path);
        intent.putExtra("releaseDate", root.results.get(position).release_date);
        intent.putExtra("language", root.results.get(position).original_language);
        intent.putExtra("popularity", root.results.get(position).popularity);
        intent.putExtra("averageRating", root.results.get(position).vote_average);
        intent.putExtra("overview", root.results.get(position).overview);
        context.startActivity(intent);
    }
}