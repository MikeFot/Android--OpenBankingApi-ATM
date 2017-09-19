package com.michaelfotiadis.ukatmdb.ui.fragment.atm.recycler;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;


import com.michaelfotiadis.ukatmdb.R;
import com.michaelfotiadis.ukbankatm.ui.recyclerview.viewholder.BaseRecyclerViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AtmOverviewViewHolder extends BaseRecyclerViewHolder {

    @LayoutRes
    private static final int LAYOUT_ID = R.layout.list_item_atm_overview;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.summary)
    TextView mSummary;
    @BindView(R.id.league_id)
    TextView mLeagueId;
    @BindView(R.id.league_url_content)
    TextView mLeaguerUrl;
    @BindView(R.id.parent_layout)
    CardView mCardView;

    protected AtmOverviewViewHolder(final View view) {
        super(view);
        ButterKnife.bind(this, view);
    }

    public static int getLayoutId() {
        return LAYOUT_ID;
    }
}
