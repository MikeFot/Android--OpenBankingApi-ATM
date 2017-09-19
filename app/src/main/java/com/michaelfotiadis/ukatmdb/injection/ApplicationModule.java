package com.michaelfotiadis.ukatmdb.injection;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.michaelfotiadis.ukatmdb.network.NetworkLoader;
import com.michaelfotiadis.ukatmdb.network.client.NetworkResolver;
import com.michaelfotiadis.ukatmdb.network.client.RestClient;
import com.michaelfotiadis.ukatmdb.network.client.okhttp.CachedHttpFactory;
import com.michaelfotiadis.ukatmdb.network.client.okhttp.OkHttpFactory;
import com.michaelfotiadis.ukatmdb.loader.BankLoader;
import com.michaelfotiadis.ukatmdb.utils.ConnectivityUtils;

import java.io.File;
import java.lang.reflect.Modifier;

import dagger.Module;
import dagger.Provides;

@SuppressWarnings("MethodMayBeStatic")
@Module
public class ApplicationModule {

    private final Context mContext;
    private final boolean mIsDebugEnabled;

    public ApplicationModule(final Application application,
                             final boolean isDebugEnabled) {
        mContext = application;
        mIsDebugEnabled = isDebugEnabled;

    }

    @Provides
    File providesCacheFile() {
        return new File(mContext.getCacheDir(), "http");
    }

    @Provides
    NetworkResolver providesNetworkResolver() {
        return new NetworkResolver() {
            @Override
            public boolean isConnected() {
                return ConnectivityUtils.isConnected(mContext);
            }
        };
    }

    @Provides
    OkHttpFactory providesOkHttpFactory(final File cacheFile, final NetworkResolver networkResolver) {

        return new CachedHttpFactory(cacheFile, networkResolver, mIsDebugEnabled);

    }

    @Provides
    RestClient providesRestClient(final Gson gson, final OkHttpFactory okHttpFactory) {
        return new RestClient(gson, okHttpFactory);
    }

    @Provides
    NetworkLoader providesNetworkLoader(final Gson gson, final OkHttpFactory okHttpFactory) {
        return new NetworkLoader(gson, okHttpFactory);
    }

    @Provides
    Gson providesGson() {
        return new GsonBuilder().setPrettyPrinting()
                .serializeNulls()
                .enableComplexMapKeySerialization()
                .excludeFieldsWithModifiers(Modifier.STATIC, Modifier.TRANSIENT, Modifier.VOLATILE)
                .create();
    }

    @Provides
    BankLoader providesBankLoader() {
        return new BankLoader(mContext.getResources());
    }


}
