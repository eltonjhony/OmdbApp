package com.movies.android.omdbapp.movies;

import com.android.annotations.NonNull;
import com.movies.android.omdbapp.data.model.Movie;
import com.movies.android.omdbapp.data.model.MovieDetail;

import java.util.List;

/**
 * Created by eltonjhony on 3/31/17.
 */

public interface MoviesContract {

    interface View {
        void setLoading(boolean isActive);

        void showMovies(List<Movie> movies);

        void showMovieDetails(MovieDetail movieDetail);

        void showError(String message);
    }

    interface Actions {
        void loadMovies();

        void openDetails(@NonNull String id);
    }
}
