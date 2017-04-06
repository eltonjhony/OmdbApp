package com.movies.android.omdbapp.infraestructure.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.movies.android.omdbapp.infraestructure.Constants;

/**
 * Created by eltonjhony on 4/6/17.
 */
public class SearcherPreferences {

    private SharedPreferences mPreferences;

    public SearcherPreferences(Context context) {
        mPreferences = context.getSharedPreferences(Constants.PreferenceKeys.SEARCHER, Context.MODE_PRIVATE);
    }

    public void recordQuery(String query) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(Constants.PreferenceKeys.SEARCH_TEXT, query);
        editor.apply();
    }

    public String getRecordedQuery() {
        String query = mPreferences.getString(Constants.PreferenceKeys.SEARCH_TEXT, null);
        return query;
    }

    public void clear() {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
