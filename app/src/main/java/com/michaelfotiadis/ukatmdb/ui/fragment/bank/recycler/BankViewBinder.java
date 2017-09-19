package com.michaelfotiadis.ukatmdb.ui.fragment.bank.recycler;

import android.content.Context;
import android.view.View;

import com.michaelfotiadis.ukatmdb.model.AtmDetails;
import com.michaelfotiadis.ukatmdb.network.Bank;
import com.michaelfotiadis.ukatmdb.ui.fragment.bank.model.UiBank;
import com.michaelfotiadis.ukbankatm.ui.recyclerview.listener.OnItemSelectedListener;
import com.michaelfotiadis.ukbankatm.ui.recyclerview.viewbinder.BaseRecyclerViewBinder;

public class BankViewBinder extends BaseRecyclerViewBinder<BankViewHolder, UiBank> {

    private final OnItemSelectedListener<UiBank> mListener;

    protected BankViewBinder(final Context context, final OnItemSelectedListener<UiBank> listener) {
        super(context);
        mListener = listener;
    }

    @Override
    protected void reset(final BankViewHolder holder) {

    }

    @Override
    protected void setData(final BankViewHolder holder, final UiBank item) {

        holder.mTitle.setText(item.getName());

        holder.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                mListener.onListItemSelected(v, item);
            }
        });


    }
}
