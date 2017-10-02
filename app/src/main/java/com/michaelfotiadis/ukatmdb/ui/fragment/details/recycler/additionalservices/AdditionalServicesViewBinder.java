package com.michaelfotiadis.ukatmdb.ui.fragment.details.recycler.additionalservices;

import android.content.Context;

import com.michaelfotiadis.ukatmdb.R;
import com.michaelfotiadis.ukatmdb.utils.TextUtils;
import com.michaelfotiadis.ukbankatm.ui.recyclerview.viewbinder.BaseRecyclerViewBinder;
import com.michaelfotiadis.ukbankatm.ui.utils.ViewUtils;

public class AdditionalServicesViewBinder extends BaseRecyclerViewBinder<AdditionalServicesViewHolder, AdditionalServices> {

    protected AdditionalServicesViewBinder(final Context context) {
        super(context);
    }

    @Override
    protected void reset(final AdditionalServicesViewHolder holder) {

    }

    @Override
    protected void setData(final AdditionalServicesViewHolder holder, final AdditionalServices item) {

        if (item.getDrawable() == null) {
            ViewUtils.showView(holder.accessibilityImage, false);
        } else {
            ViewUtils.showView(holder.accessibilityImage, true);
            ViewUtils.setDrawable(holder.accessibilityImage, item.getDrawable());
        }

        // sorry about that - country codes, currency codes hack
        if (item.getDescription().length() < 4) {
            holder.description.setText(item.getDescription());
        } else {

            final String desc = getContext().getString(R.string.bullet_placeholder,
                    TextUtils.splitCamelCase(item.getDescription()));
            holder.description.setText(desc);
        }

    }

    @Override
    public void detach() {
        // NOOP
    }
}
