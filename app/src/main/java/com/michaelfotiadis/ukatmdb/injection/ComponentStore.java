package com.michaelfotiadis.ukatmdb.injection;

import android.app.Application;

public class ComponentStore {


    private final AndroidAwareComponent mAndroidAwareComponent;

    public ComponentStore(final Application context,
                          final boolean isDebugEnabled) {

        mAndroidAwareComponent = DaggerAndroidAwareComponent.builder()
                .applicationModule(new ApplicationModule(context, isDebugEnabled))
                .build();


    }

    public AndroidAwareComponent getAndroidAwareComponent() {
        return mAndroidAwareComponent;
    }
}
