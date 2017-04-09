package com.movies.android.omdbapp.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eltonjhony on 3/31/17.
 */
public class DataResultWrapper<T> {

    @SerializedName("page")
    private int page;

    @SerializedName("results")
    private List<T> results;

    public DataResultWrapper() {
    }

    public DataResultWrapper(List<T> results) {
        this.results = results;
    }

    public int getPage() {
        return page;
    }

    public List<T> getData() {
        if (results == null) return new ArrayList<>();
        return results;
    }
}
