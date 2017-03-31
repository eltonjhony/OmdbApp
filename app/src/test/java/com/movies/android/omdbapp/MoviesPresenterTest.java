package com.movies.android.omdbapp;

import com.movies.android.omdbapp.data.MovieServiceApi;
import com.movies.android.omdbapp.data.model.Movie;
import com.movies.android.omdbapp.data.model.MovieResultWrapper;
import com.movies.android.omdbapp.movies.MoviesContract;
import com.movies.android.omdbapp.movies.MoviesPresenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eltonjhony on 3/31/17.
 */

public class MoviesPresenterTest {

    private static List<Movie> MOVIES = new ArrayList<Movie>(){{
        add(new Movie("1", "MOVIE A", "2015", ""));
        add(new Movie("2", "MOVIE B", "2016", ""));
        add(new Movie("3", "MOVIE C", "2017", ""));
        add(new Movie("4", "MOVIE D", "2018", ""));
    }};

    private static MovieResultWrapper MOVIE_RESULT_DATA = new MovieResultWrapper(MOVIES);

    @Mock
    private FakeMovieServiceImpl mApi;

    @Mock
    private MoviesContract.View mView;

    // simulate callback behavior
    @Captor
    private ArgumentCaptor<MovieServiceApi.MovieServiceCallback> mLoadMoviesCallbackCaptor;

    private MoviesPresenter mPresenter;

    @Before
    public void setupMoviesPresenter() {
        MockitoAnnotations.initMocks(this);
        mPresenter = new MoviesPresenter(mApi, mView);
    }

    @Test
    public void loadMoviesAndPopulateScreen() {

        mPresenter.loadMovies();

        // callback is captured and triggered with fake data.
        Mockito.verify(mApi).getMovies(mLoadMoviesCallbackCaptor.capture());
        mLoadMoviesCallbackCaptor.getValue().onLoaded(MOVIE_RESULT_DATA);

        // the progress is hid and the movies is shown in the screen
        Mockito.verify(mView).setLoading(false);
        Mockito.verify(mView).showMovies(MOVIE_RESULT_DATA.movies);
    }
}
