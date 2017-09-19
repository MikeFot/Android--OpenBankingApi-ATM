package com.michaelfotiadis.ukatmdb.network.client;


import com.google.gson.Gson;
import com.michaelfotiadis.ukatmdb.network.api.AtmApi;
import com.michaelfotiadis.ukatmdb.network.client.okhttp.OkHttpFactory;

import retrofit2.Retrofit;

public class RestClient {

    private final Retrofit2Factory mRetrofit2Factory;

    public RestClient(final Gson gson,
                      final OkHttpFactory factory) {

        mRetrofit2Factory = new Retrofit2Factory(gson, factory);

    }

    public synchronized <T> T createApi(final Class<T> clazz, final String baseUrl) {
        return mRetrofit2Factory.create(clazz, baseUrl).create(clazz);
    }

    public synchronized <T> T createAcceptAllApi(final Class<T> clazz, final String baseUrl) {
        return mRetrofit2Factory.createAcceptAll(clazz, baseUrl).create(clazz);
    }


}
