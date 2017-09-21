package com.michaelfotiadis.ukatmdb.ui.fragment.details.recycler.additionalservices;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.michaelfotiadis.ukatmdb.R;
import com.michaelfotiadis.ukbankatm.ui.recyclerview.viewholder.BaseRecyclerViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdditionalServicesViewHolder extends BaseRecyclerViewHolder {


    private static final int LAYOUT_ID = R.layout.list_item_additional_services;
    @BindView(R.id.image)
    ImageView accessibilityImage;
    @BindView(R.id.description)
    TextView description;

    protected AdditionalServicesViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }

    public static int getLayoutId() {
        return LAYOUT_ID;
    }
}
