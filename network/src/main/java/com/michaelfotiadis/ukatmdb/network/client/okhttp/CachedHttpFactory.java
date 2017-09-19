package com.michaelfotiadis.ukatmdb.network.client.okhttp;


import com.michaelfotiadis.ukatmdb.network.client.CachingControlInterceptor;
import com.michaelfotiadis.ukatmdb.network.client.NetworkResolver;
import com.michaelfotiadis.ukatmdb.network.client.interceptors.HeadersInterceptor;
import com.michaelfotiadis.ukatmdb.network.client.interceptors.RetryPolicyInterceptor;

import java.io.File;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Cache;
import okhttp3.ConnectionSpec;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.TlsVersion;
import okhttp3.logging.HttpLoggingInterceptor;

public class CachedHttpFactory implements OkHttpFactory {

    private static final long SIZE_OF_CACHE = 10 * 1024 * 1024; // 10 Mb
    private final Cache mCache;
    private final NetworkResolver mNetworkResolver;
    private final boolean mIsEnableDebug;

    public CachedHttpFactory(final File cacheFile,
                             final NetworkResolver networkResolver,
                             final boolean enableDebug) {

        mCache = cacheFile != null ? new Cache(cacheFile, SIZE_OF_CACHE) : null;
        this.mNetworkResolver = networkResolver;
        this.mIsEnableDebug = enableDebug;

    }

    @Override
    public OkHttpClient create(final Class<?> clazz) {
        return createClient(getDefaultInterceptors());
    }

    @Override
    public OkHttpClient createAcceptAllInterceptor(final Class<?> clazz) {
        return createAcceptAllClient(getDefaultInterceptors());
    }

    private List<Interceptor> getDefaultInterceptors() {
        final List<Interceptor> interceptors = new ArrayList<>();
        interceptors.add(new HeadersInterceptor());
        interceptors.add(new RetryPolicyInterceptor());
        interceptors.add(createLoggingInterceptor());

        if (mCache != null && mNetworkResolver != null) {
            interceptors.add(new CachingControlInterceptor(mNetworkResolver));
        }
        return interceptors;
    }

    private OkHttpClient createAcceptAllClient(final List<Interceptor> interceptors) {


        final OkHttpClient.Builder builder = getDefaultBuilder(interceptors);

        // Create a trust manager that does not validate certificate chains
        final TrustManager[] trustAllCerts = new TrustManager[] {
                new IneerX509TrustManager()
        };

        // Install the all-trusting trust manager
        final SSLContext sslContext;
        try {
            sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager)trustAllCerts[0]);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(final String hostname, final SSLSession session) {
                    return true;
                }
            });
        } catch (KeyManagementException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return builder.build();
    }

    private OkHttpClient createClient(final List<Interceptor> interceptors) {
        return getDefaultBuilder(interceptors).build();
    }

    private OkHttpClient.Builder getDefaultBuilder(final List<Interceptor> interceptors) {
        final OkHttpClient.Builder builder = new OkHttpClient.Builder();

        if (mCache != null) {
            builder.cache(mCache);
        }

        for (final Interceptor interceptor : interceptors) {
            builder.addInterceptor(interceptor);
        }

        builder.readTimeout(15, TimeUnit.SECONDS);

        return builder;
    }

    private Interceptor createLoggingInterceptor() {

        final HttpLoggingInterceptor.Level level = mIsEnableDebug ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE;

        final HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(level);

        return interceptor;
    }

    private static class IneerX509TrustManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(final java.security.cert.X509Certificate[] chain, final String authType) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(final java.security.cert.X509Certificate[] chain, final String authType) throws CertificateException {
        }

        @Override
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return new java.security.cert.X509Certificate[]{};
        }
    }
}
