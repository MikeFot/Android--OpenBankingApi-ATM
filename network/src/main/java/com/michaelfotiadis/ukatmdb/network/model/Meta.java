
package com.michaelfotiadis.ukatmdb.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Meta {

    @SerializedName("LastUpdated")
    private final String lastUpdated;
    @SerializedName("TotalResults")
    private final Integer totalResults;
    @SerializedName("Agreement")
    private final String agreement;
    @SerializedName("License")
    private final String license;
    @SerializedName("TermsOfUse")
    private final String termsOfUse;

    private Meta(final Builder builder) {
        lastUpdated = builder.lastUpdated;
        totalResults = builder.totalResults;
        agreement = builder.agreement;
        license = builder.license;
        termsOfUse = builder.termsOfUse;
    }


    public String getLastUpdated() {
        return lastUpdated;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public String getAgreement() {
        return agreement;
    }

    public String getLicense() {
        return license;
    }

    public String getTermsOfUse() {
        return termsOfUse;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private String lastUpdated;
        private Integer totalResults;
        private String agreement;
        private String license;
        private String termsOfUse;

        private Builder() {
        }

        public Builder withLastUpdated(final String val) {
            lastUpdated = val;
            return this;
        }

        public Builder withTotalResults(final Integer val) {
            totalResults = val;
            return this;
        }

        public Builder withAgreement(final String val) {
            agreement = val;
            return this;
        }

        public Builder withLicense(final String val) {
            license = val;
            return this;
        }

        public Builder withTermsOfUse(final String val) {
            termsOfUse = val;
            return this;
        }

        public Meta build() {
            return new Meta(this);
        }
    }
}
