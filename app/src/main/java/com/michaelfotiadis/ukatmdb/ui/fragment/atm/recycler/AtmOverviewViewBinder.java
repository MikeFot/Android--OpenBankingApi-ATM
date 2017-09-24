package com.michaelfotiadis.ukatmdb.ui.fragment.atm.recycler;

import android.content.Context;
import android.view.View;

import com.michaelfotiadis.ukatmdb.R;
import com.michaelfotiadis.ukatmdb.model.AtmDetails;
import com.michaelfotiadis.ukatmdb.utils.TextUtils;
import com.michaelfotiadis.ukbankatm.ui.recyclerview.listener.OnItemSelectedListener;
import com.michaelfotiadis.ukbankatm.ui.recyclerview.viewbinder.BaseRecyclerViewBinder;

public class AtmOverviewViewBinder extends BaseRecyclerViewBinder<AtmOverviewViewHolder, AtmDetails> {

    private final OnItemSelectedListener<AtmDetails> mListener;

    protected AtmOverviewViewBinder(final Context context, final OnItemSelectedListener<AtmDetails> listener) {
        super(context);
        mListener = listener;
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
            holder.postcode.setText(String.format(getString(R.string.placeholder_postcode), item.getAddressPostCode()));
        } else {
            showView(holder.postcode, false);
        }

        if (TextUtils.isNotEmpty(item.getLocationCategory())) {
            final String summary = TextUtils.splitCamelCase(item.getLocationCategory());
            holder.summary.setText(getContext().getString(R.string.placeholder_location_type, summary));
        } else {
            showView(holder.summary, false);
        }

        holder.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                holder.title.setTag(getString(R.string.tag_bank_name));

                mListener.onListItemSelected(v, item);
            }
        });


    }
}
