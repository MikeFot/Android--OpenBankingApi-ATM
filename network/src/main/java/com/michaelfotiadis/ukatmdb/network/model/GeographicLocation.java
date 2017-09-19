
package com.michaelfotiadis.ukatmdb.network.model;

import com.google.gson.annotations.SerializedName;

public class GeographicLocation {

    @SerializedName("Latitude")
    private final String latitude;
    @SerializedName("Longitude")
    private final String longitude;

    public GeographicLocation(final String latitude, final String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }
}
