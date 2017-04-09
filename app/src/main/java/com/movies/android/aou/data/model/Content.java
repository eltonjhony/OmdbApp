package com.movies.android.aou.data.model;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by eltonjhony on 3/31/17.
 */
public class Content implements Serializable {

    private static final String BASE_POSTER_URL = "http://image.tmdb.org/t/p/";
    private static final String IMG_SIZE_185 = "w185";

    @SerializedName("id")
    private String id;

    @SerializedName("poster_path")
    private String posterUrl;

    public Content() {
    }

    public Content(String id, String posterUrl) {
        this.id = id;
        this.posterUrl = posterUrl;
    }

    public String getId() {
        return id;
    }

    public String getPosterUrl() {
        if (TextUtils.isEmpty(this.posterUrl)) {
            return null;
        }
        StringBuilder builder = new StringBuilder(BASE_POSTER_URL);
        builder.append(IMG_SIZE_185).append("/").append(posterUrl);
        return builder.toString();
    }
}
