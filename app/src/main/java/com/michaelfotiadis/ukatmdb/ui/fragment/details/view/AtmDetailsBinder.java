package com.michaelfotiadis.ukatmdb.ui.fragment.details.view;

import android.content.Context;
import android.view.View;

import com.google.gson.GsonBuilder;
import com.michaelfotiadis.ukatmdb.model.AtmDetails;
import com.michaelfotiadis.ukbankatm.ui.recyclerview.listener.OnItemSelectedListener;
import com.michaelfotiadis.ukbankatm.ui.recyclerview.viewbinder.BaseRecyclerViewBinder;

public class AtmDetailsBinder extends BaseRecyclerViewBinder<AtmDetailsHolder, AtmDetails> {

    private final OnItemSelectedListener<AtmDetails> mListener;


    public AtmDetailsBinder(final Context context, final OnItemSelectedListener<AtmDetails> listener) {
        super(context);
        mListener = listener;
    }

    @Override
    public void reset(final AtmDetailsHolder holder) {

        holder.mContent.setText("");
        holder.getRoot().setOnClickListener(null);

    }

    @Override
    public void setData(final AtmDetailsHolder holder, final AtmDetails item) {

        holder.mContent.setText(new GsonBuilder().setPrettyPrinting().create().toJson(item));


        holder.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                mListener.onListItemSelected(v, item);
            }
        });


    }
}
