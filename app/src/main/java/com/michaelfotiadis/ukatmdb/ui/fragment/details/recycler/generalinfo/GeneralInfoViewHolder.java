package com.michaelfotiadis.ukatmdb.ui.fragment.details.recycler.generalinfo;

import android.view.View;
import android.widget.TextView;

import com.michaelfotiadis.ukatmdb.R;
import com.michaelfotiadis.ukbankatm.ui.recyclerview.viewholder.BaseRecyclerViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GeneralInfoViewHolder extends BaseRecyclerViewHolder {

    private static final int LAYOUT_ID = R.layout.list_item_general_info;
    @BindView(R.id.content)
    TextView content;

    protected GeneralInfoViewHolder(final View view) {
        super(view);
        ButterKnife.bind(this, view);
    }

    public static int getLayoutId() {
        return LAYOUT_ID;
    }
}
