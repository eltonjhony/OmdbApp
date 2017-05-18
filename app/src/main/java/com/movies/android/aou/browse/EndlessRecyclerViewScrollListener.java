package com.movies.android.aou.browse;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by eltonjhony on 08/04/17.
 */
public abstract class EndlessRecyclerViewScrollListener extends RecyclerView.OnScrollListener {

    private static final int NUMBER_OF_REMAINING_ITEMS = 1;

    private int currentPage = 1;

    private RecyclerView.LayoutManager mLayoutManager;

    public EndlessRecyclerViewScrollListener(GridLayoutManager layoutManager) {
        this.mLayoutManager = layoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        int lastVisibleItemPosition = ((GridLayoutManager) mLayoutManager).findLastVisibleItemPosition();
        int totalItemCount = mLayoutManager.getItemCount();

        if (lastVisibleItemPosition == totalItemCount - NUMBER_OF_REMAINING_ITEMS) {
            currentPage++;
            onLoadMore(currentPage, totalItemCount, recyclerView);
        }
    }

    public void resetCurrentPage() {
        this.currentPage = 1;
    }

    public abstract void onLoadMore(int currentPage, int totalItemCount, RecyclerView recyclerView);
}
