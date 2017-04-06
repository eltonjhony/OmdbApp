package com.movies.android.omdbapp.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by eltonjhony on 4/6/17.
 */
public class Serie extends Content implements Serializable {

    @SerializedName("name")
    private String name;

    public Serie() {
    }

    public Serie(String id, String posterUrl, String name) {
        super(id, posterUrl);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
