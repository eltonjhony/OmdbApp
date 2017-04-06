package com.movies.android.omdbapp.infraestructure;

import android.annotation.SuppressLint;

import com.movies.android.omdbapp.BuildConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by eltonjhony on 4/6/17.
 */
@SuppressWarnings("unused")
public class ApplicationConfiguration {

    @SuppressLint("UseSparseArrays")
    private static final Map<String, String> applicationParameterCacheMap = new HashMap<>();

    static {
        applicationParameterCacheMap.put(Constants.ApplicationConfigurationParameter.DEFAULT_HOST, BuildConfig.DEFAULT_HOST);
        applicationParameterCacheMap.put(Constants.ApplicationConfigurationParameter.API_KEY, BuildConfig.API_KEY);
    }

    public static String getDefaultHost() {
        return applicationParameterCacheMap.get(Constants.ApplicationConfigurationParameter.DEFAULT_HOST);
    }

    public static String getApiKey() {
        return applicationParameterCacheMap.get(Constants.ApplicationConfigurationParameter.API_KEY);
    }


}
