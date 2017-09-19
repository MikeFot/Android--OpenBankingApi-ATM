package com.michaelfotiadis.ukatmdb;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

import com.crashlytics.android.Crashlytics;
import com.michaelfotiadis.ukatmdb.injection.ComponentStore;
import com.michaelfotiadis.ukatmdb.injection.Injector;
import com.michaelfotiadis.ukatmdb.utils.AppLog;

import io.fabric.sdk.android.Fabric;

public class AtmApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        AppLog.d("Starting application");
        Fabric.with(this, new Crashlytics());

        Injector.setComponentStore(new ComponentStore(this, BuildConfig.DEBUG));

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

    }
}
