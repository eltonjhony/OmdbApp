package com.movies.android.aou.main;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.movies.android.aou.R;
import com.movies.android.aou.databinding.ActivityMainBinding;
import com.movies.android.aou.infraestructure.MyApplication;
import com.movies.android.aou.main.adapters.CurrentAdapterPosition;
import com.movies.android.aou.main.adapters.MainPageAdapter;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }
}
