package com.movies.android.aou.infraestructure.dagger;

import android.text.TextUtils;

import com.movies.android.aou.data.remote.API;
import com.movies.android.aou.data.remote.RetrofitClient;
import com.movies.android.aou.infraestructure.MyApplication;
import com.movies.android.aou.infraestructure.preferences.SimplePreferences;
import com.movies.android.aou.main.adapters.CurrentAdapterPosition;

import dagger.Module;
import dagger.Provides;

import static com.movies.android.aou.infraestructure.Constants.PreferenceKeys.PAGER_KEY;
import static com.movies.android.aou.main.adapters.MainPageAdapter.MOVIES_INDEX;

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
    SimplePreferences provideSearcherPreferences() {
        return new SimplePreferences(MyApplication.getMyApplication()) {
        };
    }

    @Provides
    CurrentAdapterPosition provideCurrentAdapterPosition(SimplePreferences preferences) {
        final String currentIndex = preferences.get(PAGER_KEY);
        return new CurrentAdapterPosition(TextUtils.isEmpty(currentIndex) ? MOVIES_INDEX : Integer.parseInt(currentIndex));
    }
}
