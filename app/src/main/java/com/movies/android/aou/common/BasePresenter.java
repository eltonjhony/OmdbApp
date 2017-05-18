package com.movies.android.aou.common;

import android.support.annotation.UiThread;

import com.android.annotations.NonNull;

import java.lang.ref.WeakReference;

/**
 * Created by eltonjhony on 02/05/17.
 */
public abstract class BasePresenter<V> implements BasePresenterContract<V> {

    private WeakReference<V> mView;

    @UiThread
    @Override
    public void attachView(@NonNull V view) {
        this.mView = new WeakReference<>(view);
    }

    @UiThread
    @Override
    public V getViewOrThrow() {
        V view = this.getView();
        if (view == null) {
            throw new IllegalStateException("The view must be attached! Have you called attachView()?");
        }
        return view;
    }

    @UiThread
    @Override
    public V getView() {
        return this.mView.get();
    }

    @UiThread
    @Override
    public void detachView() {
        if (this.mView != null); {
            this.mView.clear();
            this.mView = null;
        }
    }

    @UiThread
    @Override
    public boolean isViewAttached() {
        return getView() != null;
    }
}
