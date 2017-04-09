package com.movies.android.aou.main.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.movies.android.aou.R;
import com.movies.android.aou.browse.movies.MoviesFragment;
import com.movies.android.aou.browse.tvshows.TvShowsFragment;

/**
 * Created by eltonjhony on 4/4/17.
 */
public class MainPageAdapter extends FragmentPagerAdapter {

    public static final int MOVIES_INDEX = 0;
    public static final int TV_SHOWS_INDEX = 1;
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
            case TV_SHOWS_INDEX:
                return TvShowsFragment.newInstance();
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
            case TV_SHOWS_INDEX:
                return mContext.getString(R.string.tv_shows_label);
        }
        return super.getPageTitle(position);
    }

}
