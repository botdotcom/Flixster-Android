package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.models.Movie;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import okhttp3.Headers;

public class MovieDetailsActivity extends YouTubeBaseActivity {
    private static final String YOUTUBE_API_KEY = "AIzaSyBLtOjyvfu_ImIrUlflllbfmohoPSnncI4";
    private static final String YOUTUBE_VIDEO_URL = "https://api.themoviedb.org/3/movie/%d/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    private static final String ACTIVITY_TAG = "MovieDetailsActivity";

    YouTubePlayerView youTubePlayerView;
    TextView movieTitleTextView;
    TextView movieOverviewTextView;
    RatingBar movieVotesRatingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtube_player);
        movieTitleTextView = (TextView) findViewById(R.id.moviedetail_title_text_view);
        movieOverviewTextView = (TextView) findViewById(R.id.moviedetail_overview_text_view);
        movieVotesRatingBar = (RatingBar) findViewById(R.id.moviedetail_votes_rating_bar);

        Movie movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra("movie"));

        movieTitleTextView.setText(movie.getTitle());
        movieOverviewTextView.setText(movie.getOverview());
        movieVotesRatingBar.setRating((float) movie.getVotes());

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(String.format(YOUTUBE_VIDEO_URL, movie.getId()), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(ACTIVITY_TAG, "On success");
                try {
                    JSONArray results = json.jsonObject.getJSONArray("results");
                    Log.i(ACTIVITY_TAG, "Got results");

                    if (results.length() == 0)
                        return;
                    if (!results.getJSONObject(0).getString("site").equals("YouTube"))
                        return;
                    String videoKey = results.getJSONObject(0).getString("key");

                    initializeYoutubeVideoKey(videoKey);
//                    Log.i(ACTIVITY_TAG, "Video key: " + videoKey);
                } catch (JSONException e) {
                    Log.e(ACTIVITY_TAG, "JSON exception hit", e);
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String s, Throwable throwable) {
                Log.d(ACTIVITY_TAG, "On failure");
            }
        });
    }

    private void initializeYoutubeVideoKey(final String videoKey) {
        youTubePlayerView.initialize(YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                Log.d(ACTIVITY_TAG, "On success");
                youTubePlayer.cueVideo(videoKey);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.d(ACTIVITY_TAG, "On failure");
            }
        });
    }
}