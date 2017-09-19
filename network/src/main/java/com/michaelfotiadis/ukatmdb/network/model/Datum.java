
package com.michaelfotiadis.ukatmdb.network.model;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("AccessibilityTypes")
    private final List<String> accessibilityTypes;
    @SerializedName("Address")
    private final Address address;
    @SerializedName("AdditionalATMServices")
    private final List<String> additionalATMServices;
    @SerializedName("ATMID")
    private final String aTMID;
    @SerializedName("ATMServices")
    private final List<String> aTMServices;
    @SerializedName("BranchIdentification")
    private final String branchIdentification;
    @SerializedName("Currency")
    private final List<String> currency;
    @SerializedName("GeographicLocation")
    private final GeographicLocation geographicLocation;
    @SerializedName("LocationCategory")
    private final String locationCategory;
    @SerializedName("MinimumValueDispensed")
    private final String minimumValueDispensed;
    @SerializedName("Organisation")
    private final Organisation organisation;
    @SerializedName("SiteID")
    private final String siteID;
    @SerializedName("SiteName")
    private final String siteName;
    @SerializedName("SupportedLanguages")
    private final List<String> supportedLanguages;


    private Datum(final Builder builder) {
        accessibilityTypes = builder.accessibilityTypes;
        address = builder.address;
        additionalATMServices = builder.additionalATMServices;
        aTMID = builder.aTMID;
        aTMServices = builder.aTMServices;
        branchIdentification = builder.branchIdentification;
        currency = builder.currency;
        geographicLocation = builder.geographicLocation;
        locationCategory = builder.locationCategory;
        minimumValueDispensed = builder.minimumValueDispensed;
        organisation = builder.organisation;
        siteID = builder.siteID;
        siteName = builder.siteName;
        supportedLanguages = builder.supportedLanguages;
    }

    public String getSiteName() {
        return siteName;
    }

    public Organisation getOrganisation() {
        return organisation;
    }

    public String getBranchIdentification() {
        return branchIdentification;
    }

    public String getAtmId() {
        return aTMID;
    }

    public String getLocationCategory() {
        return locationCategory;
    }

    public String getSiteID() {
        return siteID;
    }

    public Address getAddress() {
        return address;
    }

    public GeographicLocation getGeographicLocation() {
        return geographicLocation;
    }

    public List<String> getAccessibilityTypes() {
        return accessibilityTypes;
    }

    public List<String> getSupportedLanguages() {
        return supportedLanguages;
    }

    public List<String> getAtmServices() {
        return aTMServices;
    }

    public List<String> getAdditionalATMServices() {
        return additionalATMServices;
    }

    public List<String> getCurrency() {
        return currency;
    }

    public String getMinimumValueDispensed() {
        return minimumValueDispensed;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private Organisation organisation;
        private String branchIdentification;
        private String aTMID;
        private String locationCategory;
        private String siteID;
        private String siteName;
        private Address address;
        private GeographicLocation geographicLocation;
        private List<String> accessibilityTypes;
        private List<String> supportedLanguages;
        private List<String> aTMServices;
        private List<String> additionalATMServices;
        private List<String> currency;
        private String minimumValueDispensed;

        private Builder() {
        }

        public Builder withOrganisation(final Organisation val) {
            organisation = val;
            return this;
        }

        public Builder withBranchIdentification(final String val) {
            branchIdentification = val;
            return this;
        }

        public Builder withATMID(final String val) {
            aTMID = val;
            return this;
        }

        public Builder withLocationCategory(final String val) {
            locationCategory = val;
            return this;
        }

        public Builder withSiteID(final String val) {
            siteID = val;
            return this;
        }

        public Builder withSiteName(final String val) {
            siteName = val;
            return this;
        }

        public Builder withAddress(final Address val) {
            address = val;
            return this;
        }

        public Builder withGeographicLocation(final GeographicLocation val) {
            geographicLocation = val;
            return this;
        }

        public Builder withAccessibilityTypes(final List<String> val) {
            accessibilityTypes = val;
            return this;
        }

        public Builder withSupportedLanguages(final List<String> val) {
            supportedLanguages = val;
            return this;
        }

        public Builder withATMServices(final List<String> val) {
            aTMServices = val;
            return this;
        }

        public Builder withAdditionalATMServices(final List<String> val) {
            additionalATMServices = val;
            return this;
        }

        public Builder withCurrency(final List<String> val) {
            currency = val;
            return this;
        }

        public Builder withMinimumValueDispensed(final String val) {
            minimumValueDispensed = val;
            return this;
        }

        public Datum build() {
            return new Datum(this);
        }
    }
}
