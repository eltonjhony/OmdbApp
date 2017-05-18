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
import com.movies.android.aou.databinding.FragmentMoviesBinding;
import com.movies.android.aou.infraestructure.MyApplication;
import com.movies.android.aou.infraestructure.MyLog;
import com.movies.android.aou.infraestructure.preferences.CollapseFeaturedVideoPreferences;
import com.movies.android.aou.infraestructure.preferences.MainPagerPreferences;
import com.movies.android.aou.infraestructure.preferences.SearcherPreferences;
import com.movies.android.aou.main.MainActivity;
import com.movies.android.aou.details.DetailsActivity;
import com.movies.android.aou.views.AnimatingFrameLayout;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static com.movies.android.aou.data.model.ContentSegmentEnum.NOW_PLAYING;
import static com.movies.android.aou.data.model.ContentSegmentEnum.POPULAR;
import static com.movies.android.aou.data.model.ContentSegmentEnum.TOP_RATED;
import static com.movies.android.aou.infraestructure.Constants.PreferenceKeys.PAGER_KEY;
import static com.movies.android.aou.infraestructure.Constants.PreferenceKeys.SEARCHER_KEY;
import static com.movies.android.aou.infraestructure.Constants.PreferenceKeys.SHOW_HIDE_KEY;
import static com.movies.android.aou.main.adapters.MainPageAdapter.MOVIES_INDEX;

/**
 * Created by eltonjhony on 3/31/17.
 */
public class MoviesFragment extends Fragment implements MoviesContract.View {

    private FragmentMoviesBinding mBinding;

    private BottomNavigationView mBottomNavigation;
    private MoviesRecyclerAdapter mAdapter;

    @Inject
    MoviesPresenter mPresenter;

    @Inject
    CollapseFeaturedVideoPreferences mCollapseFeaturedVideoPref;

    @Inject
    SearcherPreferences mSearcherPref;

    @Inject
    MainPagerPreferences mMainPagerPref;

    private ContentSegmentEnum selectedBottomNavigationItem = POPULAR;

    private EndlessRecyclerViewScrollListener mScrollListener;

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
        setRetainInstance(true);
        MyApplication.getServiceComponent().inject(this);
        initialize();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_movies, container, false);
        mBinding.movieDescText.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/BebasNeue.otf"));

        setupAdapter();
        setupBottomNavigation();
        setListeners();

        return mBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (this.mCollapseFeaturedVideoPref.getBoolean(SHOW_HIDE_KEY)) {
            mBinding.youtubeplayerfragment.show();
        } else {
            mBinding.youtubeplayerfragment.hide();
        }
        mPresenter.loadItems(null, selectedBottomNavigationItem, INITIAL_OFF_SET);
    }

    @Override
    public void onDestroy() {
        this.mPresenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.search:
                SearchView searchView = new SearchView(((MainActivity) getContext()).getSupportActionBar().getThemedContext());
                MenuItemCompat.setActionView(item, searchView);
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        mSearcherPref.putString(SEARCHER_KEY, query);
                        mPresenter.loadItems(query, selectedBottomNavigationItem, INITIAL_OFF_SET);
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
                                mSearcherPref.clear();
                        }
                        return true;
                    }
                });

            case R.id.list_type:
                Toast.makeText(getContext(), "teste", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
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
        this.mScrollListener.resetCurrentPage();
        this.mAdapter.replaceData(movies);
    }

    @Override
    public void showMovieDetails(MovieDetail detail) {
        mMainPagerPref.putInt(PAGER_KEY, MOVIES_INDEX);
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
        this.mAdapter.appendData(data);
    }

    @Override
    public void setupFeaturedVideo(String videoUrl) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.youtubeplayerfragment, FeaturedVideoFragment.newInstance(videoUrl)).commit();
    }

    private void initialize() {
        mPresenter.attachView(this);
        mAdapter = new MoviesRecyclerAdapter(new ArrayList<>(0), (position, id) -> {
            mPresenter.openDetails(id);
        });
    }

    private void setListeners() {
        mBinding.hideShowBtn.setOnClickListener(v -> {
            AnimatingFrameLayout youtubeFrameLayout = mBinding.youtubeplayerfragment;
            if (youtubeFrameLayout.isVisible()) {
                youtubeFrameLayout.hide();
                this.mCollapseFeaturedVideoPref.putBoolean(SHOW_HIDE_KEY, false);
            } else {
                youtubeFrameLayout.show();
                this.mCollapseFeaturedVideoPref.putBoolean(SHOW_HIDE_KEY, true);
            }
        });
    }

    private void setupAdapter() {
        mBinding.movieList.setAdapter(mAdapter);

        final int numColumns = 3;

        mBinding.movieList.setHasFixedSize(true);

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), numColumns);

        mBinding.movieList.setLayoutManager(layoutManager);
        mBinding.movieList.setEmptyView(mBinding.emptyLayout.getRoot());

        SwipeRefreshLayout refreshLayout = mBinding.refreshLayout;
        refreshLayout.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.colorPrimary),
                ContextCompat.getColor(getActivity(), R.color.colorAccent),
                ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark)
        );

        mScrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemCount, RecyclerView recyclerView) {
                mPresenter.loadItems(mSearcherPref.getString(SEARCHER_KEY), selectedBottomNavigationItem, page);
            };
        };

        mBinding.movieList.addOnScrollListener(mScrollListener);

        refreshLayout.setOnRefreshListener(() -> {
            mPresenter.loadItems(mSearcherPref.getString(SEARCHER_KEY),
                    selectedBottomNavigationItem, INITIAL_OFF_SET);
        });
    }

    private void setupBottomNavigation() {
        mBottomNavigation = mBinding.moviesBottomNavigation;
        mBottomNavigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.action_popular:
                    MoviesFragment.this.selectedBottomNavigationItem = POPULAR;
                    mBinding.movieDescText.setText(R.string.most_popular_label);
                    mPresenter.loadItems(null, POPULAR, INITIAL_OFF_SET);
                    break;

                case R.id.action_now_playing:
                    selectedBottomNavigationItem = NOW_PLAYING;
                    mBinding.movieDescText.setText(R.string.now_playing_label);
                    mPresenter.loadItems(null, NOW_PLAYING, INITIAL_OFF_SET);
                    break;

                case R.id.action_top_rated:
                    selectedBottomNavigationItem = TOP_RATED;
                    mBinding.movieDescText.setText(R.string.top_rated_label);
                    mPresenter.loadItems(null, TOP_RATED, INITIAL_OFF_SET);
                    break;
            }
            return true;
        });
    }

}
