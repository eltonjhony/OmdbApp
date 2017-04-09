package com.movies.android.aou;

import android.text.TextUtils;
import android.util.Log;

import com.movies.android.aou.data.model.DataResultWrapper;
import com.movies.android.aou.data.model.Movie;
import com.movies.android.aou.data.remote.API;
import com.movies.android.aou.infraestructure.MyLog;
import com.movies.android.aou.browse.movies.MoviesContract;
import com.movies.android.aou.browse.movies.MoviesPresenter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
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

import static com.movies.android.aou.data.remote.ErrorHandler.Error.GENERIC_CODE;
import static com.movies.android.aou.data.remote.ErrorHandler.Error.GENERIC_MESSAGE;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;

/**
 * Created by eltonjhony on 3/31/17.
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest({MyLog.class, Log.class, TextUtils.class})
public class MoviesPresenterTest {

    private static List<Movie> MOVIES = new ArrayList<Movie>(){{
        add(new Movie("1", "MOVIE A", "2015"));
        add(new Movie("2", "MOVIE B", "2016"));
        add(new Movie("3", "MOVIE C", "2017"));
        add(new Movie("4", "MOVIE D", "2018"));
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
        PowerMockito.mockStatic(MyLog.class, Log.class, TextUtils.class);
        mPresenter = new MoviesPresenter(mApi, mView);
        RxAndroidPlugins.getInstance().registerSchedulersHook(new RxAndroidSchedulersHook() {
            @Override
            public Scheduler getMainThreadScheduler() {
                return Schedulers.immediate();
            }
        });
    }

    @Test
    public void loadPopularMoviesAndPopulateScreen() {

        // When fetchPopularMovies API return mocked data from Backend
        when(mApi.fetchPopularMovies(anyString(), anyInt())).thenReturn(Observable.just(MOVIE_RESULT_DATA));

        // Mock textUtils verification to start fetch popular movies
        PowerMockito.when(TextUtils.isEmpty(anyString())).thenReturn(true);

        // When the presenter is called to load movies.
        mPresenter.loadItems("test", anyInt());

        // Then, the loading should be called with true argument
        verify(mView, times(1)).setLoading(true);

        // Then, the fetchPopularMovies API should be called.
        verify(mApi).fetchPopularMovies(anyString(), anyInt());

        // the progress is hid and the movies is shown in the screen
        verify(mView, times(1)).setLoading(false);
        verify(mView).showMovies(MOVIE_RESULT_DATA.getData());
    }

    @Test
    public void loadPopularMoviesAndReceiveGenericError() throws Exception {

        // When fetchPopularMovies API return generic exception
        when(mApi.fetchPopularMovies(anyString(), anyInt())).thenReturn(Observable.error(new Exception(GENERIC_MESSAGE)));

        // Mock textUtils verification to start fetch popular movies
        PowerMockito.when(TextUtils.isEmpty(anyString())).thenReturn(true);

        // When the presenter is called to load movies.
        mPresenter.loadItems("test", anyInt());

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
