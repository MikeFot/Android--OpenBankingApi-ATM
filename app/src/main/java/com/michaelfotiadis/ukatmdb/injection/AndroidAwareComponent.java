package com.michaelfotiadis.ukatmdb.injection;

import com.michaelfotiadis.ukatmdb.ui.fragment.bank.BankPresenter;
import com.michaelfotiadis.ukatmdb.ui.fragment.overview.AtmOverviewFragment;
import com.michaelfotiadis.ukatmdb.ui.fragment.overview.AtmOverviewPresenter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class})
public interface AndroidAwareComponent {

    void inject(AtmOverviewPresenter atmPresenter);

    void inject(BankPresenter bankPresenter);

    void inject(AtmOverviewFragment atmOverviewFragment);
}
