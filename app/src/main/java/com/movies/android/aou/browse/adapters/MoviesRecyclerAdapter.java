package com.movies.android.aou.browse.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import com.movies.android.aou.R;
import com.movies.android.aou.data.model.Movie;
import com.movies.android.aou.databinding.ContentItemBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by eltonjhony on 3/31/17.
 */
public class MoviesRecyclerAdapter extends RecyclerView.Adapter<MoviesRecyclerAdapter.ViewHolder> {

    private List<Movie> mResults;
    private OnContentItemClickListener mOnItemClickListener;

    public MoviesRecyclerAdapter(List<Movie> results, OnContentItemClickListener listener) {
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
        Movie movie = mResults.get(position);
        holder.update(movie);
        holder.setListeners(movie);
    }

    @Override
    public int getItemCount() {
        return mResults.size();
    }

    public void replaceData(List<Movie> data) {
        setList(data);
        notifyDataSetChanged();
    }

    public void appendData(List<Movie> data) {
        if (data != null && !data.isEmpty()) {
            int currentSize = this.mResults.size();
            this.mResults.addAll(data);
            notifyItemRangeInserted(currentSize, this.mResults.size() - 1);
            notifyDataSetChanged();
        } else if (this.mResults == null) {
            replaceData(data);
        }

    }

    private void setList(List<Movie> contents) {
        this.mResults = contents;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ContentItemBinding mLayout;

        public ViewHolder(ContentItemBinding binding) {
            super(binding.getRoot());
            this.mLayout = binding;
        }

        private void update(Movie movie) {
            Picasso.with(mLayout.thumbnailView.getContext())
                    .load(movie.getPosterUrl())
                    .fit().centerCrop()
                    .placeholder(R.drawable.ic_insert_photo_black_48px)
                    .into(mLayout.thumbnailView);
            if (TextUtils.isEmpty(movie.getPosterUrl())) {
                mLayout.titleView.setTypeface(
                        Typeface.createFromAsset(mLayout.titleView.getContext().getAssets(),
                                "fonts/CaviarDreams.ttf"));
                mLayout.titleView.setText(movie.getTitle());
            }
        }

        private void setListeners(final Movie movie) {
            itemView.setOnClickListener(v -> {
                v.startAnimation(AnimationUtils.loadAnimation(v.getContext(), R.anim.image_click));
                mOnItemClickListener.onClicked(movie.getId());
            });
        }
    }

    public interface OnContentItemClickListener {
        void onClicked(String id);
    }
}
