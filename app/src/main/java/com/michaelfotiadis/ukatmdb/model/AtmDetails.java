package com.michaelfotiadis.ukatmdb.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.michaelfotiadis.ukatmdb.network.model.Address;
import com.michaelfotiadis.ukatmdb.network.model.Datum;
import com.michaelfotiadis.ukatmdb.network.model.GeographicLocation;
import com.michaelfotiadis.ukatmdb.network.model.Organisation;
import com.michaelfotiadis.ukatmdb.utils.TextUtils;

import java.util.List;

public class AtmDetails implements Parcelable {


    private final List<String> mAccessibilityTypes;
    private final List<String> mAdditionalATMServices;
    private final String mAtmId;
    private final List<String> mAtmServices;
    private final String mBranchIdentification;
    private final List<String> mCurrency;
    private final String mLocationCategory;
    private final String mMinimumValueDispensed;
    private final String mSiteID;
    private final String mSiteName;
    private final List<String> mSupportedLanguages;

    private final String mAddressStreetName;
    private final String mAddressBuildingNumberOrName;
    private final String mAddressPostCode;
    private final String mAddressOptionalAddressField;
    private final String mAddressTownName;
    private final String mAddressCountrySubDivision;
    private final String mAddressCountry;

    private final String mLocationLatitude;
    private final String mLocationLongitude;

    private final String mOrganisationTrademarkIPOCode;
    private final String mOrganisationTrademarkID;
    private final String mOrganisationLei;
    private final String mOrganisationBic;
    private final String mOrganisationLegalName;


    public AtmDetails(@NonNull final Datum datum) {

        mAccessibilityTypes = datum.getAccessibilityTypes();
        mAdditionalATMServices = datum.getAdditionalATMServices();
        mAtmId = datum.getAtmId();
        mAtmServices = datum.getAtmServices();
        mBranchIdentification = datum.getBranchIdentification();
        mCurrency = datum.getCurrency();
        mLocationCategory = datum.getLocationCategory();
        mMinimumValueDispensed = datum.getMinimumValueDispensed();
        mSiteID = datum.getSiteID();
        mSiteName = datum.getSiteName();
        mSupportedLanguages = datum.getSupportedLanguages();

        final Address address = datum.getAddress();
        if (address != null) {

            mAddressStreetName = address.getStreetName();
            mAddressBuildingNumberOrName = address.getBuildingNumberOrName();
            mAddressPostCode = address.getPostCode();
            mAddressOptionalAddressField = address.getOptionalAddressField();
            mAddressTownName = address.getTownName();
            mAddressCountrySubDivision = address.getCountrySubDivision();
            mAddressCountry = address.getCountry();

        } else {
            mAddressStreetName = null;
            mAddressBuildingNumberOrName = null;
            mAddressPostCode = null;
            mAddressOptionalAddressField = null;
            mAddressTownName = null;
            mAddressCountrySubDivision = null;
            mAddressCountry = null;
        }

        final GeographicLocation location = datum.getGeographicLocation();
        if (location != null) {
            mLocationLongitude = location.getLongitude();
            mLocationLatitude = location.getLatitude();
        } else {
            mLocationLongitude = null;
            mLocationLatitude = null;
        }

        final Organisation organisation = datum.getOrganisation();
        if (organisation != null) {

            if (organisation.getBrand() != null) {
                mOrganisationTrademarkID = organisation.getBrand().getTrademarkID();
                mOrganisationTrademarkIPOCode = organisation.getBrand().getTrademarkIPOCode();
            } else {
                mOrganisationTrademarkID = null;
                mOrganisationTrademarkIPOCode = null;
            }

            if (organisation.getParentOrganisation() != null) {
                mOrganisationLei = organisation.getParentOrganisation().getLei();
                mOrganisationBic = organisation.getParentOrganisation().getBic();
                if (organisation.getParentOrganisation().getOrganisationName() != null) {
                    mOrganisationLegalName = organisation.getParentOrganisation().getOrganisationName().getLegalName();
                } else {
                    mOrganisationLegalName = null;
                }
            } else {
                mOrganisationLei = null;
                mOrganisationBic = null;
                mOrganisationLegalName = null;
            }


        } else {
            mOrganisationTrademarkID = null;
            mOrganisationTrademarkIPOCode = null;
            mOrganisationLei = null;
            mOrganisationBic = null;
            mOrganisationLegalName = null;
        }

    }

    public String getLabel() {
        final String label;
        if (TextUtils.isNotEmpty(getAddressStreetName())) {
            label = getAddressStreetName();
        } else if (TextUtils.isNotEmpty(getAddressPostCode())) {
            label = getAddressPostCode();
        } else if (TextUtils.isNotEmpty(getAddressTownName())) {
            label = getAddressTownName();
        } else {
            label = getSiteName();
        }
        return label;
    }


    public List<String> getAccessibilityTypes() {
        return mAccessibilityTypes;
    }

    public List<String> getAdditionalATMServices() {
        return mAdditionalATMServices;
    }

    public String getAtmId() {
        return mAtmId;
    }

    public List<String> getAtmServices() {
        return mAtmServices;
    }

    public String getBranchIdentification() {
        return mBranchIdentification;
    }

    public List<String> getCurrency() {
        return mCurrency;
    }

    public String getLocationCategory() {
        return mLocationCategory;
    }

    public String getMinimumValueDispensed() {
        return mMinimumValueDispensed;
    }

    public String getSiteID() {
        return mSiteID;
    }

    public String getSiteName() {
        return mSiteName;
    }

    public List<String> getSupportedLanguages() {
        return mSupportedLanguages;
    }

    public String getAddressStreetName() {
        return mAddressStreetName;
    }

    public String getAddressBuildingNumberOrName() {
        return mAddressBuildingNumberOrName;
    }

    public String getAddressPostCode() {
        return mAddressPostCode;
    }

    public String getAddressOptionalAddressField() {
        return mAddressOptionalAddressField;
    }

    public String getAddressTownName() {
        return mAddressTownName;
    }

    public String getAddressCountrySubDivision() {
        return mAddressCountrySubDivision;
    }

    public String getAddressCountry() {
        return mAddressCountry;
    }

    public String getLocationLatitude() {
        return mLocationLatitude;
    }

    public String getLocationLongitude() {
        return mLocationLongitude;
    }

    public String getOrganisationTrademarkIPOCode() {
        return mOrganisationTrademarkIPOCode;
    }

    public String getOrganisationTrademarkID() {
        return mOrganisationTrademarkID;
    }

    public String getOrganisationLei() {
        return mOrganisationLei;
    }

    public String getOrganisationBic() {
        return mOrganisationBic;
    }

    public String getOrganisationLegalName() {
        return mOrganisationLegalName;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(this.mAccessibilityTypes);
        dest.writeStringList(this.mAdditionalATMServices);
        dest.writeString(this.mAtmId);
        dest.writeStringList(this.mAtmServices);
        dest.writeString(this.mBranchIdentification);
        dest.writeStringList(this.mCurrency);
        dest.writeString(this.mLocationCategory);
        dest.writeString(this.mMinimumValueDispensed);
        dest.writeString(this.mSiteID);
        dest.writeString(this.mSiteName);
        dest.writeStringList(this.mSupportedLanguages);
        dest.writeString(this.mAddressStreetName);
        dest.writeString(this.mAddressBuildingNumberOrName);
        dest.writeString(this.mAddressPostCode);
        dest.writeString(this.mAddressOptionalAddressField);
        dest.writeString(this.mAddressTownName);
        dest.writeString(this.mAddressCountrySubDivision);
        dest.writeString(this.mAddressCountry);
        dest.writeString(this.mLocationLatitude);
        dest.writeString(this.mLocationLongitude);
        dest.writeString(this.mOrganisationTrademarkIPOCode);
        dest.writeString(this.mOrganisationTrademarkID);
        dest.writeString(this.mOrganisationLei);
        dest.writeString(this.mOrganisationBic);
        dest.writeString(this.mOrganisationLegalName);
    }

    protected AtmDetails(Parcel in) {
        this.mAccessibilityTypes = in.createStringArrayList();
        this.mAdditionalATMServices = in.createStringArrayList();
        this.mAtmId = in.readString();
        this.mAtmServices = in.createStringArrayList();
        this.mBranchIdentification = in.readString();
        this.mCurrency = in.createStringArrayList();
        this.mLocationCategory = in.readString();
        this.mMinimumValueDispensed = in.readString();
        this.mSiteID = in.readString();
        this.mSiteName = in.readString();
        this.mSupportedLanguages = in.createStringArrayList();
        this.mAddressStreetName = in.readString();
        this.mAddressBuildingNumberOrName = in.readString();
        this.mAddressPostCode = in.readString();
        this.mAddressOptionalAddressField = in.readString();
        this.mAddressTownName = in.readString();
        this.mAddressCountrySubDivision = in.readString();
        this.mAddressCountry = in.readString();
        this.mLocationLatitude = in.readString();
        this.mLocationLongitude = in.readString();
        this.mOrganisationTrademarkIPOCode = in.readString();
        this.mOrganisationTrademarkID = in.readString();
        this.mOrganisationLei = in.readString();
        this.mOrganisationBic = in.readString();
        this.mOrganisationLegalName = in.readString();
    }

    public static final Creator<AtmDetails> CREATOR = new Creator<AtmDetails>() {
        @Override
        public AtmDetails createFromParcel(Parcel source) {
            return new AtmDetails(source);
        }

        @Override
        public AtmDetails[] newArray(int size) {
            return new AtmDetails[size];
        }
    };
}
