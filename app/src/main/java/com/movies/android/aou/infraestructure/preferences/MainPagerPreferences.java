package com.movies.android.aou.infraestructure.preferences;

/**
 * Created by eltonjhony on 13/04/17.
 */

public class MainPagerPreferences extends SimplePreferences {

    @Override
    protected String providePreferencesString() {
        return "com.movies.android.aou.PAGER_PREF";
    }
}
