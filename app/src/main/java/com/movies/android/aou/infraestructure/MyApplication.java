package com.movies.android.aou.infraestructure;

import android.app.Application;

import com.movies.android.aou.infraestructure.dagger.DaggerServiceComponent;
import com.movies.android.aou.infraestructure.dagger.ServiceComponent;
import com.movies.android.aou.infraestructure.dagger.ServiceModule;

/**
 * Created by eltonjhony on 4/3/17.
 */

public class MyApplication extends Application {

    private static MyApplication myApplication;
    private static ServiceComponent serviceComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        setupGlobalContext();
        setupDIManager();
    }

    public static ServiceComponent getServiceComponent() {
        return serviceComponent;
    }

    public static MyApplication getMyApplication() {
        return myApplication;
    }

    private void setupGlobalContext() {
        myApplication = this;
    }

    private void setupDIManager() {
        serviceComponent = DaggerServiceComponent.builder().serviceModule(new ServiceModule()).build();
    }
}
