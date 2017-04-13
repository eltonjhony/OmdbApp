package com.movies.android.aou.infraestructure.preferences;

/**
 * Created by eltonjhony on 13/04/17.
 */

public class SearcherPreferences extends SimplePreferences {

    @Override
    protected String providePreferencesString() {
        return "com.movies.android.aou.SEARCH_PREF";
    }
}
