package com.michaelfotiadis.ukatmdb.ui.fragment.bank;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.michaelfotiadis.ukatmdb.R;
import com.michaelfotiadis.ukatmdb.network.Bank;
import com.michaelfotiadis.ukatmdb.preferences.UserPreferences;
import com.michaelfotiadis.ukatmdb.ui.activity.main.MainView;
import com.michaelfotiadis.ukatmdb.ui.fragment.bank.model.UiBank;
import com.michaelfotiadis.ukatmdb.ui.fragment.bank.recycler.BankRecyclerAdapter;
import com.michaelfotiadis.ukbankatm.ui.activity.BaseActivity;
import com.michaelfotiadis.ukbankatm.ui.fragment.BaseRecyclerFragment;
import com.michaelfotiadis.ukbankatm.ui.recyclerview.listener.OnItemSelectedListener;
import com.michaelfotiadis.ukbankatm.ui.recyclerview.manager.RecyclerManager;
import com.michaelfotiadis.ukbankatm.ui.recyclerview.manager.State;
import com.michaelfotiadis.ukbankatm.ui.viewmanagement.SimpleUiStateKeeper;
import com.michaelfotiadis.ukbankatm.ui.viewmanagement.UiStateKeeper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BankFragment extends BaseRecyclerFragment<UiBank> implements BankView {

    protected RecyclerManager<UiBank> mRecyclerManager;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    private BankPresenter mBankPresenter;


    public BankFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBankPresenter = new BankPresenter(this);

    }

    @Override
    protected RecyclerManager<UiBank> getRecyclerManager() {
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

        final BankRecyclerAdapter adapter = new BankRecyclerAdapter(
                getActivity(),
                new OnItemSelectedListener<UiBank>() {
                    @Override
                    public void onListItemSelected(final View view, final UiBank item) {
                        if (getActivity() instanceof MainView) {
                            ((MainView) getActivity()).showBankScreen(item.getBank());
                        }
                    }
                });
        mRecyclerManager = new RecyclerManager.Builder<>(adapter)
                .setRecycler(mRecyclerView)
                .setStateKeeper(uiStateKeeper)
                .setEmptyMessage("Nothing to see here")
                .build();


        mRecyclerManager.updateUiState();

    }

    @Override
    protected void loadData() {
        mBankPresenter.loadData();
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        ((BaseActivity) getActivity()).setTitle(R.string.main_title);
        ((BaseActivity) getActivity()).setDisplayHomeAsUpEnabled(false);

        initRecyclerManager(view);

    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
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
    public void showContent(final List<UiBank> uiBanks) {

        mRecyclerManager.setItems(uiBanks);

    }

    @Override
    public void showError(final Throwable t) {

        setRecyclerError(t.getMessage(), true);

    }

    public static BankFragment newInstance() {
        return new BankFragment();
    }

}
