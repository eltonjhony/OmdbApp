package com.movies.android.omdbapp.infraestructure;

import android.util.Log;

/**
 * Created by eltonjhony on 4/4/17.
 */

public class MyLog {

    public static void info(String tag, String message) {
        Log.i(tag, message);
    }

    public static void debug(String tag, String message) {
        Log.d(tag, message);
    }

    public static void error(String tag, String message) {
        Log.e(tag, message);
    }
}
