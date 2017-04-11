package com.movies.android.aou.browse.movies;

import android.text.TextUtils;

import com.android.annotations.NonNull;
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

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by eltonjhony on 3/31/17.
 */

public class MoviesPresenter implements MoviesContract.Actions {

    private static final int FIRST_ITEM = 0;

    private API mApi;
    private MoviesContract.View mView;

    public MoviesPresenter(API api, MoviesContract.View view) {
        this.mView = view;
        this.mApi = api;
    }

    @Override
    public void loadItems(String query, ContentSegmentEnum contentSegmentEnum, int offSet) {
        if (TextUtils.isEmpty(query)) {
            mView.setLoading(true);
            Observable<DataResultWrapper<Movie>> observable = this.getMovieEndpointBySegment(contentSegmentEnum, offSet);
            observable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<DataResultWrapper<Movie>>() {
                        @Override
                        public void onCompleted() {
                            mView.setLoading(false);
                        }

                        @Override
                        public void onError(Throwable e) {
                            ErrorHandler.Error error = new ErrorHandler(e).extract();
                            mView.showError(error.message);
                        }

                        @Override
                        public void onNext(DataResultWrapper<Movie> dataResultWrapper) {
                            if (dataResultWrapper.getPage() == 1) {
                                mView.showMovies(dataResultWrapper.getData());
                            } else {
                                mView.appendMoreMovies(dataResultWrapper.getData());
                            }
                        }
                    });

        } else {
            this.searchMovies(query, offSet);
        }
    }

    @Override
    public void retrieveFeaturedVideo(List<Movie> movies) {
        Movie movie = movies.get(FIRST_ITEM);
        mView.setLoading(true);
        mApi.getVideosById(movie.getId(), ApplicationConfiguration.getApiKey())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<VideoWrapper>() {
                    @Override
                    public void onCompleted() {
                        mView.setLoading(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        ErrorHandler.Error error = new ErrorHandler(e).extract();
                        mView.showError(error.message);
                    }

                    @Override
                    public void onNext(VideoWrapper videoWrapper) {
                        List<Video> results = videoWrapper.getResults();
                        if (results != null && !results.isEmpty()) {
                            Video video = results.get(FIRST_ITEM);
                            mView.setupFeaturedVideo(video.getKey());
                        }
                    }
                });

    }

    @Override
    public void openDetails(@NonNull String id) {
        mView.setLoading(true);
        mApi.getMovieById(id, ApplicationConfiguration.getApiKey()).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MovieDetail>() {
                    @Override
                    public void onCompleted() {
                        mView.setLoading(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        ErrorHandler.Error error = new ErrorHandler(e).extract();
                        mView.showError(error.message);
                    }

                    @Override
                    public void onNext(MovieDetail movieDetail) {
                        mView.showMovieDetails(movieDetail);
                    }
                });
    }

    private void searchMovies(String query, int offSet) {
        mView.setLoading(true);
        mApi.searchMovies(ApplicationConfiguration.getApiKey(), query, offSet)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DataResultWrapper<Movie>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        ErrorHandler.Error error = new ErrorHandler(e).extract();
                        mView.setLoading(false);
                        mView.showError(error.message);
                    }

                    @Override
                    public void onNext(DataResultWrapper<Movie> dataResultWrapper) {
                        mView.setLoading(false);
                        if (dataResultWrapper.getPage() == 1) {
                            mView.showMovies(dataResultWrapper.getData());
                        } else {
                            mView.appendMoreMovies(dataResultWrapper.getData());
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
