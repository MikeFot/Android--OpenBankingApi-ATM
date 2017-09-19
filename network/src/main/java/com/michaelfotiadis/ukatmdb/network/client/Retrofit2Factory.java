package com.michaelfotiadis.ukatmdb.network.client;

import com.google.gson.Gson;
import com.michaelfotiadis.ukatmdb.network.client.okhttp.OkHttpFactory;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/*package*/ class Retrofit2Factory {

    private final Gson gson;
    private final OkHttpFactory httpFactory;

    public Retrofit2Factory(final Gson gson,
                            final OkHttpFactory httpFactory) {
        this.httpFactory = httpFactory;
        this.gson = gson;
    }

    public Retrofit create(final Class<?> clazz, final String baseUrl) {

        return getDefault(clazz, baseUrl)
                // Async RX Adapter
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                .build();

    }

    public Retrofit createAcceptAll(final Class<?> clazz, final String baseUrl) {

        return getAcceptAll(clazz, baseUrl)
                // Async RX Adapter
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                .build();

    }

    private Retrofit.Builder getDefault(final Class<?> clazz, final String baseUrl) {
        final retrofit2.Converter.Factory factory = GsonConverterFactory.create(gson);
        final OkHttpClient client = httpFactory.create(clazz);

        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(factory);
    }

    private Retrofit.Builder getAcceptAll(final Class<?> clazz, final String baseUrl) {
        final retrofit2.Converter.Factory factory = GsonConverterFactory.create(gson);
        final OkHttpClient client = httpFactory.createAcceptAllInterceptor(clazz);

        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(factory);
    }

}
