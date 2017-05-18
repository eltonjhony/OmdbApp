package com.movies.android.aou.browse.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import com.bumptech.glide.Glide;
import com.movies.android.aou.R;
import com.movies.android.aou.data.model.TvShows;
import com.movies.android.aou.databinding.ContentItemBinding;

import java.util.List;

/**
 * Created by eltonjhony on 3/31/17.
 */
public class TvShowsRecyclerAdapter extends RecyclerView.Adapter<TvShowsRecyclerAdapter.ViewHolder> {

    private List<TvShows> mResults;
    private OnContentItemClickListener mOnItemClickListener;

    public TvShowsRecyclerAdapter(List<TvShows> results, OnContentItemClickListener listener) {
        this.mResults = results;
        this.mOnItemClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        ContentItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.content_item, parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TvShows tvShows = mResults.get(position);
        holder.update(tvShows);
        holder.setListeners(tvShows);
    }

    @Override
    public int getItemCount() {
        return mResults.size();
    }

    public void replaceData(List<TvShows> data) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new ContentDiffCallback(this.mResults, data));
        this.mResults.clear();
        this.mResults.addAll(data);
        diffResult.dispatchUpdatesTo(this);
    }

    public void appendData(List<TvShows> data) {
        if (data == null || data.isEmpty()) {
            return;
        }
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new ContentDiffCallback(this.mResults, data));
        this.mResults.addAll(data);
        diffResult.dispatchUpdatesTo(this);
    }

    public class ViewHolder extends BaseHolder<TvShows> {

        private ContentItemBinding mLayout;

        public ViewHolder(ContentItemBinding binding) {
            super(binding.getRoot());
            this.mLayout = binding;
        }

        @Override
        public void update(TvShows tvShows) {
            Glide.with(mLayout.thumbnailView.getContext())
                    .load(tvShows.getPosterUrl())
                    .centerCrop()
                    .placeholder(R.drawable.ic_insert_photo_black_48px)
                    .into(mLayout.thumbnailView);
            if (TextUtils.isEmpty(tvShows.getPosterUrl())) {
                mLayout.titleView.setTypeface(
                        Typeface.createFromAsset(mLayout.titleView.getContext().getAssets(),
                                "fonts/CaviarDreams.ttf"));
                mLayout.titleView.setText(tvShows.getName());
            } else {
                mLayout.titleView.setText(null);
            }
        }

        @Override
        public void setListeners(final TvShows tvShows) {
            itemView.setOnClickListener(v -> {
                v.startAnimation(AnimationUtils.loadAnimation(v.getContext(), R.anim.image_click));
                mOnItemClickListener.onClicked(tvShows.getId());
            });
        }
    }

    public interface OnContentItemClickListener {
        void onClicked(String id);
    }
}
