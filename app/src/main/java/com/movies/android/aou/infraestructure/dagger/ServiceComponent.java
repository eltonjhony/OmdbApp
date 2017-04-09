package com.movies.android.aou.infraestructure.dagger;

import com.movies.android.aou.browse.movies.MoviesFragment;
import com.movies.android.aou.browse.tvshows.TvShowsFragment;
import com.movies.android.aou.details.DetailsActivity;
import com.movies.android.aou.main.MainActivity;

import dagger.Component;

/**
 * Created by eltonjhony on 4/3/17.
 */
@Component(modules = {ServiceModule.class})
public interface ServiceComponent {

    void inject(MoviesFragment moviesFragment);

    void inject(TvShowsFragment tvShowsFragment);

    void inject(MainActivity mainActivity);

    void inject(DetailsActivity detailsActivity);
}
