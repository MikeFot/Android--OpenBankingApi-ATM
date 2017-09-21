package com.michaelfotiadis.ukatmdb.ui.fragment.details.recycler.additionalservices;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.michaelfotiadis.ukbankatm.ui.recyclerview.adapter.BaseRecyclerViewAdapter;

public class AdditionalServicesRecyclerAdapter extends BaseRecyclerViewAdapter<AdditionalServices, AdditionalServicesViewHolder> {

    private final AdditionalServicesViewBinder binder;

    public AdditionalServicesRecyclerAdapter(final Context context) {
        super(context);
        binder = new AdditionalServicesViewBinder(context);
    }

    @Override
    protected boolean isItemValid(final AdditionalServices item) {
        return item != null && item.getDescription() != null;
    }

    @Override
    public AdditionalServicesViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(AdditionalServicesViewHolder.getLayoutId(), parent, false);
        return new AdditionalServicesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AdditionalServicesViewHolder holder, final int position) {

        binder.setData(holder, getItem(position));

    }
}
