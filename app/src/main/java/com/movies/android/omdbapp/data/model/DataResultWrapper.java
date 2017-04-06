package com.movies.android.omdbapp.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eltonjhony on 3/31/17.
 */
public class DataResultWrapper {

    @SerializedName("Search")
    public List<Content> content;

    public DataResultWrapper() {
    }

    public DataResultWrapper(List<Content> content) {
        this.content = content;
    }

    public List<Content> getData() {
        if (content == null) return new ArrayList<>();
        return content;
    }
}
