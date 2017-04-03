package com.movies.android.omdbapp.movies.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.movies.android.omdbapp.R;
import com.movies.android.omdbapp.data.model.Movie;
import com.movies.android.omdbapp.databinding.MovieItemBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by eltonjhony on 3/31/17.
 */
public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    private List<Movie> mMovies;
    private OnMovieClickListener mOnMovieClickListener;

    public MoviesAdapter(List<Movie> movies, OnMovieClickListener listener) {
        setList(movies);
        this.mOnMovieClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        MovieItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.movie_item, parent, false);

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

    public void replaceData(List<Movie> movies) {
        setList(movies);
        notifyDataSetChanged();
    }

    private void setList(List<Movie> movies) {
        mMovies = movies;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private MovieItemBinding mLayout;

        public ViewHolder(MovieItemBinding binding) {
            super(binding.getRoot());
            this.mLayout = binding;
        }

        private void update(Movie movie) {
            Picasso.with(mLayout.movieThumbnail.getContext())
                    .load(movie.posterUrl)
                    .fit().centerCrop()
                    .placeholder(R.drawable.ic_insert_photo_black_48px)
                    .into(mLayout.movieThumbnail);
            mLayout.movieTitle.setText(movie.title);
        }

        private void setListeners(final Movie movie) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnMovieClickListener.onMovieClicked(movie.id);
                }
            });
        }
    }

    public interface OnMovieClickListener {
        void onMovieClicked(String id);
    }
}
