package com.movies.android.omdbapp.browse.movies;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.movies.android.omdbapp.R;
import com.movies.android.omdbapp.data.model.Content;
import com.movies.android.omdbapp.data.model.ContentDetail;
import com.movies.android.omdbapp.data.remote.OmdbApi;
import com.movies.android.omdbapp.databinding.FragmentMoviesBinding;
import com.movies.android.omdbapp.infraestructure.MyApplication;
import com.movies.android.omdbapp.infraestructure.MyLog;
import com.movies.android.omdbapp.infraestructure.preferences.SearcherPreferences;
import com.movies.android.omdbapp.main.MainActivity;
import com.movies.android.omdbapp.moviedetail.DetailsActivity;
import com.movies.android.omdbapp.browse.adapters.ContentBrowseAdapter;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by eltonjhony on 3/31/17.
 */
public class MoviesFragment extends Fragment implements MoviesContract.View {

    private FragmentMoviesBinding mBinding;
    private MoviesContract.Actions mActions;
    private ContentBrowseAdapter mAdapter;

    @Inject
    OmdbApi mApi;

    @Inject
    SearcherPreferences mSearcherPreferences;

    public MoviesFragment() {
    }

    public static MoviesFragment newInstance() {
        return new MoviesFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        MyApplication.getServiceComponent().inject(this);
        initialize();
    }

    @Override
    public void onResume() {
        super.onResume();
        mActions.loadItems(null);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_movies, container, false);
        setupAdapter();
        return mBinding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();

        inflater.inflate(R.menu.search_menu, menu);
        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = new SearchView(((MainActivity) getContext()).getSupportActionBar().getThemedContext());
        MenuItemCompat.setActionView(item, searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mSearcherPreferences.recordQuery(query);
                mActions.loadItems(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                MyLog.info(MoviesFragment.class.getSimpleName(), newText);
                return true;
            }
        });
        MenuItemCompat.setOnActionExpandListener(item, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.search:
                        mSearcherPreferences.clear();
                }
                return true;
            }
        });
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
    public void showMovies(List<Content> movies) {
        mAdapter.replaceData(movies);
    }

    @Override
    public void showMovieDetails(ContentDetail detail) {
        Intent intent = new Intent(getContext(), DetailsActivity.class);
        intent.putExtra(DetailsActivity.MOVIE_EXTRA, Parcels.wrap(detail));
        startActivity(intent);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    private void initialize() {
        mActions = new MoviesPresenter(mApi, this);
        mAdapter = new ContentBrowseAdapter(new ArrayList<>(0), id -> mActions.openDetails(id));
    }

    private void setupAdapter() {
        RecyclerView rv = mBinding.movieList;
        rv.setAdapter(mAdapter);

        int numColumns = 3;

        rv.setHasFixedSize(true);
        rv.setLayoutManager(new GridLayoutManager(getContext(), numColumns));

        SwipeRefreshLayout refreshLayout = mBinding.refreshLayout;
        refreshLayout.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.colorPrimary),
                ContextCompat.getColor(getActivity(), R.color.colorAccent),
                ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark)
        );
        refreshLayout.setOnRefreshListener(() -> mActions.loadItems(mSearcherPreferences.getRecordedQuery()));
    }
}
