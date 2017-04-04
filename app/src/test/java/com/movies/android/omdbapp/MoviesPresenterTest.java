package com.movies.android.omdbapp;

import android.util.Log;

import com.movies.android.omdbapp.data.model.Movie;
import com.movies.android.omdbapp.data.model.MovieResultWrapper;
import com.movies.android.omdbapp.data.remote.MovieApi;
import com.movies.android.omdbapp.infraestructure.MyLog;
import com.movies.android.omdbapp.movies.MoviesContract;
import com.movies.android.omdbapp.movies.MoviesPresenter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Scheduler;
import rx.android.plugins.RxAndroidPlugins;
import rx.android.plugins.RxAndroidSchedulersHook;
import rx.schedulers.Schedulers;

import static com.movies.android.omdbapp.data.remote.ErrorHandler.Error.GENERIC_CODE;
import static com.movies.android.omdbapp.data.remote.ErrorHandler.Error.GENERIC_MESSAGE;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;

/**
 * Created by eltonjhony on 3/31/17.
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest({MyLog.class, Log.class})
public class MoviesPresenterTest {

    private static List<Movie> MOVIES = new ArrayList<Movie>(){{
        add(new Movie("1", "MOVIE A", "2015", ""));
        add(new Movie("2", "MOVIE B", "2016", ""));
        add(new Movie("3", "MOVIE C", "2017", ""));
        add(new Movie("4", "MOVIE D", "2018", ""));
    }};

    private static MovieResultWrapper MOVIE_RESULT_DATA = new MovieResultWrapper(MOVIES);

    @Mock
    private MovieApi mApi;

    @Mock
    private MoviesContract.View mView;

    private MoviesPresenter mPresenter;

    @Before
    public void setupMoviesPresenter() {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(MyLog.class, Log.class);
        mPresenter = new MoviesPresenter(mApi, mView);
        RxAndroidPlugins.getInstance().registerSchedulersHook(new RxAndroidSchedulersHook() {
            @Override
            public Scheduler getMainThreadScheduler() {
                return Schedulers.immediate();
            }
        });
    }

    @Test
    public void loadMoviesAndPopulateScreen() {

        // When fetch API return mocked data from Backend
        when(mApi.fetch(anyString())).thenReturn(Observable.just(MOVIE_RESULT_DATA));

        // When the presenter is called to load movies.
        mPresenter.loadMovies();

        // Then, the loading should be called with true argument
        verify(mView, times(1)).setLoading(true);

        // Then, the fetch API should be called.
        verify(mApi).fetch(anyString());

        // the progress is hid and the movies is shown in the screen
        verify(mView, times(1)).setLoading(false);
        verify(mView).showMovies(MOVIE_RESULT_DATA.movies);
    }

    @Test
    public void loadMoviesAndReceiveGenericError() throws Exception {

        // When fetch API return generic exception
        when(mApi.fetch(anyString())).thenReturn(Observable.error(new Exception(GENERIC_MESSAGE)));

        // When the presenter is called to load movies.
        mPresenter.loadMovies();

        // Then, the loading should be called with true argument
        verify(mView, times(1)).setLoading(true);

        // Then, the flow should log the error with generic message
        verifyStatic(times(1));
        MyLog.error(GENERIC_CODE, GENERIC_MESSAGE);

        // Then, the load should back to false
        verify(mView, times(1)).setLoading(false);

        // Then, the view showError should be called
        verify(mView).showError(GENERIC_MESSAGE);
    }

    @After
    public void tearDown() {
        RxAndroidPlugins.getInstance().reset();
    }
}
