package com.movies.android.omdbapp.infraestructure.preferences;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by eltonjhony on 4/6/17.
 */
public class SearcherPreferences {

    private static final String SEARCHER_PREF_KEY = "searcher_pref";
    private static final String SEARCH_TEXT_KEY = "SEARCH_TEXT";

    private Context mContext;

    public SearcherPreferences(Context context) {
        mContext = context;
    }

    public void recordQuery(String query) {
        SharedPreferences preferences = mContext.getSharedPreferences(SEARCHER_PREF_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(SEARCH_TEXT_KEY, query);
        editor.apply();
    }

    public String getRecordedQuery() {
        SharedPreferences preferences = mContext.getSharedPreferences(SEARCHER_PREF_KEY, Context.MODE_PRIVATE);
        String query = preferences.getString(SEARCH_TEXT_KEY, null);
        return query;
    }

    public void clear() {
        SharedPreferences preferences = mContext.getSharedPreferences(SEARCHER_PREF_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }
}
