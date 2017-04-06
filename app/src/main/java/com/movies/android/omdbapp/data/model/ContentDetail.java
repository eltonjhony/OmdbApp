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

    @SerializedName("Title")
    String title;

    @SerializedName("Year")
    String year;

    @SerializedName("Rated")
    String rated;

    @SerializedName("Released")
    String released;

    @SerializedName("Runtime")
    String runtime;

    @SerializedName("Genre")
    String genre;

    @SerializedName("Director")
    String director;

    @SerializedName("Awards")
    String awards;

    @SerializedName("Poster")
    String poster;

    @SerializedName("Language")
    String language;

    @SerializedName("Country")
    String country;

    @SerializedName("Ratings")
    List<Rating> ratings;

    @SerializedName("imdbRating")
    String imdbRating;

    @SerializedName("Type")
    String type;

    @SerializedName("Production")
    String production;

    @SerializedName("Website")
    String website;

    public ContentDetail() {
    }

    public ContentDetail(String title, String year, String rated, String released,
                         String runtime, String genre, String director, String awards,
                         String poster, String language, String country, List<Rating> ratings,
                         String imdbRating, String type, String production, String website) {
        this.title = title;
        this.year = year;
        this.rated = rated;
        this.released = released;
        this.runtime = runtime;
        this.genre = genre;
        this.director = director;
        this.awards = awards;
        this.poster = poster;
        this.language = language;
        this.country = country;
        this.ratings = ratings;
        this.imdbRating = imdbRating;
        this.type = type;
        this.production = production;
        this.website = website;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("title", title)
                .append("genre", genre)
                .append("director", director)
                .append("language", language)
                .append("country", country)
                .append("runtime", runtime)
                .toString();
    }
}
