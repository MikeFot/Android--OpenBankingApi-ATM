package com.michaelfotiadis.ukatmdb.ui.fragment.details.recycler.generalinfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.michaelfotiadis.ukatmdb.utils.TextUtils;
import com.michaelfotiadis.ukbankatm.ui.recyclerview.adapter.BaseRecyclerViewAdapter;

public class GeneralInfoRecyclerAdapter extends BaseRecyclerViewAdapter<String, GeneralInfoViewHolder> {

    private final GeneralInfoViewBinder binder;

    public GeneralInfoRecyclerAdapter(final Context context) {
        super(context);
        binder = new GeneralInfoViewBinder(context);
    }

    @Override
    protected boolean isItemValid(final String item) {
        return TextUtils.isNotEmpty(item);
    }

    @Override
    public GeneralInfoViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(GeneralInfoViewHolder.getLayoutId(), parent, false);
        return new GeneralInfoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final GeneralInfoViewHolder holder, final int position) {
        binder.setData(holder, getItem(position));
    }
}
