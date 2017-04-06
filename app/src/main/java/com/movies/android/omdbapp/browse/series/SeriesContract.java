package com.movies.android.omdbapp.browse.series;

import com.android.annotations.NonNull;
import com.movies.android.omdbapp.data.model.Content;
import com.movies.android.omdbapp.data.model.ContentDetail;
import com.movies.android.omdbapp.data.model.Serie;

import java.util.List;

/**
 * Created by eltonjhony on 3/31/17.
 */

public interface SeriesContract {

    interface View {
        void setLoading(boolean isActive);

        void showSeries(List<Serie> movies);

        void showSeriesDetails(ContentDetail contentDetail);

        void showError(String message);
    }

    interface Actions {
        void loadItems(String query);

        void openDetails(@NonNull String id);
    }
}
