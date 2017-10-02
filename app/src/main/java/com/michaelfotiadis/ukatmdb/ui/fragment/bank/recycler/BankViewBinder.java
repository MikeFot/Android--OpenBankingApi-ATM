package com.michaelfotiadis.ukatmdb.ui.fragment.bank.recycler;

import android.content.Context;

import com.jakewharton.rxbinding2.view.RxView;
import com.michaelfotiadis.ukatmdb.ui.fragment.bank.model.UiBank;
import com.michaelfotiadis.ukbankatm.ui.recyclerview.listener.OnItemSelectedListener;
import com.michaelfotiadis.ukbankatm.ui.recyclerview.viewbinder.BaseRecyclerViewBinder;

import java.util.concurrent.TimeUnit;

import io.reactivex.disposables.Disposable;

public class BankViewBinder extends BaseRecyclerViewBinder<BankViewHolder, UiBank> {

    private final OnItemSelectedListener<UiBank> listener;
    private Disposable disposable;


    protected BankViewBinder(final Context context, final OnItemSelectedListener<UiBank> listener) {
        super(context);
        this.listener = listener;
    }

    @Override
    protected void reset(final BankViewHolder holder) {
        holder.mTitle.setText("");
    }

    @Override
    protected void setData(final BankViewHolder holder, final UiBank item) {

        holder.mTitle.setText(item.getName());

        disposable = RxView.clicks(holder.getRoot())
                .debounce(200, TimeUnit.MILLISECONDS)
                .subscribe(click -> listener.onListItemSelected(holder.getRoot(), item));

    }

    @Override
    public void detach() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
