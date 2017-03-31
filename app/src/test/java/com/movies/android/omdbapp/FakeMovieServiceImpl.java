package com.movies.android.omdbapp;

import com.movies.android.omdbapp.data.MovieServiceApi;
import com.movies.android.omdbapp.data.model.Movie;
import com.movies.android.omdbapp.data.model.MovieResultWrapper;

/**
 * Created by eltonjhony on 3/31/17.
 */
public class FakeMovieServiceImpl implements MovieServiceApi {

    private static final MovieResultWrapper MOVIES_SERVICE_DATA = new MovieResultWrapper();
    private static final Movie SINGLE_MOVIE_SERVICE_DATA = new Movie();

    @Override
    public void getMovies(MovieServiceCallback<MovieResultWrapper> callback) {
        callback.onLoaded(MOVIES_SERVICE_DATA);
    }

    @Override
    public void getMovie(String movieId, MovieServiceCallback<Movie> callback) {
        callback.onLoaded(SINGLE_MOVIE_SERVICE_DATA);
    }
}
