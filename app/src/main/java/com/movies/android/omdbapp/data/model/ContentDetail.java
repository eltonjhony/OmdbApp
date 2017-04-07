package com.movies.android.omdbapp.data.model;

import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.parceler.Parcel;

import java.util.List;

/**
 * Created by eltonjhony on 4/3/17.
 */
@Parcel
public class ContentDetail {

    @SerializedName("id")
    String id;

    @SerializedName("original_name")
    String originalName;

    @SerializedName("genres")
    List<Genres> genre;

    @SerializedName("poster_path")
    String poster;

    @SerializedName("popularity")
    String popularity;

    @SerializedName("overview")
    String overview;

    @SerializedName("backdrop_path")
    String backdropPath;

    @SerializedName("homepage")
    String homepage;

    @SerializedName("vote_average")
    String voteAverage;

    @SerializedName("vote_count")
    String voteCount;

    public ContentDetail() {
    }

    public ContentDetail(String id, String originalName, List<Genres> genre, String poster,
                         String popularity, String overview, String backdropPath,
                         String homepage, String voteAverage, String voteCount) {
        this.id = id;
        this.originalName = originalName;
        this.genre = genre;
        this.poster = poster;
        this.popularity = popularity;
        this.overview = overview;
        this.backdropPath = backdropPath;
        this.homepage = homepage;
        this.voteAverage = voteAverage;
        this.voteCount = voteCount;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("originalName", originalName)
                .append("genre", genre)
                .append("poster", poster)
                .toString();
    }
}
