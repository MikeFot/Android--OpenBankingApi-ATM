package com.michaelfotiadis.ukatmdb.ui.fragment.bank;

import com.michaelfotiadis.ukatmdb.injection.Injector;
import com.michaelfotiadis.ukatmdb.loader.BankLoader;
import com.michaelfotiadis.ukatmdb.ui.fragment.bank.model.UiBank;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class BankPresenter {

    @Inject
    BankLoader mBankLoader;

    private Single<List<UiBank>> mSingle;

    private final BankView mView;

    public BankPresenter(final BankView view) {
        Injector.getComponentStore().getAndroidAwareComponent().inject(this);
        mView = view;
    }

    protected void onStart() {
        mSingle = mBankLoader.getUiBanks();
    }

    protected void onStop() {
        mSingle.unsubscribeOn(AndroidSchedulers.mainThread());
    }

    public void loadData() {

        mSingle.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<UiBank>>() {
                    @Override
                    public void accept(final List<UiBank> uiBanks) throws Exception {
                        mView.showContent(uiBanks);
                    }
                });

    }
}
