package com.movies.android.omdbapp.infraestructure.dagger;

import com.movies.android.omdbapp.data.remote.MovieApi;
import com.movies.android.omdbapp.data.remote.RetrofitClient;

import dagger.Module;
import dagger.Provides;

/**
 * Created by eltonjhony on 4/3/17.
 */
@Module
public class ServiceModule {

    @Provides
    MovieApi provideMovieApi() {
        return RetrofitClient.getClient().create(MovieApi.class);
    }
}
