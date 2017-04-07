package com.movies.android.omdbapp.data.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

/**
 * Created by eltonjhony on 4/7/17.
 */
@Parcel
public class TvShowsDetail extends ContentDetail {

    @SerializedName("first_air_date")
    String firstAirDate;

    @SerializedName("name")
    String name;

    @SerializedName("number_of_episodes")
    int numberOfEpisodes;

    @SerializedName("number_of_seasons")
    int numberOfSeasons;

    public TvShowsDetail() {
    }

    public TvShowsDetail(String id, String originalName, List<Genres> genre, String poster,
                         String popularity, String overview, String backdropPath,
                         String homepage, String voteAverage, String voteCount,
                         String firstAirDate, String name, int numberOfEpisodes,
                         int numberOfSeasons) {
        super(id, originalName, genre, poster, popularity, overview, backdropPath, homepage, voteAverage, voteCount);
        this.firstAirDate = firstAirDate;
        this.name = name;
        this.numberOfEpisodes = numberOfEpisodes;
        this.numberOfSeasons = numberOfSeasons;
    }
}
