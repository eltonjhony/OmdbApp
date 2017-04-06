package com.movies.android.omdbapp.infraestructure;

import android.text.TextUtils;
import android.util.Log;

import com.movies.android.omdbapp.BuildConfig;

/**
 * Created by eltonjhony on 4/4/17.
 */

public class MyLog {

    private static final String DEFAULT_TAG = "MyLog";

    private static final int LOG_LEVEL_DEBUG = 4;
    private static final int LOG_LEVEL_INFO = 3;
    private static final int LOG_LEVEL_WARN = 2;
    private static final int LOG_LEVEL_ERROR = 1;

    private MyLog() {
        // just to disable default constructor
    }

    public static void info(String tag, String message) {
        if (BuildConfig.LOG_LEVEL >= LOG_LEVEL_INFO && !TextUtils.isEmpty(message)) {
            Log.i(tag, message);
        }
    }

    public static void debug(String tag, String message) {
        if (BuildConfig.LOG_LEVEL >= LOG_LEVEL_DEBUG && !TextUtils.isEmpty(message)) {
            Log.v(tag, message);
        }
    }

    public static void warn(String tag, String message) {
        if (BuildConfig.LOG_LEVEL >= LOG_LEVEL_WARN && !TextUtils.isEmpty(message)) {
            Log.w(tag, message);
        }
    }

    public static void error(String tag, String message) {
        if (BuildConfig.LOG_LEVEL >= LOG_LEVEL_ERROR && !TextUtils.isEmpty(message)) {
            Log.e(tag, message);
        }
    }

    public static void info(String message) {
        if (BuildConfig.LOG_LEVEL >= LOG_LEVEL_INFO && !TextUtils.isEmpty(message)) {
            Log.i(DEFAULT_TAG, message);
        }
    }

    public static void debug(String message) {
        if (BuildConfig.LOG_LEVEL >= LOG_LEVEL_DEBUG && !TextUtils.isEmpty(message)) {
            Log.v(DEFAULT_TAG, message);
        }
    }

    public static void warn(String message) {
        if (BuildConfig.LOG_LEVEL >= LOG_LEVEL_WARN && !TextUtils.isEmpty(message)) {
            Log.w(DEFAULT_TAG, message);
        }
    }

    public static void error(String message) {
        if (BuildConfig.LOG_LEVEL >= LOG_LEVEL_ERROR && !TextUtils.isEmpty(message)) {
            Log.e(DEFAULT_TAG, message);
        }
    }
}
