package com.movies.android.aou.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by eltonjhony on 10/04/17.
 */

public class Video implements Serializable {

    @SerializedName("id")
    private String id;

    @SerializedName("key")
    private String key;

    @SerializedName("name")
    private String name;

    @SerializedName("type")
    private String type;

    public Video() {
    }

    public Video(String id, String key, String name, String type) {
        this.id = id;
        this.key = key;
        this.name = name;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
}
