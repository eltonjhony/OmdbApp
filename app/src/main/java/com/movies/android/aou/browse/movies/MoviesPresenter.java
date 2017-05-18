package com.movies.android.aou.browse.movies;

import android.text.TextUtils;

import com.android.annotations.NonNull;
import com.movies.android.aou.common.BasePresenter;
import com.movies.android.aou.data.model.Movie;
import com.movies.android.aou.data.model.MovieDetail;
import com.movies.android.aou.data.model.ContentSegmentEnum;
import com.movies.android.aou.data.model.Video;
import com.movies.android.aou.data.model.VideoWrapper;
import com.movies.android.aou.data.remote.API;
import com.movies.android.aou.data.remote.ErrorHandler;
import com.movies.android.aou.data.model.DataResultWrapper;
import com.movies.android.aou.infraestructure.ApplicationConfiguration;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by eltonjhony on 3/31/17.
 */
public class MoviesPresenter extends BasePresenter<MoviesContract.View> implements MoviesContract.Actions {

    private static final int FIRST_ITEM = 0;

    private API mApi;

    private Subscription mSubscription;

    @Inject
    public MoviesPresenter(API api) {
        this.mApi = api;
    }

    @Override
    public void loadItems(String query, ContentSegmentEnum contentSegmentEnum, int offSet) {
        if (TextUtils.isEmpty(query)) {
            getViewOrThrow().setLoading(true);
            final Observable<DataResultWrapper<Movie>> observable = this.getMovieEndpointBySegment(contentSegmentEnum, offSet);
            mSubscription = observable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<DataResultWrapper<Movie>>() {
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
                        public void onNext(DataResultWrapper<Movie> dataResultWrapper) {
                            if (dataResultWrapper.getPage() == 1) {
                                getView().showMovies(dataResultWrapper.getData());
                            } else {
                                getView().appendMoreMovies(dataResultWrapper.getData());
                            }
                        }
                    });

            if (offSet == 1) {
                this.retrieveFeaturedVideo(observable);
            }
        } else {
            this.searchMovies(query, offSet);
        }
    }

    @Override
    public void openDetails(@NonNull String id) {
        getViewOrThrow().setLoading(true);
        mSubscription = mApi.getMovieById(id, ApplicationConfiguration.getApiKey()).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MovieDetail>() {
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
                    public void onNext(MovieDetail movieDetail) {
                        getView().showMovieDetails(movieDetail);
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

    private void searchMovies(String query, int offSet) {
        getViewOrThrow().setLoading(true);
        mSubscription = mApi.searchMovies(ApplicationConfiguration.getApiKey(), query, offSet)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DataResultWrapper<Movie>>() {
                    @Override
                    public void onCompleted() {
                        getView().setLoading(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (isViewAttached()) {
                            ErrorHandler.Error error = new ErrorHandler(e).extract();
                            getView().setLoading(false);
                            getView().showError(error.message);
                        }
                    }

                    @Override
                    public void onNext(DataResultWrapper<Movie> dataResultWrapper) {
                        if (isViewAttached()) {
                            if (dataResultWrapper.getPage() == 1) {
                                getView().showMovies(dataResultWrapper.getData());
                            } else {
                                getView().appendMoreMovies(dataResultWrapper.getData());
                            }
                        }
                    }
                });
    }

    private void retrieveFeaturedVideo(Observable<DataResultWrapper<Movie>> observable) {
        getViewOrThrow().setLoading(true);
        mSubscription = observable.flatMap(movieDataResultWrapper -> {
            Movie movie = movieDataResultWrapper.getData().get(FIRST_ITEM);
            return mApi.getVideosById(movie.getId(), ApplicationConfiguration.getApiKey());
        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<VideoWrapper>() {
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
                    public void onNext(VideoWrapper videoWrapper) {
                        List<Video> results = videoWrapper.getResults();
                        if (results != null && !results.isEmpty()) {
                            Video video = results.get(FIRST_ITEM);
                            getView().setupFeaturedVideo(video.getKey());
                        }
                    }
                });
    }

    private Observable<DataResultWrapper<Movie>> getMovieEndpointBySegment(ContentSegmentEnum contentSegmentEnum, int offSet) {
        Observable<DataResultWrapper<Movie>> observable;
        switch (contentSegmentEnum) {
            case POPULAR:
                observable = mApi.fetchPopularMovies(ApplicationConfiguration.getApiKey(), offSet);
                break;
            case NOW_PLAYING:
                observable = mApi.fetchNowPlayingMovies(ApplicationConfiguration.getApiKey(), offSet);
                break;
            case TOP_RATED:
                observable = mApi.fetchTopRatedMovies(ApplicationConfiguration.getApiKey(), offSet);
                break;
            default:
                throw new RuntimeException("Invalid option " + contentSegmentEnum);
        }
        return observable;
    }
}
