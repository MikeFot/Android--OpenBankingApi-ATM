package com.michaelfotiadis.ukatmdb.image;

import android.support.annotation.DrawableRes;

import com.michaelfotiadis.ukatmdb.R;
import com.michaelfotiadis.ukatmdb.utils.AppLog;

import uk.co.alt236.resourcemirror.Mirror;

public class ImageLoader {

    @DrawableRes
    public static int getImageIdReflectively(final String drawableName, final ImageFamily family) {

        AppLog.d("Looking for drawable name: " + drawableName + " of family " + family);

        return Mirror.of("com.michaelfotiadis.ukatmdb").getDrawables().optListDrawableId(drawableName, getFamily(family), getFallback(family));

    }

    @DrawableRes
    private static int getFallback(final ImageFamily family) {

        switch (family) {

            case COUNTRY:
                return R.drawable.ic_list_country_unknown;
            case HERO:
                break;
            case ITEM:
                break;
            case MINI:
                break;
        }
        return R.drawable.ic_list_country_unknown;
    }

    private static String getFamily(final ImageFamily type) {

        switch (type) {

            case COUNTRY:
                return "country";
            case HERO:
                break;
            case ITEM:
                break;
            case MINI:
                break;
        }
        return "";

    }

    public enum ImageFamily {
        COUNTRY, HERO, ITEM, MINI
    }

}
