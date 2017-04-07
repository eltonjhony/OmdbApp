package com.movies.android.omdbapp.data.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

/**
 * Created by eltonjhony on 4/7/17.
 */
@Parcel
public class MovieDetail extends ContentDetail {

    @SerializedName("title")
    String title;

    @SerializedName("release_date")
    String released;

    @SerializedName("runtime")
    String runtime;

    public MovieDetail() {
    }

    public MovieDetail(String id, String originalName, List<Genres> genre, String poster,
                       String popularity, String overview, String backdropPath,
                       String homepage, String voteAverage, String voteCount,
                       String title, String released, String runtime) {
        super(id, originalName, genre, poster, popularity, overview, backdropPath, homepage, voteAverage, voteCount);
        this.title = title;
        this.released = released;
        this.runtime = runtime;
    }
}
