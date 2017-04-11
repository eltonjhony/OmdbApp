package com.movies.android.aou.browse.movies;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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

import com.movies.android.aou.R;
import com.movies.android.aou.browse.EndlessRecyclerViewScrollListener;
import com.movies.android.aou.browse.adapters.MoviesRecyclerAdapter;
import com.movies.android.aou.data.model.Movie;
import com.movies.android.aou.data.model.MovieDetail;
import com.movies.android.aou.data.model.ContentSegmentEnum;
import com.movies.android.aou.data.remote.API;
import com.movies.android.aou.databinding.FragmentMoviesBinding;
import com.movies.android.aou.infraestructure.MyApplication;
import com.movies.android.aou.infraestructure.MyLog;
import com.movies.android.aou.infraestructure.preferences.PagerIndexPreferences;
import com.movies.android.aou.infraestructure.preferences.SearcherPreferences;
import com.movies.android.aou.main.MainActivity;
import com.movies.android.aou.details.DetailsActivity;
import com.movies.android.aou.views.RecyclerViewWithEmptySupport;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static com.movies.android.aou.data.model.ContentSegmentEnum.NOW_PLAYING;
import static com.movies.android.aou.data.model.ContentSegmentEnum.POPULAR;
import static com.movies.android.aou.data.model.ContentSegmentEnum.TOP_RATED;
import static com.movies.android.aou.main.adapters.MainPageAdapter.MOVIES_INDEX;
import static java.lang.String.valueOf;

/**
 * Created by eltonjhony on 3/31/17.
 */
public class MoviesFragment extends Fragment implements MoviesContract.View {

    private FragmentMoviesBinding mBinding;

    private BottomNavigationView mBottomNavigation;

    private MoviesContract.Actions mActions;
    private MoviesRecyclerAdapter mAdapter;

    @Inject
    API mApi;

    @Inject
    SearcherPreferences mSearcherPreferences;

    @Inject
    PagerIndexPreferences mPagerIndexPreferences;

    private ContentSegmentEnum selectedBottomNavigationItem = POPULAR;

    public MoviesFragment() {
    }

    public static MoviesFragment newInstance() {
        MoviesFragment fragment = new MoviesFragment();
        return fragment;
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
        mActions.loadItems(null, selectedBottomNavigationItem, INITIAL_OFF_SET);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_movies, container, false);
        mBinding.movieDescText.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/BebasNeue.otf"));

        setupAdapter();
        setupBottomNavigation();
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
                mSearcherPreferences.saveAsync(query);
                mActions.loadItems(query, selectedBottomNavigationItem, INITIAL_OFF_SET);
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
    public void showMovies(List<Movie> movies) {
        mActions.retrieveFeaturedVideo(movies);
        mAdapter.replaceData(movies);
    }

    @Override
    public void showMovieDetails(MovieDetail detail) {
        mPagerIndexPreferences.saveAsync(valueOf(MOVIES_INDEX));
        Intent intent = new Intent(getContext(), DetailsActivity.class);
        intent.putExtra(DetailsActivity.MOVIE_EXTRA, Parcels.wrap(detail));
        startActivity(intent);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void appendMoreMovies(List<Movie> data) {
        mAdapter.appendData(data);
    }

    @Override
    public void setupFeaturedVideo(String videoUrl) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.youtubeplayerfragment, FeaturedVideoFragment.newInstance(videoUrl)).commit();
    }

    private void initialize() {
        mActions = new MoviesPresenter(mApi, this);
        mAdapter = new MoviesRecyclerAdapter(new ArrayList<>(0), id -> mActions.openDetails(id));
    }

    private void setupAdapter() {
        RecyclerViewWithEmptySupport rv = mBinding.movieList;
        rv.setAdapter(mAdapter);

        final int numColumns = 3;

        rv.setHasFixedSize(true);
        GridLayoutManager layout = new GridLayoutManager(getContext(), numColumns);
        rv.setLayoutManager(layout);
        rv.setEmptyView(mBinding.emptyLayout.getRoot());

        SwipeRefreshLayout refreshLayout = mBinding.refreshLayout;
        refreshLayout.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.colorPrimary),
                ContextCompat.getColor(getActivity(), R.color.colorAccent),
                ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark)
        );
        refreshLayout.setOnRefreshListener(() -> mActions.loadItems(mSearcherPreferences.get(),
                selectedBottomNavigationItem, INITIAL_OFF_SET));
        rv.addOnScrollListener(new EndlessRecyclerViewScrollListener(layout) {
            @Override
            public void onLoadMore(int page, int totalItemCount, RecyclerView recyclerView) {
                mActions.loadItems(mSearcherPreferences.get(), selectedBottomNavigationItem, page);
            };
        });
    }

    private void setupBottomNavigation() {
        mBottomNavigation = mBinding.moviesBottomNavigation;
        mBottomNavigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.action_popular:
                    MoviesFragment.this.selectedBottomNavigationItem = POPULAR;
                    mBinding.movieDescText.setText(R.string.most_popular_label);
                    mActions.loadItems(null, POPULAR, INITIAL_OFF_SET);
                    break;

                case R.id.action_now_playing:
                    selectedBottomNavigationItem = NOW_PLAYING;
                    mBinding.movieDescText.setText(R.string.now_playing_label);
                    mActions.loadItems(null, NOW_PLAYING, INITIAL_OFF_SET);
                    break;

                case R.id.action_top_rated:
                    selectedBottomNavigationItem = TOP_RATED;
                    mBinding.movieDescText.setText(R.string.top_rated_label);
                    mActions.loadItems(null, TOP_RATED, INITIAL_OFF_SET);
                    break;
            }
            return true;
        });
    }

}
