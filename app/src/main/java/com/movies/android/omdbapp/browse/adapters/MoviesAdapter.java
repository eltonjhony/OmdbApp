package com.movies.android.omdbapp.browse.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import com.movies.android.omdbapp.R;
import com.movies.android.omdbapp.data.model.Content;
import com.movies.android.omdbapp.data.model.Movie;
import com.movies.android.omdbapp.databinding.ContentItemBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by eltonjhony on 3/31/17.
 */
public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    private List<Movie> mMovies;
    private OnContentItemClickListener mOnItemClickListener;

    public MoviesAdapter(List<Movie> movies, OnContentItemClickListener listener) {
        setList(movies);
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
        Movie movie = mMovies.get(position);
        holder.update(movie);
        holder.setListeners(movie);
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    public void replaceData(List<Movie> contents) {
        setList(contents);
        notifyDataSetChanged();
    }

    public void appendData(List<Movie> contents) {
        if (this.mMovies == null) {
            replaceData(contents);
        } else {
            this.mMovies.addAll(contents);

        }
    }

    private void setList(List<Movie> movies) {
        this.mMovies = movies;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ContentItemBinding mLayout;

        public ViewHolder(ContentItemBinding binding) {
            super(binding.getRoot());
            this.mLayout = binding;
        }

        private void update(Movie movie) {
            Picasso.with(mLayout.movieThumbnail.getContext())
                    .load(movie.getPosterUrl())
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
