package com.movies.android.aou.browse.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by eltonjhony on 17/04/17.
 */

public abstract class BaseHolder<T> extends RecyclerView.ViewHolder {

    public BaseHolder(View itemView) {
        super(itemView);
    }

    public abstract void update(T item);

    public abstract void setListeners(T item);
}
