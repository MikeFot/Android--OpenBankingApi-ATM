package com.michaelfotiadis.ukatmdb.ui.fragment.atm;

import com.michaelfotiadis.ukatmdb.injection.Injector;
import com.michaelfotiadis.ukatmdb.model.AtmDetails;
import com.michaelfotiadis.ukatmdb.network.Bank;
import com.michaelfotiadis.ukatmdb.network.NetworkLoader;
import com.michaelfotiadis.ukatmdb.network.model.AtmResponse;
import com.michaelfotiadis.ukatmdb.network.model.Datum;
import com.michaelfotiadis.ukatmdb.utils.AppLog;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class AtmOverviewPresenter {
    private Observable<AtmResponse> mObservable;
    private final AtmOverviewView mView;
    private final Bank mBank;

    @Inject
    NetworkLoader mNetworkLoader;

    /*package*/  AtmOverviewPresenter(final Bank bank, final AtmOverviewView mainView) {

        Injector.getComponentStore().getAndroidAwareComponent().inject(this);

        mBank = bank;
        mView = mainView;

    }

    protected void loadData() {


        mObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .debounce(200, TimeUnit.MILLISECONDS)
                .cache()
                .map(new Function<AtmResponse, List<AtmDetails>>() {
                    @Override
                    public List<AtmDetails> apply(@NonNull final AtmResponse atmResponse) throws Exception {

                        final List<AtmDetails> details = new ArrayList<>();

                        for (final Datum datum : atmResponse.getData()) {
                            details.add(new AtmDetails(datum));
                        }


                        return details;
                    }
                })
                .subscribe(new Observer<List<AtmDetails>>() {
                    @Override
                    public void onSubscribe(@NonNull final Disposable d) {
                        mView.showProgress();
                    }

                    @Override
                    public void onNext(@NonNull final List<AtmDetails> details) {
                        AppLog.d("Received " + details.size() + " details");
                        mView.showContent(details);

                    }

                    @Override
                    public void onError(@NonNull final Throwable e) {

                        mView.showError(e);

                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }


    protected void onStart() {
        mObservable = mNetworkLoader.getForBank(mBank);
    }

    protected void onStop() {
        mObservable.unsubscribeOn(AndroidSchedulers.mainThread());
    }

}
