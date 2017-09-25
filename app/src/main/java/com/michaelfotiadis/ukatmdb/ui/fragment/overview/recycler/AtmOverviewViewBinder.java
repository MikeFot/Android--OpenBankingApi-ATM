package com.michaelfotiadis.ukatmdb.ui.fragment.overview.recycler;

import android.content.Context;

import com.jakewharton.rxbinding2.view.RxView;
import com.michaelfotiadis.ukatmdb.R;
import com.michaelfotiadis.ukatmdb.model.AtmDetails;
import com.michaelfotiadis.ukatmdb.utils.TextUtils;
import com.michaelfotiadis.ukbankatm.ui.recyclerview.listener.OnItemSelectedListener;
import com.michaelfotiadis.ukbankatm.ui.recyclerview.viewbinder.BaseRecyclerViewBinder;

import java.util.concurrent.TimeUnit;

public class AtmOverviewViewBinder extends BaseRecyclerViewBinder<AtmOverviewViewHolder, AtmDetails> {

    private final OnItemSelectedListener<AtmDetails> listener;

    protected AtmOverviewViewBinder(final Context context, final OnItemSelectedListener<AtmDetails> listener) {
        super(context);
        this.listener = listener;
    }

    @Override
    protected void reset(final AtmOverviewViewHolder holder) {
    }

    @Override
    protected void setData(final AtmOverviewViewHolder holder, final AtmDetails item) {

        holder.getRoot().setTag(null);


        if (TextUtils.isNotEmpty(item.getAddressTownName())) {
            holder.title.setText(item.getAddressTownName());
        } else if (TextUtils.isNotEmpty(item.getSiteName())) {
            holder.title.setText(item.getSiteName());
        } else {
            showView(holder.title, false);
        }

        if (TextUtils.isNotEmpty(item.getAddressStreetName())) {
            holder.street.setText(item.getAddressStreetName());
        } else {
            showView(holder.street, false);
        }

        if (TextUtils.isNotEmpty(item.getAddressPostCode())) {
            holder.postcode.setText(item.getAddressPostCode());
        } else {
            showView(holder.postcode, false);
        }

        if (TextUtils.isNotEmpty(item.getLocationCategory())) {
            final String summary = TextUtils.splitCamelCase(item.getLocationCategory());
            holder.summary.setText(getContext().getString(R.string.placeholder_location_type, summary));
        } else {
            showView(holder.summary, false);
        }

        RxView.clicks(holder.getRoot())
                .debounce(200, TimeUnit.MILLISECONDS)
                .takeUntil(RxView.detaches(holder.getRoot()))
                .subscribe(click -> {
                    holder.title.setTag(AtmOverviewViewBinder.this.getString(R.string.tag_bank_name));
                    listener.onListItemSelected(holder.getRoot(), item);
                });

    }
}
