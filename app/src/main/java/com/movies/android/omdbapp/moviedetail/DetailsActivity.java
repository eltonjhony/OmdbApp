package com.movies.android.omdbapp.moviedetail;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.movies.android.omdbapp.R;
import com.movies.android.omdbapp.data.model.ContentDetail;

public class DetailsActivity extends AppCompatActivity {

    public static final String MOVIE_EXTRA = "com.movies.android.omdbapp.MOVIE_DETAIL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ContentDetail detail = (ContentDetail) getIntent().getSerializableExtra(MOVIE_EXTRA);
        Log.i(DetailsActivity.class.getSimpleName(), detail.toString());
    }
}
