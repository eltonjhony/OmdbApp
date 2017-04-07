package com.movies.android.omdbapp.browse.tvshows;

import com.android.annotations.NonNull;
import com.movies.android.omdbapp.data.model.DataResultWrapper;
import com.movies.android.omdbapp.data.model.TvShows;
import com.movies.android.omdbapp.data.model.TvShowsDetail;
import com.movies.android.omdbapp.data.remote.ErrorHandler;
import com.movies.android.omdbapp.data.remote.API;
import com.movies.android.omdbapp.infraestructure.ApplicationConfiguration;
import com.movies.android.omdbapp.infraestructure.MyLog;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by eltonjhony on 3/31/17.
 */

public class TvShowsPresenter implements TvShowsContract.Actions {

    private API mApi;
    private TvShowsContract.View mView;

    public TvShowsPresenter(API api, TvShowsContract.View view) {
        this.mView = view;
        this.mApi = api;
    }

    @Override
    public void loadItems(String query) {
        final String searchText = query != null ? query : "Chicago"; //TODO To improve this!
        mView.setLoading(true);
        mApi.searchTvShows(ApplicationConfiguration.getApiKey(), searchText)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DataResultWrapper<TvShows>>() {
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
                    public void onNext(DataResultWrapper<TvShows> dataResultWrapper) {
                        mView.setLoading(false);
                        mView.displayTvShows(dataResultWrapper.getData());
                    }
                });
    }

    @Override
    public void openDetails(@NonNull String id) {
        mView.setLoading(true);
        mApi.getTvShowById(id, ApplicationConfiguration.getApiKey())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TvShowsDetail>() {
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
                    public void onNext(TvShowsDetail tvShowsDetail) {
                        mView.setLoading(false);
                        mView.displayTvShowsDetails(tvShowsDetail);
                    }
                });
    }
}
