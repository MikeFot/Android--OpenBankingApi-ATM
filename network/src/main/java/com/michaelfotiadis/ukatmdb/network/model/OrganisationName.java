
package com.michaelfotiadis.ukatmdb.network.model;

import com.google.gson.annotations.SerializedName;

public class OrganisationName {

    @SerializedName("LegalName")
    private final String legalName;

    public OrganisationName(final String legalName) {
        this.legalName = legalName;
    }

    public String getLegalName() {
        return legalName;
    }
}
