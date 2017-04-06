package com.movies.android.omdbapp.main;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.movies.android.omdbapp.R;
import com.movies.android.omdbapp.databinding.ActivityMainBinding;
import com.movies.android.omdbapp.main.adapters.MainPageAdapter;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        mBinding.pager.setAdapter(new MainPageAdapter(this, getSupportFragmentManager()));
    }

}
