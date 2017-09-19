package com.michaelfotiadis.ukatmdb.ui.fragment.atm.recycler;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.view.View;

import com.michaelfotiadis.ukatmdb.R;
import com.michaelfotiadis.ukatmdb.model.AtmDetails;
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



        if (item.getLabel() != null) {
            holder.mTitle.setText(item.getLabel());
        }

        if (item.getAtmId() != null) {
            holder.mLeagueId.setText(String.valueOf(item.getAtmId()));
        }

        if (item.getLocationCategory() != null) {
            holder.mSummary.setText(item.getLocationCategory());
        }

        if (item.getAddressPostCode() != null) {
            holder.mLeaguerUrl.setText(item.getAddressPostCode());
        }


        holder.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                holder.mTitle.setTag(getString(R.string.tag_bank_name));

                mListener.onListItemSelected(v, item);
            }
        });


    }
}
