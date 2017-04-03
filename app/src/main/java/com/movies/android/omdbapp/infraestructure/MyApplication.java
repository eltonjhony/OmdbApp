package com.movies.android.omdbapp.infraestructure;

import android.app.Application;

import com.movies.android.omdbapp.infraestructure.dagger.DaggerServiceComponent;
import com.movies.android.omdbapp.infraestructure.dagger.ServiceComponent;
import com.movies.android.omdbapp.infraestructure.dagger.ServiceModule;

/**
 * Created by eltonjhony on 4/3/17.
 */

public class MyApplication extends Application {

    private static ServiceComponent serviceComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        setupDIManager();
    }

    public static ServiceComponent getServiceComponent() {
        return serviceComponent;
    }

    private void setupDIManager() {
        serviceComponent = DaggerServiceComponent.builder().serviceModule(new ServiceModule()).build();
    }
}
