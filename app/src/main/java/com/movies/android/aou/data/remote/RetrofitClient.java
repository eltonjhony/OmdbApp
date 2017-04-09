package com.movies.android.aou.data.remote;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.movies.android.aou.BuildConfig;
import com.movies.android.aou.infraestructure.ApplicationConfiguration;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by eltonjhony on 3/31/17.
 */
public class RetrofitClient {

    private static Retrofit retrofit = null;

    private static Gson gson = new GsonBuilder().create();

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(ApplicationConfiguration.getDefaultHost())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(new OkHttpClient.Builder().addInterceptor(setupLogInterceptor()).build())
                    .build();
        }
        return retrofit;
    }

    @NonNull
    private static HttpLoggingInterceptor setupLogInterceptor() {
        Level level;
        if (BuildConfig.DEBUG) {
            level = Level.BODY;
        } else {
            level = Level.NONE;
        }
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(level);
        return logging;
    }
}
