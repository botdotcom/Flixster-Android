package com.example.flixster;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

public class MoviesDbClient {
    private static final String MOVIE_API_KEY = "a07e22bc18f5cb106bfe4cc1f83ad8ed";
    private static final String API_BASE_URL = "https://api.themoviedb.org/3/movie/";

    private AsyncHttpClient client;

    public MoviesDbClient() {
        this.client = new AsyncHttpClient();
    }

    private String getApiUrl(String relativeUrl) {
        return API_BASE_URL + relativeUrl;
    }

    public void getMoviesNowPlaying(JsonHttpResponseHandler handler) {
        String url = getApiUrl(String.format("now_playing?api_key=%s", MOVIE_API_KEY));
        client.get(url, handler);
    }

    public void getMoviesTrailerVideos(int movieId, JsonHttpResponseHandler handler) {
        String url = getApiUrl(String.format("%d/videos?api_key=%s", movieId, MOVIE_API_KEY));
        client.get(url, handler);
    }
}
