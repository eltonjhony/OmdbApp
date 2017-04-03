package com.movies.android.omdbapp.data.model;

import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * Created by eltonjhony on 4/3/17.
 */

public class MovieDetail implements Serializable {

    @SerializedName("Title")
    private String title;

    @SerializedName("Year")
    private String year;

    @SerializedName("Rated")
    private String rated;

    @SerializedName("Released")
    private String released;

    @SerializedName("Runtime")
    private String runtime;

    @SerializedName("Genre")
    private String genre;

    @SerializedName("Director")
    private String director;

    @SerializedName("Awards")
    private String awards;

    @SerializedName("Poster")
    private String poster;

    @SerializedName("Language")
    private String language;

    @SerializedName("Country")
    private String country;

    @SerializedName("Ratings")
    private List<Rating> ratings;

    @SerializedName("imdbRating")
    private String imdbRating;

    @SerializedName("Type")
    private String type;

    @SerializedName("Production")
    private String production;

    @SerializedName("Website")
    private String website;

    public MovieDetail() {
    }

    public MovieDetail(String title, String year, String rated, String released,
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

    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public String getRated() {
        return rated;
    }

    public String getReleased() {
        return released;
    }

    public String getRuntime() {
        return runtime;
    }

    public String getGenre() {
        return genre;
    }

    public String getDirector() {
        return director;
    }

    public String getAwards() {
        return awards;
    }

    public String getPoster() {
        return poster;
    }

    public String getLanguage() {
        return language;
    }

    public String getCountry() {
        return country;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public String getImdbRating() {
        return imdbRating;
    }

    public String getType() {
        return type;
    }

    public String getProduction() {
        return production;
    }

    public String getWebsite() {
        return website;
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
