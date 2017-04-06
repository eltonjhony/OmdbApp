package com.movies.android.omdbapp.infraestructure.dagger;

import com.movies.android.omdbapp.data.remote.API;
import com.movies.android.omdbapp.data.remote.RetrofitClient;
import com.movies.android.omdbapp.infraestructure.MyApplication;
import com.movies.android.omdbapp.infraestructure.preferences.SearcherPreferences;

import dagger.Module;
import dagger.Provides;

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
        return new SearcherPreferences(MyApplication.getMyApplication());
    }
}
