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
import com.movies.android.aou.data.model.Movie;
import com.movies.android.aou.databinding.ContentItemBinding;

import java.util.List;

/**
 * Created by eltonjhony on 3/31/17.
 */
public class MoviesRecyclerAdapter extends RecyclerView.Adapter<MoviesRecyclerAdapter.ViewHolder> {

    private List<Movie> mResults;
    private OnContentItemClickListener mOnItemClickListener;

    public MoviesRecyclerAdapter(List<Movie> results, OnContentItemClickListener listener) {
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
        Movie movie = mResults.get(position);
        holder.update(movie);
        holder.setListeners(movie);
    }

    @Override
    public int getItemCount() {
        return this.mResults.size();
    }

    public void replaceData(List<Movie> movies) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new ContentDiffCallback(this.mResults, movies));
        this.mResults.clear();
        this.mResults.addAll(movies);
        diffResult.dispatchUpdatesTo(this);
    }

    public void appendData(List<Movie> movies) {
        if (movies == null || movies.isEmpty()) {
            return;
        }
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new ContentDiffCallback(this.mResults, movies));
        this.mResults.addAll(movies);
        diffResult.dispatchUpdatesTo(this);
    }

    public class ViewHolder extends BaseHolder<Movie> {

        private ContentItemBinding mLayout;

        public ViewHolder(ContentItemBinding binding) {
            super(binding.getRoot());
            this.mLayout = binding;
        }

        @Override
        public void update(Movie movie) {
            Glide.with(mLayout.thumbnailView.getContext())
                    .load(movie.getPosterUrl())
                    .centerCrop()
                    .placeholder(R.drawable.ic_insert_photo_black_48px)
                    .into(mLayout.thumbnailView);

            if (TextUtils.isEmpty(movie.getPosterUrl())) {
                mLayout.titleView.setTypeface(
                        Typeface.createFromAsset(mLayout.titleView.getContext().getAssets(),
                                "fonts/CaviarDreams.ttf"));
                mLayout.titleView.setText(movie.getTitle());
            } else {
                mLayout.titleView.setText(null);
            }
        }

        @Override
        public void setListeners(final Movie movie) {
            itemView.setOnClickListener(v -> {
                v.startAnimation(AnimationUtils.loadAnimation(v.getContext(), R.anim.image_click));
                int position = getAdapterPosition();
                mOnItemClickListener.onClicked(position, movie.getId());
            });
        }
    }

    public interface OnContentItemClickListener {
        void onClicked(int position, String id);
    }
}
