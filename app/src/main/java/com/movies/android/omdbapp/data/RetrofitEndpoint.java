package com.movies.android.omdbapp.data;

import com.movies.android.omdbapp.data.model.MovieResultWrapper;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by eltonjhony on 3/31/17.
 */

public interface RetrofitEndpoint {

    @GET("./")
    Call<MovieResultWrapper> fetch(@Query("s") String q, @Query("r") String format);
}
