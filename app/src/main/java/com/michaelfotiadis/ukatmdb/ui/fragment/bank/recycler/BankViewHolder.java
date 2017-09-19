package com.michaelfotiadis.ukatmdb.ui.fragment.bank.recycler;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.michaelfotiadis.ukatmdb.R;
import com.michaelfotiadis.ukbankatm.ui.recyclerview.viewholder.BaseRecyclerViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BankViewHolder extends BaseRecyclerViewHolder {

    @LayoutRes
    private static final int LAYOUT_ID = R.layout.list_item_bank;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.parent_layout)
    ViewGroup mRoot;


    protected BankViewHolder(final View view) {
        super(view);
        ButterKnife.bind(this, view);
    }

    public static int getLayoutId() {
        return LAYOUT_ID;
    }
}
