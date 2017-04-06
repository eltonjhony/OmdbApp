package com.movies.android.omdbapp.data.remote;

import com.movies.android.omdbapp.data.model.ContentDetail;
import com.movies.android.omdbapp.data.model.DataResultWrapper;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by eltonjhony on 3/31/17.
 */

public interface OmdbApi {

    @GET("./")
    Observable<DataResultWrapper> fetch(@Query("s") String q, @Query("type") String type);

    @GET("./")
    Observable<ContentDetail> getById(@Query("i") String id);
}
