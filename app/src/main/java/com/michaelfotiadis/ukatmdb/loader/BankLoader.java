package com.michaelfotiadis.ukatmdb.loader;

import android.content.res.Resources;
import android.support.annotation.Nullable;

import com.michaelfotiadis.ukatmdb.R;
import com.michaelfotiadis.ukatmdb.network.Bank;
import com.michaelfotiadis.ukatmdb.ui.fragment.bank.model.UiBank;
import com.michaelfotiadis.ukatmdb.utils.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class BankLoader {


    public BankLoader(final Resources resources) {
        mResources = resources;
    }

    private final Resources mResources;


    public Single<List<UiBank>> getUiBanks() {

        return getBanks()
                .subscribeOn(Schedulers.io())
                .map(new Function<List<Bank>, List<UiBank>>() {
                    @Override
                    public List<UiBank> apply(@NonNull final List<Bank> banks) throws Exception {

                        final List<UiBank> uiBanks = new ArrayList<>();

                        for (final Bank bank : banks) {

                            final String name = getNameForBank(bank, mResources);

                            if (TextUtils.isNotEmpty(name)) {
                                uiBanks.add(new UiBank(name, bank));
                            }

                        }

                        return uiBanks;
                    }
                });


    }

    @Nullable
    public static String getNameForBank(final Bank bank, final Resources resources) {
        final String name;
        switch (bank) {
            case BANK_OF_IRELAND:
                name = resources.getString(R.string.bank_of_ireland);
                break;
            case BANK_OF_SCOTLAND:
                name = resources.getString(R.string.bank_of_scotland);
                break;
            case BARCLAYS:
                name = resources.getString(R.string.bank_barclays);
                break;
            case DANSKE:
                name = resources.getString(R.string.bank_danske);
                break;
            case FIRST_TRUST:
                name = resources.getString(R.string.first_trust);
                break;
            case HALIFAX:
                name = resources.getString(R.string.halifax);
                break;
            case HSBC:
                name = resources.getString(R.string.hsbc);
                break;
            case NATIONWIDE:
                name = resources.getString(R.string.nationwide);
                break;
            case NATWEST:
                name = resources.getString(R.string.natwest);
                break;
            case ROYAL_BANK_OF_SCOTLAND:
                name = resources.getString(R.string.rbos);
                break;
            case SANTANDER:
                name = resources.getString(R.string.santander);
                break;
            case ULSTER:
                name = resources.getString(R.string.ulster);
                break;
            default:
                name = null;
        }
        return name;
    }

    private Single<List<Bank>> getBanks() {
        return Single.create(new SingleOnSubscribe<List<Bank>>() {
            @Override
            public void subscribe(@NonNull final SingleEmitter<List<Bank>> e) throws Exception {
                e.onSuccess(Arrays.asList(Bank.values()));
            }
        });
    }


}
