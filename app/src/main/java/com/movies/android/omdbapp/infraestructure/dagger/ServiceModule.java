package com.movies.android.omdbapp.infraestructure.dagger;

import android.text.TextUtils;

import com.movies.android.omdbapp.data.remote.API;
import com.movies.android.omdbapp.data.remote.RetrofitClient;
import com.movies.android.omdbapp.infraestructure.Constants;
import com.movies.android.omdbapp.infraestructure.MyApplication;
import com.movies.android.omdbapp.infraestructure.preferences.PagerIndexPreferences;
import com.movies.android.omdbapp.infraestructure.preferences.SearcherPreferences;
import com.movies.android.omdbapp.main.adapters.CurrentAdapterPosition;

import dagger.Module;
import dagger.Provides;

import static com.movies.android.omdbapp.main.adapters.MainPageAdapter.MOVIES_INDEX;

/**
 * Created by eltonjhony on 4/3/17.
 */
@Module
public class ServiceModule {

    @Provides
    API provideMovieApi() {
        return RetrofitClient.getClient().create(API.class);
    }

    @Provides
    SearcherPreferences provideSearcherPreferences() {
        return new SearcherPreferences(MyApplication.getMyApplication(), Constants.PreferenceKeys.SEARCHER);
    }

    @Provides
    PagerIndexPreferences providePagerIndexPreferences() {
        return new PagerIndexPreferences(MyApplication.getMyApplication(), Constants.PreferenceKeys.PAGER_INDEX);
    }

    @Provides
    CurrentAdapterPosition provideCurrentAdapterPosition(PagerIndexPreferences preferences) {
        final String currentIndex = preferences.get();
        return new CurrentAdapterPosition(TextUtils.isEmpty(currentIndex) ? MOVIES_INDEX : Integer.parseInt(currentIndex));
    }
}
