package com.movies.android.omdbapp.data.remote;

import com.movies.android.omdbapp.data.model.ContentDetail;
import com.movies.android.omdbapp.data.model.DataResultWrapper;
import com.movies.android.omdbapp.data.model.Movie;
import com.movies.android.omdbapp.data.model.Serie;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by eltonjhony on 3/31/17.
 */

public interface API {

    @GET("./movie/popular")
    Observable<DataResultWrapper<Movie>> fetchPopularMovies(@Query("api_key") String api_key);

    @GET("./search/movie")
    Observable<DataResultWrapper<Movie>> searchMovies(@Query("api_key") String api_key, @Query("query") String query);

    @GET("./search/tv")
    Observable<DataResultWrapper<Serie>> searchTvShows(@Query("api_key") String api_key, @Query("query") String query);

    @GET("./")
    Observable<ContentDetail> getById(@Query("i") String id);
}
