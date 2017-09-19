package com.michaelfotiadis.ukatmdb.ui.fragment.atm.recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.michaelfotiadis.ukatmdb.model.AtmDetails;
import com.michaelfotiadis.ukbankatm.ui.recyclerview.adapter.BaseRecyclerViewAdapter;
import com.michaelfotiadis.ukbankatm.ui.recyclerview.listener.OnItemSelectedListener;

public class AtmRecyclerAdapter extends BaseRecyclerViewAdapter<AtmDetails, AtmOverviewViewHolder> {

    private final AtmOverviewViewBinder mBinder;


    public AtmRecyclerAdapter(final Context context,
                              final OnItemSelectedListener<AtmDetails> listener) {
        super(context);
        this.mBinder = new AtmOverviewViewBinder(context, listener);
    }

    @Override
    protected boolean isItemValid(final AtmDetails item) {
        return item != null;
    }

    @Override
    public AtmOverviewViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(AtmOverviewViewHolder.getLayoutId(), parent, false);
        return new AtmOverviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AtmOverviewViewHolder holder, final int position) {
        mBinder.bind(holder, getItem(position));
    }

}
