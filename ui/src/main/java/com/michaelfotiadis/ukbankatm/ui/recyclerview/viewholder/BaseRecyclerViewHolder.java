package com.michaelfotiadis.ukbankatm.ui.recyclerview.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class BaseRecyclerViewHolder extends RecyclerView.ViewHolder {

    private final View mRoot;

    protected BaseRecyclerViewHolder(final View view) {
        super(view);
        mRoot = view;
    }

    public View getRoot() {
        return mRoot;
    }

}