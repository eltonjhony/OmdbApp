package com.movies.android.aou.browse.adapters;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import com.movies.android.aou.data.model.Content;

import java.util.List;

/**
 * Created by eltonjhony on 17/04/17.
 */

public class ContentDiffCallback extends DiffUtil.Callback {

    private List<? extends Content> mOldMovies;
    private List<? extends Content> mNewMovies;

    public ContentDiffCallback(List<? extends Content> mOldMovies, List<? extends Content> mNewMovies) {
        this.mOldMovies = mOldMovies;
        this.mNewMovies = mNewMovies;
    }

    @Override
    public int getOldListSize() {
        return mOldMovies != null ? mOldMovies.size() : 0;
    }

    @Override
    public int getNewListSize() {
        return mNewMovies != null ? mNewMovies.size() : 0;
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return mNewMovies.get(newItemPosition).getId() == mOldMovies.get(oldItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return mNewMovies.get(newItemPosition).equals(mOldMovies.get(oldItemPosition));
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
