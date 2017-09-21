package com.michaelfotiadis.ukatmdb.ui.fragment.details.recycler.generalinfo;

import android.content.Context;
import android.text.Html;

import com.michaelfotiadis.ukatmdb.utils.TextUtils;
import com.michaelfotiadis.ukbankatm.ui.recyclerview.viewbinder.BaseRecyclerViewBinder;

public class GeneralInfoViewBinder extends BaseRecyclerViewBinder<GeneralInfoViewHolder, String> {

    protected GeneralInfoViewBinder(final Context context) {
        super(context);
    }

    @Override
    protected void reset(final GeneralInfoViewHolder holder) {
        holder.content.setText(null);
        showView(holder.content, false);
    }

    @Override
    protected void setData(final GeneralInfoViewHolder holder, final String item) {

        if (TextUtils.isNotEmpty(item)) {
            holder.content.setText(Html.fromHtml(item));
            showView(holder.content, true);
        } else {
            showView(holder.content, false);
        }

    }
}
