package com.movies.android.aou.browse.tvshows;

import android.text.TextUtils;

import com.android.annotations.NonNull;
import com.movies.android.aou.common.BasePresenter;
import com.movies.android.aou.data.model.ContentSegmentEnum;
import com.movies.android.aou.data.model.DataResultWrapper;
import com.movies.android.aou.data.model.TvShows;
import com.movies.android.aou.data.model.TvShowsDetail;
import com.movies.android.aou.data.remote.ErrorHandler;
import com.movies.android.aou.data.remote.API;
import com.movies.android.aou.infraestructure.ApplicationConfiguration;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by eltonjhony on 3/31/17.
 */

public class TvShowsPresenter extends BasePresenter<TvShowsContract.View> implements TvShowsContract.Actions {

    private API mApi;

    private Subscription mSubscription;

    @Inject
    public TvShowsPresenter(API api) {
        this.mApi = api;
    }

    @Override
    public void loadItems(String query, ContentSegmentEnum contentSegmentEnum, int offSet) {
        if (TextUtils.isEmpty(query)) {
            getViewOrThrow().setLoading(true);
            Observable<DataResultWrapper<TvShows>> observable = this.getTvShowsEndpointBySegment(contentSegmentEnum, offSet);
            mSubscription = observable
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<DataResultWrapper<TvShows>>() {
                        @Override
                        public void onCompleted() {
                            getView().setLoading(false);
                        }

                        @Override
                        public void onError(Throwable e) {
                            ErrorHandler.Error error = new ErrorHandler(e).extract();
                            getView().showError(error.message);
                        }

                        @Override
                        public void onNext(DataResultWrapper<TvShows> dataResultWrapper) {
                            if (dataResultWrapper.getPage() == 1) {
                                getView().displayTvShows(dataResultWrapper.getData());
                            } else {
                                getView().displayMoreTvShows(dataResultWrapper.getData());
                            }
                        }
                    });
        } else {
            this.searchTvShows(query, offSet);
        }
    }

    @Override
    public void openDetails(@NonNull String id) {
        getViewOrThrow().setLoading(true);
        mSubscription = mApi.getTvShowById(id, ApplicationConfiguration.getApiKey())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TvShowsDetail>() {
                    @Override
                    public void onCompleted() {
                        getView().setLoading(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        ErrorHandler.Error error = new ErrorHandler(e).extract();
                        getViewOrThrow().showError(error.message);
                    }

                    @Override
                    public void onNext(TvShowsDetail tvShowsDetail) {
                        getView().displayTvShowsDetails(tvShowsDetail);
                    }
                });
    }

    @Override
    public void onDestroy() {
        if (mSubscription != null) {
            mSubscription.unsubscribe();
        }
        super.detachView();
    }

    private void searchTvShows(String query, int offSet) {
        getViewOrThrow().setLoading(true);
        mSubscription = mApi.searchTvShows(ApplicationConfiguration.getApiKey(), query, offSet)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DataResultWrapper<TvShows>>() {
                    @Override
                    public void onCompleted() {
                        getView().setLoading(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        ErrorHandler.Error error = new ErrorHandler(e).extract();
                        getView().showError(error.message);
                    }

                    @Override
                    public void onNext(DataResultWrapper<TvShows> dataResultWrapper) {
                        if (dataResultWrapper.getPage() == 1) {
                            getView().displayTvShows(dataResultWrapper.getData());
                        } else {
                            getView().displayMoreTvShows(dataResultWrapper.getData());
                        }
                    }
                });
    }

    private Observable<DataResultWrapper<TvShows>> getTvShowsEndpointBySegment(ContentSegmentEnum contentSegmentEnum,
                                                                               int offSet) {
        Observable<DataResultWrapper<TvShows>> observable;
        switch (contentSegmentEnum) {
            case POPULAR:
                observable = mApi.fetchPopularTvShows(ApplicationConfiguration.getApiKey(), offSet);
                break;

            case ON_THE_AIR:
                observable = mApi.fetchOnTheAirTvShows(ApplicationConfiguration.getApiKey(), offSet);
                break;

            case TOP_RATED:
                observable = mApi.fetchTopRatedTvShows(ApplicationConfiguration.getApiKey(), offSet);
                break;

            default:
                throw new RuntimeException("Invalid option " + contentSegmentEnum);
        }

        return observable;
    }
}
