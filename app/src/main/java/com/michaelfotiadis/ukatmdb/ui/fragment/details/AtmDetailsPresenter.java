package com.michaelfotiadis.ukatmdb.ui.fragment.details;

import com.michaelfotiadis.ukatmdb.model.AtmDetails;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class AtmDetailsPresenter {

    private final AtmDetails mAtmDetails;
    private final AtmDetailsView mView;

    public AtmDetailsPresenter(final AtmDetails atmDetails, final AtmDetailsView view) {


        mAtmDetails = atmDetails;
        mView = view;
    }


    protected void onStart() {

    }

    protected void onStop() {

    }

    public void loadData() {

        mView.showContent(mAtmDetails);

    }
}
