package com.movies.android.omdbapp.infraestructure.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

/**
 * Created by eltonjhony on 4/6/17.
 */
public abstract class SimplePreferences {

    private SharedPreferences mPreferences;
    private String mKey;

    private SimplePreferences() {
    }

    public SimplePreferences(Context context, String key) {
        if (TextUtils.isEmpty(key)) {
            throw new RuntimeException("Could not create SharedPreferences without key");
        }
        this.mPreferences = context.getSharedPreferences(key, Context.MODE_PRIVATE);
        this.mKey = key;
    }

    public void saveAsync(String value) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(this.mKey, value);
        editor.apply();
    }

    public String get() {
        try {
            return mPreferences.getString(this.mKey, null);
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
