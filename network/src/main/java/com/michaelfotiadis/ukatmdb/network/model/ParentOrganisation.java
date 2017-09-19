
package com.michaelfotiadis.ukatmdb.network.model;

import com.google.gson.annotations.SerializedName;

public class ParentOrganisation {

    @SerializedName("LEI")
    private final String lEI;
    @SerializedName("BIC")
    private final String bIC;
    @SerializedName("OrganisationName")
    private final OrganisationName organisationName;

    public ParentOrganisation(final String lEI, final String bIC, final OrganisationName organisationName) {
        this.lEI = lEI;
        this.bIC = bIC;
        this.organisationName = organisationName;
    }

    public String getBic() {
        return bIC;
    }

    public String getLei() {
        return lEI;
    }

    public OrganisationName getOrganisationName() {
        return organisationName;
    }
}
