package com.movies.android.aou.browse.movies;

import com.android.annotations.NonNull;
import com.movies.android.aou.data.model.Movie;
import com.movies.android.aou.data.model.MovieDetail;
import com.movies.android.aou.data.model.ContentSegmentEnum;

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

        void setupFeaturedVideo(String videoUrl);
    }

    interface Actions {
        void loadItems(String query, ContentSegmentEnum contentSegmentEnum, int offSet);

        void retrieveFeaturedVideo(List<Movie> movies);

        void openDetails(@NonNull String id);

        void onDestroy();
    }
}
