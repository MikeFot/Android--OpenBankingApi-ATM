package com.michaelfotiadis.ukatmdb.ui.fragment.details.factory;

import android.content.res.Resources;
import android.support.annotation.NonNull;

import com.michaelfotiadis.ukatmdb.R;
import com.michaelfotiadis.ukatmdb.image.ImageLoader;
import com.michaelfotiadis.ukatmdb.model.AtmDetails;
import com.michaelfotiadis.ukatmdb.ui.fragment.details.recycler.additionalservices.AdditionalServices;
import com.michaelfotiadis.ukatmdb.utils.IsoUtils;
import com.michaelfotiadis.ukatmdb.utils.ListUtils;
import com.michaelfotiadis.ukatmdb.utils.TextUtils;

import java.util.ArrayList;
import java.util.List;

public class UiAtmDetailsFactory {

    private final Resources resources;


    public UiAtmDetailsFactory(final Resources resources) {
        this.resources = resources;
    }

    @NonNull
    public List<String> getInfoItems(final AtmDetails atmDetails) {

        final List<String> items = new ArrayList<>();

        if (TextUtils.isNotEmpty(atmDetails.getAddressBuildingNumberOrName())) {
            items.add(String.format(resources.getString(R.string.placeholder_address_building_number), atmDetails.getAddressBuildingNumberOrName()));
        }
        if (TextUtils.isNotEmpty(atmDetails.getAddressStreetName())) {
            items.add(String.format(resources.getString(R.string.placeholder_address_street_name), atmDetails.getAddressStreetName()));
        }
        if (TextUtils.isNotEmpty(atmDetails.getAddressTownName())) {
            items.add(String.format(resources.getString(R.string.placeholder_address_town), atmDetails.getAddressTownName()));
        }
        if (TextUtils.isNotEmpty(atmDetails.getAddressPostCode())) {
            items.add(String.format(resources.getString(R.string.placeholder_address_postcode), atmDetails.getAddressPostCode()));
        }

        if (TextUtils.isNotEmpty(atmDetails.getMinimumValueDispensed())) {
            items.add(String.format(resources.getString(R.string.placeholder_minimum_value), atmDetails.getMinimumValueDispensed()));
        }
        return items;
    }


    @SuppressWarnings("MethodMayBeStatic")
    @NonNull
    public List<AdditionalServices> getAdditionalServices(final List<String> services) {
        final List<AdditionalServices> additionalServices = new ArrayList<>();

        if (ListUtils.isListNotNullOrEmpty(services)) {

            for (final String atmService : services) {
                additionalServices.add(new AdditionalServices(atmService));
            }

        }
        return additionalServices;
    }

    @NonNull
    public List<AdditionalServices> getCountryServices(List<String> services) {
        final List<AdditionalServices> additionalServices = new ArrayList<>();

        if (ListUtils.isListNotNullOrEmpty(services)) {

            final IsoUtils isoUtils = new IsoUtils();
            for (final String service : services) {

                final Integer drawableRes;

                if (service.length() == 3) {
                    final String iso2 = isoUtils.iso3CountryCodeToIso2CountryCode(service);
                    //noinspection IfMayBeConditional
                    if (iso2 != null) {
                        drawableRes = ImageLoader.getCountryImageIdReflectively(iso2.toLowerCase());
                    } else {
                        drawableRes = null;
                    }
                } else if (service.length() == 2) {
                    drawableRes = ImageLoader.getCountryImageIdReflectively(service.toLowerCase());
                } else {
                    drawableRes = null;
                }

                if (drawableRes == null) {
                    additionalServices.add(new AdditionalServices(service));
                } else {
                    additionalServices.add(new AdditionalServices(drawableRes, service));
                }

            }

        }
        return additionalServices;
    }

}
