package com.movies.android.aou.data.remote;

import com.movies.android.aou.data.model.DataResultWrapper;
import com.movies.android.aou.data.model.Movie;
import com.movies.android.aou.data.model.MovieDetail;
import com.movies.android.aou.data.model.TvShows;
import com.movies.android.aou.data.model.TvShowsDetail;
import com.movies.android.aou.data.model.VideoWrapper;

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

    @GET("./movie/top_rated")
    Observable<DataResultWrapper<Movie>> fetchTopRatedMovies(@Query("api_key") String apiKey, @Query("page") int page);

    @GET("./movie/now_playing")
    Observable<DataResultWrapper<Movie>> fetchNowPlayingMovies(@Query("api_key") String apiKey, @Query("page") int page);

    @GET("./search/movie")
    Observable<DataResultWrapper<Movie>> searchMovies(@Query("api_key") String apiKey, @Query("query") String query, @Query("page") int page);

    @GET("./movie/{movieId}/videos")
    Observable<VideoWrapper> getVideosById(@Path("movieId") String movieId, @Query("api_key") String apiKey);

    @GET("./search/tv")
    Observable<DataResultWrapper<TvShows>> searchTvShows(@Query("api_key") String apiKey, @Query("query") String query, @Query("page") int page);

    @GET("./tv/popular")
    Observable<DataResultWrapper<TvShows>> fetchPopularTvShows(@Query("api_key") String apiKey, @Query("page") int page);

    @GET("./tv/on_the_air")
    Observable<DataResultWrapper<TvShows>> fetchOnTheAirTvShows(@Query("api_key") String apiKey, @Query("page") int page);

    @GET("./tv/top_rated")
    Observable<DataResultWrapper<TvShows>> fetchTopRatedTvShows(@Query("api_key") String apiKey, @Query("page") int page);

    @GET("./movie/{movieId}")
    Observable<MovieDetail> getMovieById(@Path("movieId") String movieId, @Query("api_key") String apiKey);

    @GET("./tv/{tvId}")
    Observable<TvShowsDetail> getTvShowById(@Path("tvId") String tvId, @Query("api_key") String apiKey);
}
