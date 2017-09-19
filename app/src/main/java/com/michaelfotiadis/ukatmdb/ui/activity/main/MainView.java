package com.michaelfotiadis.ukatmdb.ui.activity.main;

import com.michaelfotiadis.ukatmdb.model.AtmDetails;
import com.michaelfotiadis.ukatmdb.network.Bank;

public interface MainView {

    void showBankScreen(Bank bank);

    void showSelectionScreen();

    void showDetailsScreen(AtmDetails atmDetails);

}
