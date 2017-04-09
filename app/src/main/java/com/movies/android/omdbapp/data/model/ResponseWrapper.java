package com.movies.android.omdbapp.data.model;

import java.io.Serializable;

/**
 * Created by eltonjhony on 08/04/17.
 */

public class ResponseWrapper implements Serializable {

    private int httpCode;
    private String httpMessage;

    public int getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(int httpCode) {
        this.httpCode = httpCode;
    }

    public String getHttpMessage() {
        return httpMessage;
    }

    public void setHttpMessage(String httpMessage) {
        this.httpMessage = httpMessage;
    }
}
