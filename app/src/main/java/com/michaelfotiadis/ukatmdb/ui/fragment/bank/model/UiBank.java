package com.michaelfotiadis.ukatmdb.ui.fragment.bank.model;

import com.michaelfotiadis.ukatmdb.network.Bank;

public class UiBank {

    private final String mName;
    private final Bank mBank;

    public UiBank(final String name, final Bank bank) {
        mName = name;
        mBank = bank;
    }

    public String getName() {
        return mName;
    }

    public Bank getBank() {
        return mBank;
    }
}
