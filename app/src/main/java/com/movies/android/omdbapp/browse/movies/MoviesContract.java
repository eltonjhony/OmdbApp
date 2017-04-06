package com.movies.android.omdbapp.browse.movies;

import com.android.annotations.NonNull;
import com.movies.android.omdbapp.data.model.Content;
import com.movies.android.omdbapp.data.model.ContentDetail;
import com.movies.android.omdbapp.data.model.Movie;

import java.util.List;

/**
 * Created by eltonjhony on 3/31/17.
 */

public interface MoviesContract {

    interface View {
        void setLoading(boolean isActive);

        void showMovies(List<Movie> movies);

        void showMovieDetails(ContentDetail contentDetail);

        void showError(String message);
    }

    interface Actions {
        void loadItems(String query);

        void openDetails(@NonNull String id);
    }
}
