package com.movies.android.omdbapp.moviedetail;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.movies.android.omdbapp.R;
import com.movies.android.omdbapp.data.model.ContentDetail;
import com.movies.android.omdbapp.infraestructure.MyLog;

import org.parceler.Parcels;

public class DetailsActivity extends AppCompatActivity {

    public static final String MOVIE_EXTRA = "com.movies.android.omdbapp.MOVIE_DETAIL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ContentDetail detail = Parcels.unwrap(getIntent().getParcelableExtra(MOVIE_EXTRA));
        MyLog.debug(DetailsActivity.class.getSimpleName(), detail.toString());
    }
}
