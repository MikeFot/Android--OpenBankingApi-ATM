package com.michaelfotiadis.ukatmdb.injection;

import com.michaelfotiadis.ukatmdb.ui.fragment.atm.AtmOverviewFragment;
import com.michaelfotiadis.ukatmdb.ui.fragment.atm.AtmOverviewPresenter;
import com.michaelfotiadis.ukatmdb.ui.fragment.bank.BankPresenter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class})
public interface AndroidAwareComponent {

    void inject(AtmOverviewPresenter atmPresenter);

    void inject(BankPresenter bankPresenter);

    void inject(AtmOverviewFragment atmOverviewFragment);
}
