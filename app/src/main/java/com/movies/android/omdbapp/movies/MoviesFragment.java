package com.movies.android.omdbapp.movies;

import android.content.Intent;
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
import android.widget.Toast;

import com.movies.android.omdbapp.R;
import com.movies.android.omdbapp.data.model.Movie;
import com.movies.android.omdbapp.data.model.MovieDetail;
import com.movies.android.omdbapp.data.remote.MovieApi;
import com.movies.android.omdbapp.databinding.FragmentMoviesBinding;
import com.movies.android.omdbapp.infraestructure.MyApplication;
import com.movies.android.omdbapp.moviedetail.DetailsActivity;
import com.movies.android.omdbapp.movies.adapters.MoviesAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by eltonjhony on 3/31/17.
 */
public class MoviesFragment extends Fragment implements MoviesContract.View {

    private FragmentMoviesBinding mBinding;
    private MoviesContract.Actions mActions;
    private MoviesAdapter mAdapter;

    @Inject MovieApi mApi;

    public MoviesFragment() {
    }

    public static MoviesFragment newInstance() {
        return new MoviesFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.getServiceComponent().inject(this);
        mActions = new MoviesPresenter(mApi, this);
        mAdapter = new MoviesAdapter(new ArrayList<Movie>(0), id -> mActions.openDetails(id));
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
        setupAdapter();
        return mBinding.getRoot();
    }

    @Override
    public void setLoading(final boolean isActive) {
        if (getView() == null) {
            return;
        }
        final SwipeRefreshLayout srl = mBinding.refreshLayout;
        srl.post(() -> srl.setRefreshing(isActive));
    }

    @Override
    public void showMovies(List<Movie> movies) {
        mAdapter.replaceData(movies);
    }

    @Override
    public void showMovieDetails(MovieDetail detail) {
        Intent intent = new Intent(getContext(), DetailsActivity.class);
        intent.putExtra(DetailsActivity.MOVIE_EXTRA, detail);
        startActivity(intent);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    private void setupAdapter() {
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
        refreshLayout.setOnRefreshListener(() -> mActions.loadMovies());
    }
}
