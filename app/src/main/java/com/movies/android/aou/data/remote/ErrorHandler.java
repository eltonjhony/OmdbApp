package com.movies.android.aou.data.remote;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.movies.android.aou.infraestructure.MyLog;

import java.io.IOException;
import java.io.Serializable;

import okhttp3.ResponseBody;
import retrofit2.adapter.rxjava.HttpException;

import static com.movies.android.aou.data.remote.ErrorHandler.Error.GENERIC_CODE;
import static com.movies.android.aou.data.remote.ErrorHandler.Error.GENERIC_MESSAGE;

/**
 * Created by eltonjhony on 4/3/17.
 */
public class ErrorHandler {

    private Throwable t;

    public ErrorHandler(Throwable t) {
        this.t = t;
    }

    public Error extract() {
        MyLog.error(ErrorHandler.class.getSimpleName(), t.getMessage());
        if (t instanceof HttpException) {
            ResponseBody body = ((HttpException) t).response().errorBody();
            try {
                Gson gson = new Gson();
                Error error = gson.fromJson(body.string(), Error.class);
                MyLog.error(error.code, error.message);
                return new Error(error.code, error.message);
            } catch (IOException e) {
                return new Error(GENERIC_CODE, GENERIC_MESSAGE);
            }
        }
        return new Error(GENERIC_CODE, GENERIC_MESSAGE);
    }

    public class Error implements Serializable {

        public static final String GENERIC_MESSAGE = "Something went wrong!";
        public static final int GENERIC_CODE = 1;

        @SerializedName("status_code")
        public int code;

        @SerializedName("status_message")
        public String message;

        public Error() {
        }

        public Error(int code, String message) {
            this.code = code;
            this.message = message;
        }
    }

}
