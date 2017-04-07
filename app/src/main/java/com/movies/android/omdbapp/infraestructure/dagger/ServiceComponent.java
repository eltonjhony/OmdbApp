package com.movies.android.omdbapp.infraestructure.dagger;

import com.movies.android.omdbapp.browse.movies.MoviesFragment;
import com.movies.android.omdbapp.browse.tvshows.TvShowsFragment;

import dagger.Component;

/**
 * Created by eltonjhony on 4/3/17.
 */
@Component(modules = {ServiceModule.class})
public interface ServiceComponent {

    void inject(MoviesFragment moviesFragment);

    void inject(TvShowsFragment tvShowsFragment);
}
