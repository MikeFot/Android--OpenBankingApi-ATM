package com.michaelfotiadis.ukbankatm.ui.recyclerview.listener;

import android.view.View;

public interface OnItemSelectedListener<T> {

    void onListItemSelected(View view, T item);

}
