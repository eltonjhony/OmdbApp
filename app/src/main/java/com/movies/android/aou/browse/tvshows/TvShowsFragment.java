package com.movies.android.aou.browse.tvshows;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
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

import com.movies.android.aou.R;
import com.movies.android.aou.browse.EndlessRecyclerViewScrollListener;
import com.movies.android.aou.browse.adapters.TvShowsRecyclerAdapter;
import com.movies.android.aou.data.model.ContentSegmentEnum;
import com.movies.android.aou.data.model.TvShows;
import com.movies.android.aou.data.model.TvShowsDetail;
import com.movies.android.aou.databinding.FragmentTvShowsBinding;
import com.movies.android.aou.infraestructure.MyApplication;
import com.movies.android.aou.infraestructure.MyLog;
import com.movies.android.aou.infraestructure.preferences.SimplePreferences;
import com.movies.android.aou.main.MainActivity;
import com.movies.android.aou.details.DetailsActivity;
import com.movies.android.aou.views.RecyclerViewWithEmptySupport;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static com.movies.android.aou.data.model.ContentSegmentEnum.ON_THE_AIR;
import static com.movies.android.aou.data.model.ContentSegmentEnum.POPULAR;
import static com.movies.android.aou.data.model.ContentSegmentEnum.TOP_RATED;
import static com.movies.android.aou.infraestructure.Constants.PreferenceKeys.PAGER_KEY;
import static com.movies.android.aou.infraestructure.Constants.PreferenceKeys.SEARCHER_KEY;
import static com.movies.android.aou.main.adapters.MainPageAdapter.TV_SHOWS_INDEX;
import static java.lang.String.valueOf;

/**
 * Created by eltonjhony on 3/31/17.
 */
public class TvShowsFragment extends Fragment implements TvShowsContract.View {

    private FragmentTvShowsBinding mBinding;

    private BottomNavigationView mBottomNavigationMenu;
    private TvShowsRecyclerAdapter mAdapter;

    @Inject
    TvShowsPresenter mPresenter;

    @Inject
    SimplePreferences mSimplePreferences;

    private ContentSegmentEnum selectedBottomNavigationItem = POPULAR;

    public TvShowsFragment() {
    }

    public static TvShowsFragment newInstance() {
        return new TvShowsFragment();
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
        mPresenter.loadItems(mSimplePreferences.get(SEARCHER_KEY), selectedBottomNavigationItem, INITIAL_OFF_SET);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_tv_shows, container, false);
        setupAdapter();
        setupBottomNavigation();
        return mBinding.getRoot();
    }

    @Override
    public void onDestroy() {
        this.mPresenter.onDestroy();
        super.onDestroy();
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
                mSimplePreferences.saveAsync(SEARCHER_KEY, query);
                mPresenter.loadItems(query, selectedBottomNavigationItem, INITIAL_OFF_SET);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                MyLog.info(TvShowsFragment.class.getSimpleName(), newText);
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
                        mSimplePreferences.clear();
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
    public void displayTvShows(List<TvShows> tvShows) {
        mAdapter.replaceData(tvShows);
    }

    @Override
    public void displayTvShowsDetails(TvShowsDetail detail) {
        mSimplePreferences.saveAsync(PAGER_KEY, valueOf(TV_SHOWS_INDEX));
        Intent intent = new Intent(getContext(), DetailsActivity.class);
        intent.putExtra(DetailsActivity.TV_SHOW_EXTRA, Parcels.wrap(detail));
        startActivity(intent);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void displayMoreTvShows(List<TvShows> data) {
        mAdapter.appendData(data);
    }

    private void initialize() {
        mPresenter.setView(this);
        mAdapter = new TvShowsRecyclerAdapter(new ArrayList<>(0), id -> mPresenter.openDetails(id));
    }

    private void setupAdapter() {
        RecyclerViewWithEmptySupport rv = mBinding.seriesList;
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
        refreshLayout.setOnRefreshListener(() -> {
            mPresenter.loadItems(mSimplePreferences.get(SEARCHER_KEY), selectedBottomNavigationItem, INITIAL_OFF_SET);
        });
        rv.addOnScrollListener(new EndlessRecyclerViewScrollListener(layout) {
            @Override
            public void onLoadMore(int page, int totalItemCount, RecyclerView recyclerView) {
                mPresenter.loadItems(mSimplePreferences.get(SEARCHER_KEY), selectedBottomNavigationItem, page);
            }
        });
    }

    private void setupBottomNavigation() {
        mBottomNavigationMenu = mBinding.tvShowsBottomNavigation;
        mBottomNavigationMenu.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.action_popular:
                    TvShowsFragment.this.selectedBottomNavigationItem = POPULAR;
                    mPresenter.loadItems(null, POPULAR, INITIAL_OFF_SET);
                    break;

                case R.id.action_on_the_air:
                    TvShowsFragment.this.selectedBottomNavigationItem = ON_THE_AIR;
                    mPresenter.loadItems(null, ON_THE_AIR, INITIAL_OFF_SET);
                    break;

                case R.id.action_top_rated:
                    TvShowsFragment.this.selectedBottomNavigationItem = TOP_RATED;
                    mPresenter.loadItems(null, TOP_RATED, INITIAL_OFF_SET);
                    break;
            }
            return true;
        });
    }
}
