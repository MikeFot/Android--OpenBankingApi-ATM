package com.michaelfotiadis.ukatmdb.network.api;

import com.michaelfotiadis.ukatmdb.network.model.AtmResponse;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;

public interface AtmApi {

    @GET("atms")
    Call<AtmResponse> get();

    @GET("atms")
    Observable<AtmResponse> getRx();

}
