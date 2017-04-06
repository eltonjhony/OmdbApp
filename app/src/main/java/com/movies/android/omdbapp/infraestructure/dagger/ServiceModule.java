package com.movies.android.omdbapp.infraestructure.dagger;

import com.movies.android.omdbapp.data.remote.OmdbApi;
import com.movies.android.omdbapp.data.remote.RetrofitClient;

import dagger.Module;
import dagger.Provides;

/**
 * Created by eltonjhony on 4/3/17.
 */
@Module
public class ServiceModule {

    @Provides
    OmdbApi provideMovieApi() {
        return RetrofitClient.getClient().create(OmdbApi.class);
    }
}
