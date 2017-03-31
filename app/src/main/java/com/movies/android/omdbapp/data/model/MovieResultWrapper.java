package com.movies.android.omdbapp.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by eltonjhony on 3/31/17.
 */
public class MovieResultWrapper {

    @SerializedName("Search")
    public List<Movie> movies;

    public MovieResultWrapper() {
    }

    public MovieResultWrapper(List<Movie> movies) {
        this.movies = movies;
    }
}
