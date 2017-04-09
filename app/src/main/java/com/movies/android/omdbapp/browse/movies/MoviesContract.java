package com.movies.android.omdbapp.browse.movies;

import com.android.annotations.NonNull;
import com.movies.android.omdbapp.data.model.Movie;
import com.movies.android.omdbapp.data.model.MovieDetail;

import java.util.List;

/**
 * Created by eltonjhony on 3/31/17.
 */

public interface MoviesContract {

    interface View {

        int INITIAL_OFF_SET = 1;

        void setLoading(boolean isActive);

        void showMovies(List<Movie> movies);

        void showMovieDetails(MovieDetail movieDetail);

        void showError(String message);

        void appendMoreMovies(List<Movie> data);
    }

    interface Actions {
        void loadItems(String query, int offSet);

        void openDetails(@NonNull String id);
    }
}
