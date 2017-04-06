package com.movies.android.omdbapp.browse.series;

import com.android.annotations.NonNull;
import com.movies.android.omdbapp.data.model.ContentDetail;
import com.movies.android.omdbapp.data.model.DataResultWrapper;
import com.movies.android.omdbapp.data.remote.ErrorHandler;
import com.movies.android.omdbapp.data.remote.OmdbApi;
import com.movies.android.omdbapp.infraestructure.MyLog;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by eltonjhony on 3/31/17.
 */

public class SeriesPresenter implements SeriesContract.Actions {

    private OmdbApi mApi;
    private SeriesContract.View mView;

    public SeriesPresenter(OmdbApi api, SeriesContract.View view) {
        this.mView = view;
        this.mApi = api;
    }

    @Override
    public void loadItems(String query) {
        final String searchText = query != null ? query : "Chicago";
        mView.setLoading(true);
        mApi.fetch(searchText, "series").subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DataResultWrapper>() {
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
                    public void onNext(DataResultWrapper dataResultWrapper) {
                        mView.setLoading(false);
                        mView.showSeries(dataResultWrapper.getData());
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
                        mView.showSeriesDetails(contentDetail);
                    }
                });
    }
}
