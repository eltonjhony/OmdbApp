package com.movies.android.omdbapp.movies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.movies.android.omdbapp.R;
import com.movies.android.omdbapp.utils.ActivityUtils;

public class MoviesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        if (savedInstanceState == null) {
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    MoviesFragment.newInstance(),
                    R.id.content);
        }
    }
}
