package com.movies.android.omdbapp;

import android.util.Log;

import com.movies.android.omdbapp.data.model.Content;
import com.movies.android.omdbapp.data.model.DataResultWrapper;
import com.movies.android.omdbapp.data.remote.API;
import com.movies.android.omdbapp.infraestructure.MyLog;
import com.movies.android.omdbapp.browse.movies.MoviesContract;
import com.movies.android.omdbapp.browse.movies.MoviesPresenter;

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

    private static List<Content> MOVIES = new ArrayList<Content>(){{
        add(new Content("1", "MOVIE A", "2015", ""));
        add(new Content("2", "MOVIE B", "2016", ""));
        add(new Content("3", "MOVIE C", "2017", ""));
        add(new Content("4", "MOVIE D", "2018", ""));
    }};

    private static DataResultWrapper MOVIE_RESULT_DATA = new DataResultWrapper(MOVIES);

    @Mock
    private API mApi;

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

        // When fetchPopularMovies API return mocked data from Backend
        when(mApi.fetchPopularMovies(anyString(), anyString())).thenReturn(Observable.just(MOVIE_RESULT_DATA));

        // When the presenter is called to load movies.
        mPresenter.loadItems("test");

        // Then, the loading should be called with true argument
        verify(mView, times(1)).setLoading(true);

        // Then, the fetchPopularMovies API should be called.
        verify(mApi).fetchPopularMovies(anyString(), anyString());

        // the progress is hid and the movies is shown in the screen
        verify(mView, times(1)).setLoading(false);
        verify(mView).showMovies(MOVIE_RESULT_DATA.getData());
    }

    @Test
    public void loadMoviesAndReceiveGenericError() throws Exception {

        // When fetchPopularMovies API return generic exception
        when(mApi.fetchPopularMovies(anyString(), anyString())).thenReturn(Observable.error(new Exception(GENERIC_MESSAGE)));

        // When the presenter is called to load movies.
        mPresenter.loadItems("test");

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
