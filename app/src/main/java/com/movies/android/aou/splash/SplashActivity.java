package com.movies.android.aou.splash;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.movies.android.aou.main.MainActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onResume() {
        super.onResume();
        new Handler().postDelayed(() -> {
            startActivity(new Intent(this, MainActivity.class));
        }, 1000);
    }
}
