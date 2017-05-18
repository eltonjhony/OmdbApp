package com.movies.android.aou.common;

import com.android.annotations.NonNull;

/**
 * Created by eltonjhony on 02/05/17.
 */

public interface BasePresenterContract<V> {

    void attachView(@NonNull V view);

    V getViewOrThrow();

    V getView();

    void detachView();

    boolean isViewAttached();
}
