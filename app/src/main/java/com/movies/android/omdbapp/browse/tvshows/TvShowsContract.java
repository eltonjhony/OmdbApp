package com.movies.android.omdbapp.browse.tvshows;

import com.android.annotations.NonNull;
import com.movies.android.omdbapp.data.model.TvShows;
import com.movies.android.omdbapp.data.model.TvShowsDetail;

import java.util.List;

/**
 * Created by eltonjhony on 3/31/17.
 */

public interface TvShowsContract {

    interface View {

        int INITIAL_OFF_SET = 1;

        void setLoading(boolean isActive);

        void displayTvShows(List<TvShows> tvShows);

        void displayTvShowsDetails(TvShowsDetail tvShowsDetail);

        void showError(String message);

        void displayMoreTvShows(List<TvShows> data);
    }

    interface Actions {
        void loadItems(String query, int offSet);

        void openDetails(@NonNull String id);
    }
}
