
package com.michaelfotiadis.ukatmdb.network.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AtmResponse {

    @SerializedName("meta")
    private final Meta meta;
    @SerializedName("data")
    private final List<Datum> data;

    public AtmResponse(final Meta meta, final List<Datum> data) {
        this.meta = meta;
        this.data = data;
    }

    public Meta getMeta() {
        return meta;
    }

    public List<Datum> getData() {
        return data;
    }
}
