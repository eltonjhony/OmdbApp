package com.movies.android.omdbapp.browse;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by eltonjhony on 08/04/17.
 */
public abstract class EndlessRecyclerViewScrollListener extends RecyclerView.OnScrollListener {

    private int currentPage = 0;

    private RecyclerView.LayoutManager mLayoutManager;

    public EndlessRecyclerViewScrollListener(GridLayoutManager layoutManager) {
        this.mLayoutManager = layoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        int lastVisibleItemPosition = ((GridLayoutManager) mLayoutManager).findLastVisibleItemPosition();
        int totalItemCount = mLayoutManager.getItemCount();

        if (lastVisibleItemPosition == totalItemCount - 1) {
            currentPage++;
            onLoadMore(currentPage, totalItemCount, recyclerView);
        }
    }

    public abstract void onLoadMore(int currentPage, int totalItemCount, RecyclerView recyclerView);
}
