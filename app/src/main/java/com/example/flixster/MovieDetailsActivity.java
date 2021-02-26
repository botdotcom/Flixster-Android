package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.flixster.models.Movie;

import org.parceler.Parcels;

public class MovieDetailsActivity extends AppCompatActivity {
    TextView movieTitleTextView;
    TextView movieOverviewTextView;
    RatingBar movieVotesRatingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        movieTitleTextView = findViewById(R.id.moviedetail_title_text_view);
        movieOverviewTextView = findViewById(R.id.moviedetail_overview_text_view);
        movieVotesRatingBar = findViewById(R.id.moviedetail_votes_rating_bar);

        Movie movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra("movie"));

        movieTitleTextView.setText(movie.getTitle());
        movieOverviewTextView.setText(movie.getOverview());
        movieVotesRatingBar.setRating((float) movie.getVotes());
    }
}