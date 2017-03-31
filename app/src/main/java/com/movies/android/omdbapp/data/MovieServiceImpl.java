package com.movies.android.omdbapp.data;

import com.movies.android.omdbapp.data.model.Movie;
import com.movies.android.omdbapp.data.model.MovieResultWrapper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by eltonjhony on 3/31/17.
 */

public class MovieServiceImpl implements MovieServiceApi {

    RetrofitEndpoint mRetrofit;

    public MovieServiceImpl() {
        mRetrofit = RetrofitClient.getClient().create(RetrofitEndpoint.class);
    }

    @Override
    public void getMovies(final MovieServiceCallback<MovieResultWrapper> callback) {
        Call<MovieResultWrapper> callMovies = mRetrofit.fetch("star wars", "json");
        callMovies.enqueue(new Callback<MovieResultWrapper>() {
            @Override
            public void onResponse(Call<MovieResultWrapper> call, Response<MovieResultWrapper> response) {
                if (response.code() == 200) {
                    MovieResultWrapper movieResultWrapper = response.body();
                    callback.onLoaded(movieResultWrapper);
                }
            }

            @Override
            public void onFailure(Call<MovieResultWrapper> call, Throwable t) {
                // error handler
            }
        });
    }

    @Override
    public void getMovie(String movieId, MovieServiceCallback<Movie> callback) {

    }
}
