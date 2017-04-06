package com.movies.android.omdbapp.browse.movies;

import com.android.annotations.NonNull;
import com.movies.android.omdbapp.data.model.Content;
import com.movies.android.omdbapp.data.model.ContentDetail;

import java.util.List;

/**
 * Created by eltonjhony on 3/31/17.
 */

public interface MoviesContract {

    interface View {
        void setLoading(boolean isActive);

        void showMovies(List<Content> movies);

        void showMovieDetails(ContentDetail contentDetail);

        void showError(String message);
    }

    interface Actions {
        void loadItems(String query);

        void openDetails(@NonNull String id);
    }
}
