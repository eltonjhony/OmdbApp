package com.movies.android.omdbapp.browse.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import com.movies.android.omdbapp.R;
import com.movies.android.omdbapp.data.model.TvShows;
import com.movies.android.omdbapp.databinding.ContentItemBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by eltonjhony on 3/31/17.
 */
public class TvShowsRecyclerAdapter extends RecyclerView.Adapter<TvShowsRecyclerAdapter.ViewHolder> {

    private List<TvShows> mResults;
    private OnContentItemClickListener mOnItemClickListener;

    public TvShowsRecyclerAdapter(List<TvShows> results, OnContentItemClickListener listener) {
        setList(results);
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
        setList(data);
        notifyDataSetChanged();
    }

    public void appendData(List<TvShows> data) {
        if (data != null && !data.isEmpty()) {
            int currentSize = this.mResults.size();
            this.mResults.addAll(data);
            notifyItemRangeInserted(currentSize, this.mResults.size() - 1);
            notifyDataSetChanged();
        } else if (this.mResults == null) {
            replaceData(data);
        }

    }

    private void setList(List<TvShows> tvShowses) {
        this.mResults = tvShowses;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ContentItemBinding mLayout;

        public ViewHolder(ContentItemBinding binding) {
            super(binding.getRoot());
            this.mLayout = binding;
        }

        private void update(TvShows tvShows) {
            Picasso.with(mLayout.thumbnailView.getContext())
                    .load(tvShows.getPosterUrl())
                    .fit().centerCrop()
                    .placeholder(R.drawable.ic_insert_photo_black_48px)
                    .into(mLayout.thumbnailView);
            if (TextUtils.isEmpty(tvShows.getPosterUrl())) {
                mLayout.titleView.setTypeface(
                        Typeface.createFromAsset(mLayout.titleView.getContext().getAssets(),
                                "fonts/CaviarDreams.ttf"));
                mLayout.titleView.setText(tvShows.getName());
            }
        }

        private void setListeners(final TvShows tvShows) {
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
