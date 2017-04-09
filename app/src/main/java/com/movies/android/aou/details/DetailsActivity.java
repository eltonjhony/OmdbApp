package com.movies.android.aou.details;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.movies.android.aou.R;
import com.movies.android.aou.data.model.MovieDetail;
import com.movies.android.aou.data.model.TvShowsDetail;
import com.movies.android.aou.infraestructure.MyApplication;
import com.movies.android.aou.infraestructure.MyLog;
import org.parceler.Parcels;

public class DetailsActivity extends AppCompatActivity {

    public static final String MOVIE_EXTRA = "com.movies.android.omdbapp.MOVIE_DETAIL";
    public static final String TV_SHOW_EXTRA = "com.movies.android.omdbapp.TV_SHOW_DETAIL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        MyApplication.getServiceComponent().inject(this);

        MovieDetail movieDetail = Parcels.unwrap(getIntent().getParcelableExtra(MOVIE_EXTRA));
        TvShowsDetail tvShowsDetail = Parcels.unwrap(getIntent().getParcelableExtra(TV_SHOW_EXTRA));

        if (movieDetail != null) {
            MyLog.debug(DetailsActivity.class.getSimpleName(), movieDetail.toString());
        }

        if (tvShowsDetail != null) {
            MyLog.debug(DetailsActivity.class.getSimpleName(), tvShowsDetail.toString());
        }

    }
}
