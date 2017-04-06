package com.movies.android.omdbapp.infraestructure.dagger;

import com.movies.android.omdbapp.browse.movies.MoviesFragment;
import com.movies.android.omdbapp.browse.series.SeriesFragment;

import dagger.Component;

/**
 * Created by eltonjhony on 4/3/17.
 */
@Component(modules = {ServiceModule.class})
public interface ServiceComponent {

    void inject(MoviesFragment moviesFragment);

    void inject(SeriesFragment seriesFragment);
}
