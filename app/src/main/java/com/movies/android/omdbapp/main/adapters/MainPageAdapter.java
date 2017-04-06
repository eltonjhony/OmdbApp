package com.movies.android.omdbapp.main.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.movies.android.omdbapp.R;
import com.movies.android.omdbapp.browse.movies.MoviesFragment;
import com.movies.android.omdbapp.browse.series.SeriesFragment;

/**
 * Created by eltonjhony on 4/4/17.
 */
public class MainPageAdapter extends FragmentPagerAdapter {

    private static final int MOVIES_INDEX = 0;
    private static final int SERIES_INDEX = 1;
    private static final int TOTAL_PAGES = 2;

    private Context mContext;

    public MainPageAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case MOVIES_INDEX:
                return MoviesFragment.newInstance();
            case SERIES_INDEX:
                return SeriesFragment.newInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return TOTAL_PAGES;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case MOVIES_INDEX:
                return mContext.getString(R.string.movies_label);
            case SERIES_INDEX:
                return mContext.getString(R.string.series_label);
        }
        return super.getPageTitle(position);
    }
}
