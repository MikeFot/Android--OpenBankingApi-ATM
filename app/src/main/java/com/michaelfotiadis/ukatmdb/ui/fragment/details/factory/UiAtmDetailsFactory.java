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

        final StringBuilder sb = getAddressField(atmDetails);
        final String address = sb.toString().trim();

        if (TextUtils.isNotEmpty(address)) {
            items.add(address);
        }

        if (TextUtils.isNotEmpty(atmDetails.getMinimumValueDispensed())) {
            items.add(String.format(resources.getString(R.string.placeholder_minimum_value), atmDetails.getMinimumValueDispensed()));
        }
        return items;
    }

    @SuppressWarnings("MethodMayBeStatic")
    @NonNull
    private StringBuilder getAddressField(final AtmDetails atmDetails) {
        final StringBuilder sb = new StringBuilder();

        if (TextUtils.isNotEmpty(atmDetails.getAddressBuildingNumberOrName())) {
            sb.append(atmDetails.getAddressBuildingNumberOrName());
            sb.append("\n");
        }
        if (TextUtils.isNotEmpty(atmDetails.getAddressStreetName())) {
            sb.append(atmDetails.getAddressStreetName());
            sb.append("\n");
        }
        if (TextUtils.isNotEmpty(atmDetails.getAddressTownName())) {
            sb.append(atmDetails.getAddressTownName());
            sb.append("\n");
        }
        if (TextUtils.isNotEmpty(atmDetails.getAddressPostCode())) {
            sb.append(atmDetails.getAddressPostCode());
            sb.append("\n");
        }
        return sb;
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

    @SuppressWarnings("MethodMayBeStatic")
    @NonNull
    public List<AdditionalServices> getCountryServices(final List<String> services) {
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
