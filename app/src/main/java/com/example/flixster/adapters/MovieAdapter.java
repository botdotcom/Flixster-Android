package com.example.flixster.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixster.MovieDetailsActivity;
import com.example.flixster.R;
import com.example.flixster.models.Movie;

import org.parceler.Parcels;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    Context context;
    private List<Movie> movieList;
    private static final String TAG = "MovieAdapter";

    public MovieAdapter(Context context, List<Movie> movieList) {
        this.context = context;
        this.movieList = movieList;
    }

    // inflate view from XML and return the holder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder");
        LayoutInflater inflater = LayoutInflater.from(context);
        View movieView = inflater.inflate(R.layout.movie_item_layout, parent, false);
        return new ViewHolder(movieView);
    }

    // populate data into item through holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: " + position);
        Movie movie = movieList.get(position);
        holder.bind(movie);
    }

    // return total count of items
    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout movieContainer;
        private TextView movieTitleTextView;
        private TextView movieOverviewTextView;
        private ImageView moviePosterImageView;
        private TextView movieVotesTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            movieContainer = itemView.findViewById(R.id.movie_row_container);
            movieTitleTextView = (TextView) itemView.findViewById(R.id.movie_title_text_view);
            movieOverviewTextView = (TextView) itemView.findViewById(R.id.movie_overview_text_view);
            moviePosterImageView = (ImageView) itemView.findViewById(R.id.movie_image_view);
            movieVotesTextView = (TextView) itemView.findViewById(R.id.movie_votes_text_view);

        }

        public void bind(Movie movie) {
            movieTitleTextView.setText(movie.getTitle());
            movieOverviewTextView.setText(movie.getOverview());
            movieVotesTextView.setText(movie.getVotes() + " out of 10");
            String imageURL;

            // check for screen orientation and change image accordingly
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                imageURL = movie.getBackdropPath();
            }
            /*else if (Double.parseDouble(movie.getVotes()) >= 5.0){
                imageURL = movie.getBackdropPath();
            }*/
            else {
                imageURL = movie.getPosterPath();
            }

            // add poster image
            Glide.with(context).load(imageURL).into(moviePosterImageView);

            // add movie on click listener
            movieContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, MovieDetailsActivity.class);

                    intent.putExtra("movie", Parcels.wrap(movie));
                    context.startActivity(intent);
                }
            });
        }
    }
}
