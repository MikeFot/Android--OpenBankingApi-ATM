package com.michaelfotiadis.ukbankatm.ui.view.holder;


import android.content.Context;
import android.view.View;

/**
 * Base View Holder class
 */
public abstract class BaseViewHolder {

    private final View mRoot;

    protected BaseViewHolder(final View view) {
        this.mRoot = view;
    }

    public View getRoot() {
        return mRoot;
    }

    public Context getContext() {
        return mRoot.getContext();
    }

}
