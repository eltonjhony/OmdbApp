package com.movies.android.aou.infraestructure.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import static com.movies.android.aou.infraestructure.Constants.PreferenceKeys.SIMPLE_PREF_KEY;

/**
 * Created by eltonjhony on 4/6/17.
 */
public abstract class SimplePreferences {

    private SharedPreferences mPreferences;

    private SimplePreferences() {
    }

    public SimplePreferences(Context context) {
        this.mPreferences = context.getSharedPreferences(SIMPLE_PREF_KEY, Context.MODE_PRIVATE);
    }

    public void saveAsync(String key, String value) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String get(String key) {
        try {
            return mPreferences.getString(key, null);
        } catch (NullPointerException e) {
            return null;
        }
    }

    public void clear() {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
