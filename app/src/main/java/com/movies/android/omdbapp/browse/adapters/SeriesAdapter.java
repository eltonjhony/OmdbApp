package com.movies.android.omdbapp.browse.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import com.movies.android.omdbapp.R;
import com.movies.android.omdbapp.data.model.Content;
import com.movies.android.omdbapp.data.model.Serie;
import com.movies.android.omdbapp.databinding.ContentItemBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by eltonjhony on 3/31/17.
 */
public class SeriesAdapter extends RecyclerView.Adapter<SeriesAdapter.ViewHolder> {

    private List<Serie> mSeries;
    private OnContentItemClickListener mOnItemClickListener;

    public SeriesAdapter(List<Serie> series, OnContentItemClickListener listener) {
        setList(series);
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
        Serie serie = mSeries.get(position);
        holder.update(serie);
        holder.setListeners(serie);
    }

    @Override
    public int getItemCount() {
        return mSeries.size();
    }

    public void replaceData(List<Serie> contents) {
        setList(contents);
        notifyDataSetChanged();
    }

    public void appendData(List<Serie> contents) {
        if (this.mSeries == null) {
            replaceData(contents);
        } else {
            this.mSeries.addAll(contents);

        }
    }

    private void setList(List<Serie> movies) {
        mSeries = movies;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ContentItemBinding mLayout;

        public ViewHolder(ContentItemBinding binding) {
            super(binding.getRoot());
            this.mLayout = binding;
        }

        private void update(Serie serie) {
            Picasso.with(mLayout.movieThumbnail.getContext())
                    .load(serie.getPosterUrl())
                    .fit().centerCrop()
                    .placeholder(R.drawable.ic_insert_photo_black_48px)
                    .into(mLayout.movieThumbnail);
        }

        private void setListeners(final Content content) {
            itemView.setOnClickListener(v -> {
                v.startAnimation(AnimationUtils.loadAnimation(v.getContext(), R.anim.image_click));
                mOnItemClickListener.onClicked(content.getId());
            });
        }
    }

    public interface OnContentItemClickListener {
        void onClicked(String id);
    }
}
