package com.movies.android.omdbapp.main;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.movies.android.omdbapp.R;
import com.movies.android.omdbapp.databinding.ActivityMainBinding;
import com.movies.android.omdbapp.infraestructure.MyApplication;
import com.movies.android.omdbapp.main.adapters.CurrentAdapterPosition;
import com.movies.android.omdbapp.main.adapters.MainPageAdapter;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mBinding;

    @Inject
    CurrentAdapterPosition currentAdapterPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.getServiceComponent().inject(this);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        mBinding.pager.setAdapter(new MainPageAdapter(this, getSupportFragmentManager()));
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBinding.pager.setCurrentItem(currentAdapterPosition.getIndex());
    }
}
