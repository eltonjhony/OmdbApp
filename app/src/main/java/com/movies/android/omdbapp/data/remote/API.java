package com.movies.android.omdbapp.data.remote;

import com.movies.android.omdbapp.data.model.DataResultWrapper;
import com.movies.android.omdbapp.data.model.Movie;
import com.movies.android.omdbapp.data.model.MovieDetail;
import com.movies.android.omdbapp.data.model.TvShows;
import com.movies.android.omdbapp.data.model.TvShowsDetail;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by eltonjhony on 3/31/17.
 */

public interface API {

    @GET("./movie/popular")
    Observable<DataResultWrapper<Movie>> fetchPopularMovies(@Query("api_key") String apiKey, @Query("page") int page);

    @GET("./search/movie")
    Observable<DataResultWrapper<Movie>> searchMovies(@Query("api_key") String apiKey, @Query("query") String query, @Query("page") int page);

    @GET("./search/tv")
    Observable<DataResultWrapper<TvShows>> searchTvShows(@Query("api_key") String apiKey, @Query("query") String query, @Query("page") int page);

    @GET("./movie/{movieId}")
    Observable<MovieDetail> getMovieById(@Path("movieId") String movieId, @Query("api_key") String apiKey);

    @GET("./tv/{tvId}")
    Observable<TvShowsDetail> getTvShowById(@Path("tvId") String tvId, @Query("api_key") String apiKey);
}
