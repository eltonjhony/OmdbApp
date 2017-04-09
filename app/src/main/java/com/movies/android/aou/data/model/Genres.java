package com.movies.android.aou.data.model;

import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.parceler.Parcel;

/**
 * Created by eltonjhony on 4/7/17.
 */
@Parcel
public class Genres {

    @SerializedName("id")
    String id;

    @SerializedName("name")
    String name;

    public Genres() {
    }

    public Genres(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("name", name)
                .toString();
    }
}
