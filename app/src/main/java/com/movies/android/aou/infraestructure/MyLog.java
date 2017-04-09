package com.movies.android.aou.infraestructure;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.movies.android.aou.BuildConfig;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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

    public static void error(Object tag, String message) {
        if (BuildConfig.LOG_LEVEL >= LOG_LEVEL_ERROR && !TextUtils.isEmpty(message)) {
            Log.e(String.valueOf(tag), message);
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

    /**
     * Just for debug purpose
     */
    public static void logToFile(String message) {
        if (!BuildConfig.DEBUG) {
            return;
        }

        File log = new File(Environment.getExternalStorageDirectory(), "myAppLogFile.txt");
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new FileWriter(log.getAbsolutePath(), log.exists()));
            out.write(message);
            out.close();
        } catch (IOException e) {
            // silent
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    // silent
                }
            }
        }
    }
}
