package com.michaelfotiadis.ukatmdb.ui.fragment.atm;

import com.michaelfotiadis.ukatmdb.model.AtmDetails;

import java.util.List;

interface AtmOverviewView {


    void showProgress();

    void showContent(List<AtmDetails> atmDetails);

    void showError(Throwable t);

}
