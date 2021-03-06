package com.example.flixster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Movie {
    private String posterPath;
    private String backdropPath;
    private int id;
    private String title;
    private String overview;
    private double votes;

    // added for the parceler library
    public Movie() {}

    public Movie(JSONObject jsonObject) throws JSONException {
        posterPath = jsonObject.getString("poster_path");
        backdropPath = jsonObject.getString("backdrop_path");
        id = jsonObject.getInt("id");
        title = jsonObject.getString("title");
        overview = jsonObject.getString("overview");
        votes = jsonObject.getDouble("vote_average");
    }

    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", posterPath);
    }

    public String getBackdropPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", backdropPath);
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public double getVotes() {
        return votes;
    }

    public int getId() {
        return id;
    }

    public static List<Movie> moviesFromJsonArray(JSONArray movieJsonArray) throws JSONException {
        List<Movie> movieList = new ArrayList<>();

        for (int i = 0; i < movieJsonArray.length(); i++) {
            movieList.add(new Movie(movieJsonArray.getJSONObject(i)));
        }

        return movieList;
    }
}
