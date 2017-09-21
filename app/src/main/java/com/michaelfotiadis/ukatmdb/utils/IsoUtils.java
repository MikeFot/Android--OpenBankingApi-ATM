package com.michaelfotiadis.ukatmdb.utils;

import android.support.annotation.Nullable;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class IsoUtils {

    private Map<String, Locale> localeMap;

    public IsoUtils() {
        String[] countries = Locale.getISOCountries();
        localeMap = new HashMap<>(countries.length);
        for (String country : countries) {
            Locale locale = new Locale("", country);
            localeMap.put(locale.getISO3Country().toUpperCase(), locale);
        }
    }

    @Nullable
    public String iso3CountryCodeToIso2CountryCode(String iso3CountryCode) {

        if (localeMap.containsKey(iso3CountryCode)) {
            return localeMap.get(iso3CountryCode.toUpperCase()).getCountry();
        } else {
            AppLog.e("Could not find locale for " + iso3CountryCode);
            return null;
        }
    }

    public String iso2CountryCodeToIso3CountryCode(String iso2CountryCode) {
        Locale locale = new Locale("", iso2CountryCode);
        return locale.getISO3Country();
    }

}
