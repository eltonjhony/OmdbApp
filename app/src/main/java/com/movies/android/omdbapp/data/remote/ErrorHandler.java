package com.movies.android.omdbapp.data.remote;

import android.accounts.NetworkErrorException;

import com.movies.android.omdbapp.infraestructure.MyLog;

/**
 * Created by eltonjhony on 4/3/17.
 */
public class ErrorHandler {

    private Throwable t;

    public ErrorHandler(Throwable t) {
        this.t = t;
    }

    public Error extract() {
        Throwable cause = t.getCause();
        MyLog.error(ErrorHandler.class.getSimpleName(), cause.getMessage());
        if (cause instanceof NetworkErrorException) {
            return new Error(Error.NO_NETWORK_CODE, cause.getMessage());
        }
        return new Error(Error.GENERIC_CODE, Error.GENERIC_MESSAGE);
    }

    public class Error {

        public static final String GENERIC_MESSAGE = "Something went wrong!";

        public static final String NO_NETWORK_CODE = "74";
        public static final String GENERIC_CODE = "45";

        public String code;
        public String message;

        public Error(String code, String message) {
            this.code = code;
            this.message = message;
        }
    }

}
