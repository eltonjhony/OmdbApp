package com.movies.android.omdbapp.data;

import com.movies.android.omdbapp.data.model.Movie;
import com.movies.android.omdbapp.data.model.MovieResultWrapper;

/**
 * Created by eltonjhony on 3/31/17.
 */
public interface MovieServiceApi {

    interface MovieServiceCallback<T> {
        void onLoaded(T filmes);
    }

    void getMovies(MovieServiceCallback<MovieResultWrapper> callback);

    void getMovie(String movieId, MovieServiceCallback<Movie> callback);
}
