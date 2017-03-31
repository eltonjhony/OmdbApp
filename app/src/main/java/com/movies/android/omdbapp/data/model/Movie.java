package com.movies.android.omdbapp.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by eltonjhony on 3/31/17.
 */

public class Movie {

    @SerializedName("imdbID")
    public String id;

    @SerializedName("Title")
    public String title;

    @SerializedName("Year")
    public String year;

    @SerializedName("Poster")
    public String posterUrl;

    public Movie() {
    }

    public Movie(String id, String title, String year, String posterUrl) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.posterUrl = posterUrl;
    }
}
