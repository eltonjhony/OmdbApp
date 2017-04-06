package com.movies.android.omdbapp.browse.movies;

import android.text.TextUtils;

import com.android.annotations.NonNull;
import com.movies.android.omdbapp.data.model.Movie;
import com.movies.android.omdbapp.data.remote.API;
import com.movies.android.omdbapp.data.remote.ErrorHandler;
import com.movies.android.omdbapp.data.model.ContentDetail;
import com.movies.android.omdbapp.data.model.DataResultWrapper;
import com.movies.android.omdbapp.infraestructure.ApplicationConfiguration;
import com.movies.android.omdbapp.infraestructure.MyLog;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by eltonjhony on 3/31/17.
 */

public class MoviesPresenter implements MoviesContract.Actions {

    private API mApi;
    private MoviesContract.View mView;

    public MoviesPresenter(API api, MoviesContract.View view) {
        this.mView = view;
        this.mApi = api;
    }

    @Override
    public void loadItems(String query) {
        if (TextUtils.isEmpty(query)) {
            mView.setLoading(true);
            mApi.fetchPopularMovies(ApplicationConfiguration.getApiKey()).subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<DataResultWrapper<Movie>>() {
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
                        public void onNext(DataResultWrapper<Movie> dataResultWrapper) {
                            mView.setLoading(false);
                            mView.showMovies(dataResultWrapper.getData());
                        }
                    });

        } else {
            searchMovies(query);
        }
    }

    private void searchMovies(String query) {
        mView.setLoading(true);
        mApi.searchMovies(ApplicationConfiguration.getApiKey(), query)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DataResultWrapper<Movie>>() {
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
                    public void onNext(DataResultWrapper<Movie> dataResultWrapper) {
                        mView.setLoading(false);
                        mView.showMovies(dataResultWrapper.getData());
                    }
                });
    }

    @Override
    public void openDetails(@NonNull String id) {
        mView.setLoading(true);
        mApi.getById(id).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ContentDetail>() {
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
                    public void onNext(ContentDetail contentDetail) {
                        mView.setLoading(false);
                        mView.showMovieDetails(contentDetail);
                    }
                });
    }
}
