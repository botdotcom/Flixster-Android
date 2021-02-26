package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.adapters.MovieAdapter;
import com.example.flixster.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {
    private static final String NOW_PLAYING_MOVIES_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    private static final String ACTIVITY_TAG = "MainActivity";
    List<Movie> movieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movieList = new ArrayList<>();
        
        // create adapter
        MovieAdapter movieAdapter = new MovieAdapter(this, movieList);

        // set adapter on recycler view
        RecyclerView recyclerView = findViewById(R.id.movie_list_recycler_view);
        recyclerView.setAdapter(movieAdapter);

        // set layout manager on recycler view
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // get JSON data from API
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(NOW_PLAYING_MOVIES_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(ACTIVITY_TAG, "On success");
                JSONObject jsonObject = json.jsonObject;

                try {
                    JSONArray movieResults = jsonObject.getJSONArray("results");
                    Log.i(ACTIVITY_TAG, "Got results");
                    movieList.addAll(Movie.moviesFromJsonArray(movieResults));
                    movieAdapter.notifyDataSetChanged();
                    Log.i(ACTIVITY_TAG, "Movies: " + movieList.size());
                }
                catch (JSONException je) {
                    Log.e(ACTIVITY_TAG, "JSON exception hit", je);
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String s, Throwable throwable) {
                Log.d(ACTIVITY_TAG, "On failure");
            }
        });
    }

    public List<Movie> getMovieList() {
        return movieList;
    }
}