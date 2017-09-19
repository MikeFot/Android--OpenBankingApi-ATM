package com.michaelfotiadis.ukatmdb.ui.fragment.atm;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.michaelfotiadis.ukatmdb.R;
import com.michaelfotiadis.ukatmdb.injection.Injector;
import com.michaelfotiadis.ukatmdb.loader.BankLoader;
import com.michaelfotiadis.ukatmdb.model.AtmDetails;
import com.michaelfotiadis.ukatmdb.network.Bank;
import com.michaelfotiadis.ukatmdb.preferences.UserPreferences;
import com.michaelfotiadis.ukatmdb.ui.activity.main.MainView;
import com.michaelfotiadis.ukatmdb.ui.fragment.atm.recycler.AtmRecyclerAdapter;
import com.michaelfotiadis.ukatmdb.ui.fragment.atm.recycler.AtmRecyclerContentUpdater;
import com.michaelfotiadis.ukatmdb.utils.AppLog;
import com.michaelfotiadis.ukatmdb.utils.TextUtils;
import com.michaelfotiadis.ukbankatm.ui.activity.BaseActivity;
import com.michaelfotiadis.ukbankatm.ui.fragment.BaseRecyclerFragment;
import com.michaelfotiadis.ukbankatm.ui.recyclerview.listener.OnItemSelectedListener;
import com.michaelfotiadis.ukbankatm.ui.recyclerview.manager.RecyclerManager;
import com.michaelfotiadis.ukbankatm.ui.recyclerview.manager.State;
import com.michaelfotiadis.ukbankatm.ui.viewmanagement.SimpleUiStateKeeper;
import com.michaelfotiadis.ukbankatm.ui.viewmanagement.UiStateKeeper;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AtmOverviewFragment extends BaseRecyclerFragment<AtmDetails> implements AtmOverviewView {

    private static final String ARG_BANK = AtmOverviewFragment.class.getSimpleName() + ".param1";
    private static final String ARG_OUTSTATE = AtmOverviewFragment.class.getSimpleName() + ".outstate";
    protected RecyclerManager<AtmDetails> mRecyclerManager;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @Inject
    Gson mGson;
    private AtmOverviewPresenter mBankPresenter;
    private AtmRecyclerContentUpdater mContentUpdater;


    public AtmOverviewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(final Context context) {
        super.onAttach(context);
        Injector.getComponentStore().getAndroidAwareComponent().inject(this);
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new UserPreferences(getContext()).writeUserSelectedBank(getBankArgument());

        mBankPresenter = new AtmOverviewPresenter(getBankArgument(), this);

    }

    private Bank getBankArgument() {
        return getArguments() != null ? Bank.fromString(getArguments().getString(ARG_BANK)) : Bank.UNDEFINED;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        writePreferences(Collections.<AtmDetails>emptyList());

    }

    private void writePreferences(final List<AtmDetails> atmDetails) {
        if (atmDetails == null) {
            return;
        }
        getActivity().getPreferences(Context.MODE_PRIVATE).edit().putString(ARG_OUTSTATE, mGson.toJson(atmDetails)).apply();
    }

    private List<AtmDetails> readPreferences() {
        final String payload = getActivity().getPreferences(Context.MODE_PRIVATE).getString(ARG_OUTSTATE, "");

        if (TextUtils.isNotEmpty(payload)) {
            return mGson.fromJson(payload, new TypeToken<List<AtmDetails>>(){}.getType());
        } else {
            return Collections.emptyList();
        }

    }

    @Override
    protected RecyclerManager<AtmDetails> getRecyclerManager() {
        return mRecyclerManager;
    }

    @Override
    protected RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    @Override
    protected void initRecyclerManager(final View view) {

        final UiStateKeeper uiStateKeeper = new SimpleUiStateKeeper(view, mRecyclerView);
        mRecyclerView.setHasFixedSize(true);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());

        mRecyclerView.setLayoutManager(layoutManager);

        final AtmRecyclerAdapter adapter = new AtmRecyclerAdapter(getActivity(), new OnItemSelectedListener<AtmDetails>() {
            @Override
            public void onListItemSelected(final View view, final AtmDetails item) {

                if (getActivity() instanceof MainView) {
                    ((MainView) getActivity()).showDetailsScreen(item);
                }
            }
        });

        mRecyclerManager = new RecyclerManager.Builder<>(adapter)
                .setRecycler(mRecyclerView)
                .setStateKeeper(uiStateKeeper)
                .setEmptyMessage("Nothing to see here")
                .build();


        mContentUpdater = new AtmRecyclerContentUpdater(mRecyclerManager, mRecyclerView, layoutManager);

        mRecyclerManager.updateUiState();

    }

    @Override
    protected void loadData() {
        AppLog.d("Loading Data");

        mBankPresenter.loadData();
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        final String title = BankLoader.getNameForBank(getBankArgument(), getResources());
        ((BaseActivity) getActivity()).setTitle(title);
        ((BaseActivity) getActivity()).setDisplayHomeAsUpEnabled(true);

        initRecyclerManager(view);
    }

    @Override
    public void onResume() {
        super.onResume();
        final List<AtmDetails> existingData = readPreferences();
        if (existingData.isEmpty()) {
            AppLog.d("Loading data");
            loadData();
        } else {
            AppLog.d("Restoring from preferences");
            mContentUpdater.setItems(existingData);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mBankPresenter.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mBankPresenter.onStop();
    }

    @Override
    public void showProgress() {

        if (mRecyclerManager.getItemCount() == 0) {
            mRecyclerManager.clearError();
            mRecyclerManager.updateUiState(State.PROGRESS);
        }
    }

    @Override
    public void showContent(final List<AtmDetails> atmDetails) {
        writePreferences(atmDetails);
        mContentUpdater.setItems(atmDetails);

    }

    @Override
    public void showError(final Throwable t) {

        setRecyclerError(t.getMessage(), true);

    }

    public static AtmOverviewFragment newInstance(final Bank bank) {
        final AtmOverviewFragment fragment = new AtmOverviewFragment();
        final Bundle args = new Bundle();
        args.putString(ARG_BANK, bank.toString());
        fragment.setArguments(args);
        return fragment;
    }

}
