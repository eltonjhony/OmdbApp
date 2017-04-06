package com.movies.android.omdbapp.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by eltonjhony on 3/31/17.
 */

public class Content implements Serializable {

    @SerializedName("imdbID")
    private String id;

    @SerializedName("Title")
    private String title;

    @SerializedName("Year")
    private String year;

    @SerializedName("Poster")
    private String posterUrl;

    public Content() {
    }

    public Content(String id, String title, String year, String posterUrl) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.posterUrl = posterUrl;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public String getPosterUrl() {
        return posterUrl;
    }
}
