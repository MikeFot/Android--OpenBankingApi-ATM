
package com.michaelfotiadis.ukatmdb.network.model;

import com.google.gson.annotations.SerializedName;

public class Brand {

    @SerializedName("TrademarkIPOCode")
    private final String trademarkIPOCode;
    @SerializedName("TrademarkID")
    private final String trademarkID;

    public Brand(final String trademarkIPOCode, final String trademarkID) {
        this.trademarkIPOCode = trademarkIPOCode;
        this.trademarkID = trademarkID;
    }

    public String getTrademarkIPOCode() {
        return trademarkIPOCode;
    }

    public String getTrademarkID() {
        return trademarkID;
    }
}
