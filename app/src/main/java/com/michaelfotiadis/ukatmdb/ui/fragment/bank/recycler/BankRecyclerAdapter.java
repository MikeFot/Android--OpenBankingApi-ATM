package com.michaelfotiadis.ukatmdb.ui.fragment.bank.recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.michaelfotiadis.ukatmdb.ui.fragment.bank.model.UiBank;
import com.michaelfotiadis.ukatmdb.utils.TextUtils;
import com.michaelfotiadis.ukbankatm.ui.recyclerview.adapter.BaseRecyclerViewAdapter;
import com.michaelfotiadis.ukbankatm.ui.recyclerview.listener.OnItemSelectedListener;

public class BankRecyclerAdapter extends BaseRecyclerViewAdapter<UiBank, BankViewHolder> {

    private final BankViewBinder mBinder;


    public BankRecyclerAdapter(final Context context,
                               final OnItemSelectedListener<UiBank> listener) {
        super(context);
        this.mBinder = new BankViewBinder(context, listener);
    }

    @Override
    protected boolean isItemValid(final UiBank item) {
        return item != null && TextUtils.isNotEmpty(item.getName());
    }

    @Override
    public BankViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(BankViewHolder.getLayoutId(), parent, false);
        return new BankViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final BankViewHolder holder, final int position) {
        mBinder.bind(holder, getItem(position));
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        mBinder.detach();
    }
}
