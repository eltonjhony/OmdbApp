package com.movies.android.aou.infraestructure.preferences;

/**
 * Created by eltonjhony on 13/04/17.
 */

public class CollapseFeaturedVideoPreferences extends SimplePreferences {

    @Override
    protected String providePreferencesString() {
        return "com.movies.android.aou.SHOW_HIDE_PREF";
    }
}
