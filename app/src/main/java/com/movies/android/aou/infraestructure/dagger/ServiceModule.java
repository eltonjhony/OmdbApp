package com.movies.android.aou.infraestructure.dagger;

import com.movies.android.aou.data.remote.API;
import com.movies.android.aou.data.remote.RetrofitClient;
import com.movies.android.aou.infraestructure.preferences.CollapseFeaturedVideoPreferences;
import com.movies.android.aou.infraestructure.preferences.MainPagerPreferences;
import com.movies.android.aou.infraestructure.preferences.SearcherPreferences;
import com.movies.android.aou.main.adapters.CurrentAdapterPosition;

import dagger.Module;
import dagger.Provides;

import static com.movies.android.aou.infraestructure.Constants.PreferenceKeys.PAGER_KEY;

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
        return new SearcherPreferences();
    }

    @Provides
    MainPagerPreferences provideMainPagerPreferences() {
        return new MainPagerPreferences();
    }

    @Provides
    CollapseFeaturedVideoPreferences provideCollapseFeaturedVideoPreferences() {
        return new CollapseFeaturedVideoPreferences();
    }

    @Provides
    CurrentAdapterPosition provideCurrentAdapterPosition(MainPagerPreferences preferences) {
        return new CurrentAdapterPosition(preferences.getInt(PAGER_KEY));
    }
}
