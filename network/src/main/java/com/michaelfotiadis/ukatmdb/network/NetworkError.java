package com.michaelfotiadis.ukatmdb.network;

public class NetworkError {

    private final Throwable mException;

    public NetworkError(final Throwable e) {

        mException = e;
    }

    public Throwable getException() {
        return mException;
    }
}
