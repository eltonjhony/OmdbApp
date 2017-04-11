package com.movies.android.aou.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by eltonjhony on 10/04/17.
 */
public class VideoWrapper implements Serializable {

    @SerializedName("id")
    private String id;

    @SerializedName("results")
    private List<Video> results;

    public VideoWrapper() {
    }

    public VideoWrapper(String id, List<Video> results) {
        this.id = id;
        this.results = results;
    }

    public String getId() {
        return id;
    }

    public List<Video> getResults() {
        return results;
    }
}
