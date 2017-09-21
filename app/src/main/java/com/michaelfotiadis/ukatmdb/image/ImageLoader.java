package com.michaelfotiadis.ukatmdb.image;

import android.support.annotation.DrawableRes;

import com.michaelfotiadis.ukatmdb.R;
import com.michaelfotiadis.ukatmdb.utils.AppLog;

import uk.co.alt236.resourcemirror.Mirror;

public class ImageLoader {

    @DrawableRes
    public static int getCountryImageIdReflectively(final String drawableName) {

        AppLog.d("Looking for drawable name: " + drawableName);


        final String searchName;
        // known exception, need to update my library
        if (drawableName.equals("en")) {
            searchName = "gb";
        } else {
            searchName = drawableName.toLowerCase();
        }

        return Mirror.of("com.michaelfotiadis.ukatmdb").getDrawables().optListDrawableId(searchName, "country", R.drawable.ic_list_country_unknown);

    }


}
