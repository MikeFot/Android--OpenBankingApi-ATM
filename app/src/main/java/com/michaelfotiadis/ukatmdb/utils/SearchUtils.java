package com.michaelfotiadis.ukatmdb.utils;

import android.support.annotation.Nullable;

import java.util.List;

public class SearchUtils {

    public static boolean isThereAMatch(@Nullable final String content, @Nullable final String query) {
        return !(TextUtils.isEmpty(content) || TextUtils.isEmpty(query)) && (content.toLowerCase().contains(query.toLowerCase()));
    }

    public static boolean isThereAMatch(@Nullable final List<String> items, @Nullable final String query) {
        if (items == null || items.isEmpty() || TextUtils.isEmpty(query)) {
            return false;
        } else {
            for (final String item : items) {
                if (isThereAMatch(item, query)) {
                    return true;
                }
            }
        }
        return false;
    }

}
