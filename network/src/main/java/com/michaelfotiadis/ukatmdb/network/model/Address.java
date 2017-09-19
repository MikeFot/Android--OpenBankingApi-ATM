
package com.michaelfotiadis.ukatmdb.network.model;

import com.google.gson.annotations.SerializedName;

public class Address {

    @SerializedName("StreetName")
    private final String streetName;
    @SerializedName("BuildingNumberOrName")
    private final String buildingNumberOrName;
    @SerializedName("PostCode")
    private final String postCode;
    @SerializedName("OptionalAddressField")
    private final String optionalAddressField;
    @SerializedName("TownName")
    private final String townName;
    @SerializedName("CountrySubDivision")
    private final String countrySubDivision;
    @SerializedName("Country")
    private final String country;

    private Address(final Builder builder) {
        streetName = builder.streetName;
        buildingNumberOrName = builder.buildingNumberOrName;
        postCode = builder.postCode;
        optionalAddressField = builder.optionalAddressField;
        townName = builder.townName;
        countrySubDivision = builder.countrySubDivision;
        country = builder.country;
    }


    public String getStreetName() {
        return streetName;
    }

    public String getBuildingNumberOrName() {
        return buildingNumberOrName;
    }

    public String getPostCode() {
        return postCode;
    }

    public String getOptionalAddressField() {
        return optionalAddressField;
    }

    public String getTownName() {
        return townName;
    }

    public String getCountrySubDivision() {
        return countrySubDivision;
    }

    public String getCountry() {
        return country;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private String streetName;
        private String buildingNumberOrName;
        private String postCode;
        private String optionalAddressField;
        private String townName;
        private String countrySubDivision;
        private String country;

        private Builder() {
        }

        public Builder withStreetName(final String val) {
            streetName = val;
            return this;
        }

        public Builder withBuildingNumberOrName(final String val) {
            buildingNumberOrName = val;
            return this;
        }

        public Builder withPostCode(final String val) {
            postCode = val;
            return this;
        }

        public Builder withOptionalAddressField(final String val) {
            optionalAddressField = val;
            return this;
        }

        public Builder withTownName(final String val) {
            townName = val;
            return this;
        }

        public Builder withCountrySubDivision(final String val) {
            countrySubDivision = val;
            return this;
        }

        public Builder withCountry(final String val) {
            country = val;
            return this;
        }

        public Address build() {
            return new Address(this);
        }
    }
}
