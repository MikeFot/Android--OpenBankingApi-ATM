package com.michaelfotiadis.ukatmdb.ui.fragment.overview.utils;

import android.location.Address;
import android.support.annotation.NonNull;

import com.michaelfotiadis.ukatmdb.model.AtmDetails;
import com.michaelfotiadis.ukatmdb.utils.AppLog;
import com.michaelfotiadis.ukatmdb.utils.ListUtils;
import com.michaelfotiadis.ukatmdb.utils.SearchUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public final class AtmFilterUtils {

    private AtmFilterUtils() {
        // NOOP
    }

    public static List<AtmDetails> findNearestAddresses(@io.reactivex.annotations.NonNull final Address address, final List<AtmDetails> items) {

        AppLog.d("Got address " + address.getLatitude() + " " + address.getLongitude());
        if (ListUtils.isListNotNullOrEmpty(items)) {

            final Map<Double, AtmDetails> tree = new TreeMap<>();

            for (final AtmDetails atm : items) {

                final Double lon = atm.getLongitudeAsDouble();
                final Double lat = atm.getLatitudeAsDouble();

                final double distance;
                //noinspection IfMayBeConditional
                if (lon != null && lat != null) {
                    distance = Math.hypot(lon - address.getLongitude(), lat - address.getLatitude());
                } else {
                    distance = Double.MAX_VALUE;
                }

                tree.put(distance, atm);
            }

            return new ArrayList<>(tree.values());
        } else {
            return ListUtils.isListNotNullOrEmpty(items) ? items : Collections.emptyList();
        }
    }

    @NonNull
    public static List<AtmDetails> getFilteredItems(final String query, final List<AtmDetails> items) {
        final List<AtmDetails> filteredItems = new ArrayList<>();

        for (final AtmDetails details : items) {

            if (SearchUtils.isThereAMatch(details.getLabel(), query)
                    || SearchUtils.isThereAMatch(details.getSiteName(), query)
                    || SearchUtils.isThereAMatch(details.getAddressCountry(), query)
                    || SearchUtils.isThereAMatch(details.getAddressPostCode(), query)
                    || SearchUtils.isThereAMatch(details.getAddressTownName(), query)
                    || SearchUtils.isThereAMatch(details.getAddressBuildingNumberOrName(), query)
                    || SearchUtils.isThereAMatch(details.getAddressOptionalAddressField(), query)
                    || SearchUtils.isThereAMatch(details.getAddressStreetName(), query)
                    || SearchUtils.isThereAMatch(details.getSupportedLanguages(), query)
                    || SearchUtils.isThereAMatch(details.getAdditionalATMServices(), query)
                    || SearchUtils.isThereAMatch(details.getAccessibilityTypes(), query)
                    || SearchUtils.isThereAMatch(details.getAtmServices(), query)
                    || SearchUtils.isThereAMatch(details.getCurrency(), query)) {
                filteredItems.add(details);
            }

        }
        return filteredItems;
    }

}
