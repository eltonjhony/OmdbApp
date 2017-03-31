package com.movies.android.omdbapp.movies;

import com.android.annotations.NonNull;
import com.movies.android.omdbapp.data.MovieServiceApi;
import com.movies.android.omdbapp.data.model.Movie;
import com.movies.android.omdbapp.data.model.MovieResultWrapper;

/**
 * Created by eltonjhony on 3/31/17.
 */

public class MoviesPresenter implements MoviesContract.Actions {

    private MovieServiceApi mApi;
    private MoviesContract.View mView;

    public MoviesPresenter(MovieServiceApi api, MoviesContract.View view) {
        this.mApi = api;
        this.mView = view;
    }

    @Override
    public void loadMovies() {
        mView.setLoading(true);

        mApi.getMovies(new MovieServiceApi.MovieServiceCallback<MovieResultWrapper>() {
            @Override
            public void onLoaded(MovieResultWrapper movieResultWrapper) {
                mView.setLoading(false);
                mView.showMovies(movieResultWrapper.movies);
            }
        });
    }

    @Override
    public void openDetails(@NonNull Movie movie) {

    }
}
