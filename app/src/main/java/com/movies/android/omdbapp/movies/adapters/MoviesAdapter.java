package com.movies.android.omdbapp.movies.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.movies.android.omdbapp.R;
import com.movies.android.omdbapp.data.model.Movie;
import com.movies.android.omdbapp.databinding.MovieItemBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by eltonjhony on 3/31/17.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    private MovieItemBinding mBinding;
    private List<Movie> mMovies;

    public MoviesAdapter(List<Movie> movies) {
        setList(movies);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        mBinding = DataBindingUtil.inflate(inflater, R.layout.movie_item, parent, false);

        return new ViewHolder(mBinding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Movie movie = mMovies.get(position);

        Picasso.with(holder.thumbnail.getContext())
                .load(movie.posterUrl)
                .fit().centerCrop()
                .placeholder(R.drawable.ic_insert_photo_black_48px)
                .into(holder.thumbnail);

        holder.title.setText(movie.title);
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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView thumbnail;
        public TextView title;

        public ViewHolder(MovieItemBinding binding) {
            super(binding.getRoot());
            thumbnail = binding.movieThumbnail;
            title = binding.movieTitle;
        }

        @Override
        public void onClick(View v) {

        }
    }
}
