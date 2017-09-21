package com.michaelfotiadis.ukatmdb.ui.fragment.details.recycler.additionalservices;

import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;

public class AdditionalServices {

    @DrawableRes
    private final Integer drawable;
    private final String description;

    public AdditionalServices(final String description) {
        this.description = description;
        this.drawable = null;
    }

    public AdditionalServices(final int drawable, final String description) {
        this.drawable = drawable;
        this.description = description;
    }

    @Nullable
    public Integer getDrawable() {
        return drawable;
    }

    public String getDescription() {
        return description;
    }
}
