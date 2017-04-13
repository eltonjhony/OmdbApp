package com.movies.android.aou.infraestructure.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.movies.android.aou.infraestructure.MyApplication;

/**
 * Created by eltonjhony on 4/6/17.
 */
public abstract class SimplePreferences {

    public void putString(String key, String value) {
        SharedPreferences.Editor editor = provideSharedPref().edit();
        editor.putString(key, value);
        editor.apply();
    }

    public void putInt(String key, int value) {
        SharedPreferences.Editor editor = provideSharedPref().edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public void putBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = provideSharedPref().edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public String getString(String key) {
        return provideSharedPref().getString(key, null);
    }

    public int getInt(String key) {
        return provideSharedPref().getInt(key, 0);
    }

    public boolean getBoolean(String key) {
        return provideSharedPref().getBoolean(key, false);
    }

    public void clear() {
        SharedPreferences.Editor editor = provideSharedPref().edit();
        editor.clear();
        editor.apply();
    }

    protected final SharedPreferences provideSharedPref() {
        return MyApplication.getMyApplication().getSharedPreferences(providePreferencesString(), Context.MODE_PRIVATE);
    }

    protected abstract String providePreferencesString();
}
