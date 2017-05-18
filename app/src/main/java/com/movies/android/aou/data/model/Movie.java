package com.movies.android.aou.data.model;

import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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

    public boolean areContentsTheSame(Movie newItem) {
        return this.equals(newItem);
    }

    public boolean areItemsTheSame(Movie item2) {
        return this.getId().equals(item2.getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Movie)) return false;

        Movie movie = (Movie) o;

        return new EqualsBuilder()
                .append(getId(), movie.getId())
                .append(getTitle(), movie.getTitle())
                .append(getOriginalTitle(), movie.getOriginalTitle())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getId())
                .append(getTitle())
                .append(getOriginalTitle())
                .toHashCode();
    }
}
