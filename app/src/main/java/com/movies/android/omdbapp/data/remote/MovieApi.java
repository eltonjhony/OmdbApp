package com.movies.android.omdbapp.data.remote;

import com.movies.android.omdbapp.data.model.MovieDetail;
import com.movies.android.omdbapp.data.model.MovieResultWrapper;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by eltonjhony on 3/31/17.
 */

public interface MovieApi {

    @GET("./")
    Observable<MovieResultWrapper> fetch(@Query("s") String q);

    @GET("./")
    Observable<MovieDetail> getById(@Query("i") String id);
}
