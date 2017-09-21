package com.michaelfotiadis.ukatmdb.ui.fragment.details.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.michaelfotiadis.ukatmdb.R;
import com.michaelfotiadis.ukbankatm.ui.recyclerview.viewholder.BaseRecyclerViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AtmDetailsHolder extends BaseRecyclerViewHolder {


    @BindView(R.id.content)
    TextView mContent;
    @BindView(R.id.recycler_services)
    RecyclerView mServicesRecycler;

    public AtmDetailsHolder(final View view) {
        super(view);
        ButterKnife.bind(this, view);
    }

}
