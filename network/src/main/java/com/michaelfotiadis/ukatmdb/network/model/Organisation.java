
package com.michaelfotiadis.ukatmdb.network.model;

import com.google.gson.annotations.SerializedName;

public class Organisation {

    @SerializedName("ParentOrganisation")
    private final ParentOrganisation parentOrganisation;
    @SerializedName("Brand")
    private final Brand brand;

    public Organisation(final ParentOrganisation parentOrganisation, final Brand brand) {
        this.parentOrganisation = parentOrganisation;
        this.brand = brand;
    }

    public ParentOrganisation getParentOrganisation() {
        return parentOrganisation;
    }

    public Brand getBrand() {
        return brand;
    }
}
