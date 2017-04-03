package com.movies.android.omdbapp.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by eltonjhony on 4/3/17.
 */
public class Rating implements Serializable {

    @SerializedName("Source")
    private String source;

    @SerializedName("Value")
    private String value;

    public Rating() {
    }

    public Rating(String source, String value) {
        this.source = source;
        this.value = value;
    }

    public String getSource() {
        return source;
    }

    public String getValue() {
        return value;
    }
}
