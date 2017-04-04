package com.movies.android.omdbapp.movies;

import com.android.annotations.NonNull;
import com.movies.android.omdbapp.data.remote.ErrorHandler;
import com.movies.android.omdbapp.data.model.MovieDetail;
import com.movies.android.omdbapp.data.model.MovieResultWrapper;
import com.movies.android.omdbapp.data.remote.MovieApi;
import com.movies.android.omdbapp.infraestructure.MyLog;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by eltonjhony on 3/31/17.
 */

public class MoviesPresenter implements MoviesContract.Actions {

    private MovieApi mApi;
    private MoviesContract.View mView;

    public MoviesPresenter(MovieApi api, MoviesContract.View view) {
        this.mView = view;
        this.mApi = api;
    }

    @Override
    public void loadMovies() {
        mView.setLoading(true);
        mApi.fetch("star wars").subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MovieResultWrapper>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        ErrorHandler.Error error = new ErrorHandler(e).extract();
                        MyLog.error(error.code, error.message);
                        mView.setLoading(false);
                        mView.showError(error.message);
                    }

                    @Override
                    public void onNext(MovieResultWrapper movieResultWrapper) {
                        mView.setLoading(false);
                        mView.showMovies(movieResultWrapper.movies);
                    }
                });
    }

    @Override
    public void openDetails(@NonNull String id) {
        mView.setLoading(true);
        mApi.getById(id).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MovieDetail>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        ErrorHandler.Error error = new ErrorHandler(e).extract();
                        MyLog.error(error.code, error.message);
                        mView.setLoading(false);
                        mView.showError(error.message);
                    }

                    @Override
                    public void onNext(MovieDetail movieDetail) {
                        mView.setLoading(false);
                        mView.showMovieDetails(movieDetail);
                    }
                });
    }
}
