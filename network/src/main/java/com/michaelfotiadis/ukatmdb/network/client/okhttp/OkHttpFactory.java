package com.michaelfotiadis.ukatmdb.network.client.okhttp;


import okhttp3.OkHttpClient;

public interface OkHttpFactory {

    OkHttpClient create(final Class<?> apiClass);

    OkHttpClient createAcceptAllInterceptor(Class<?> clazz);

}
