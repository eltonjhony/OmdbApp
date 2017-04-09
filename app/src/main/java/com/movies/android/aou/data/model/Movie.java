package com.movies.android.aou.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by eltonjhony on 4/6/17.
 */
public class Movie extends Content implements Serializable {

    @SerializedName("title")
    private String title;

    @SerializedName("original_title")
    private String originalTitle;

    public Movie() {
    }

    public Movie(String id, String posterUrl, String title) {
        super(id, posterUrl);
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }
}
