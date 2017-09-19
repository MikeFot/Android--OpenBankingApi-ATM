package com.michaelfotiadis.ukatmdb.ui.fragment.bank;

import com.michaelfotiadis.ukatmdb.ui.fragment.bank.model.UiBank;

import java.util.List;

public interface BankView {
    void showProgress();

    void showContent(List<UiBank> uiBanks);

    void showError(Throwable t);
}
