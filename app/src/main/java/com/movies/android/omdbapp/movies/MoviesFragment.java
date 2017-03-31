package com.movies.android.omdbapp.movies;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.movies.android.omdbapp.R;
import com.movies.android.omdbapp.data.MovieServiceImpl;
import com.movies.android.omdbapp.data.model.Movie;
import com.movies.android.omdbapp.databinding.FragmentMoviesBinding;
import com.movies.android.omdbapp.movies.adapters.MoviesAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eltonjhony on 3/31/17.
 */

public class MoviesFragment extends Fragment implements MoviesContract.View {

    private FragmentMoviesBinding mBinding;
    private MoviesContract.Actions mActions;
    private MoviesAdapter mAdapter;

    public MoviesFragment() {
    }

    public static MoviesFragment newInstance() {
        return new MoviesFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new MoviesAdapter(new ArrayList<Movie>(0));
        mActions = new MoviesPresenter(new MovieServiceImpl(), this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mActions.loadMovies();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_movies, container, false);
        setupRecyclerView();
        return mBinding.getRoot();
    }

    @Override
    public void setLoading(final boolean isActive) {
        if (getView() == null) {
            return;
        }
        final SwipeRefreshLayout srl = mBinding.refreshLayout;
        srl.post(new Runnable() {
            @Override
            public void run() {
                srl.setRefreshing(isActive);
            }
        });
    }

    @Override
    public void showMovies(List<Movie> movies) {
        mAdapter.replaceData(movies);
    }

    @Override
    public void showMovieDetails(String movieId) {

    }

    private void setupRecyclerView() {
        RecyclerView rv = mBinding.movieList;
        rv.setAdapter(mAdapter);

        int numColumns = 1;

        rv.setHasFixedSize(true);
        rv.setLayoutManager(new GridLayoutManager(getContext(), numColumns));

        SwipeRefreshLayout refreshLayout = mBinding.refreshLayout;
        refreshLayout.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.colorPrimary),
                ContextCompat.getColor(getActivity(), R.color.colorAccent),
                ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark)
        );
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mActions.loadMovies();
            }
        });
    }
}
