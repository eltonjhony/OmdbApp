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
    public String title;

    @SerializedName("Year")
    public String year;

    @SerializedName("Rated")
    public String rated;

    @SerializedName("Released")
    public String released;

    @SerializedName("Runtime")
    public String runtime;

    @SerializedName("Genre")
    public String genre;

    @SerializedName("Director")
    public String director;

    @SerializedName("Awards")
    public String awards;

    @SerializedName("Poster")
    public String poster;

    @SerializedName("Language")
    public String language;

    @SerializedName("Country")
    public String country;

    @SerializedName("Ratings")
    public List<Rating> ratings;

    @SerializedName("imdbRating")
    public String imdbRating;

    @SerializedName("Type")
    public String type;

    @SerializedName("Production")
    public String production;

    @SerializedName("Website")
    public String website;

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
