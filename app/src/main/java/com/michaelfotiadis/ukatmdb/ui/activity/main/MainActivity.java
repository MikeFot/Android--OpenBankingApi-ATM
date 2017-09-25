package com.michaelfotiadis.ukatmdb.ui.activity.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

import com.michaelfotiadis.ukatmdb.R;
import com.michaelfotiadis.ukatmdb.model.AtmDetails;
import com.michaelfotiadis.ukatmdb.network.Bank;
import com.michaelfotiadis.ukatmdb.preferences.UserPreferences;
import com.michaelfotiadis.ukatmdb.ui.fragment.bank.BankFragment;
import com.michaelfotiadis.ukatmdb.ui.fragment.details.AtmDetailsFragment;
import com.michaelfotiadis.ukatmdb.ui.fragment.overview.AtmOverviewFragment;
import com.michaelfotiadis.ukbankatm.ui.activity.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements MainView {

    private static final int CONTENT_ID = R.id.content_frame;
    private static final String FRAGMENT_TAG = "fragment.tag";

    @BindView(R.id.root)
    ViewGroup mRootView;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);

        showSelectionScreen();
        final Bank bank = new UserPreferences(this).getUserSelectedBank();
        if (bank != null && bank != Bank.UNDEFINED) {
            showBankScreen(bank);
        }

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_container;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    @Override
    public void showBankScreen(final Bank bank) {

        if (bank == Bank.UNDEFINED) {
            throw new IllegalStateException("Undefined bank");
        }

        final FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        final Fragment fragment = AtmOverviewFragment.newInstance(bank);

        fragmentTransaction.setCustomAnimations(R.anim.slide_in_right_chrome, R.anim.fade_out);

        fragmentTransaction.replace(CONTENT_ID, fragment, FRAGMENT_TAG);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    @Override
    public void showSelectionScreen() {
        addContentFragmentIfMissing(BankFragment.newInstance(), CONTENT_ID, FRAGMENT_TAG);
    }

    @Override
    public void showDetailsScreen(final AtmDetails details) {
        final FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        final Fragment fragment = AtmDetailsFragment.newInstance(details);
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_right_chrome, R.anim.slide_out_left_chrome, R.anim.slide_in_left_chrome, R.anim.slide_out_right_chrome);
        fragmentTransaction.replace(CONTENT_ID, fragment, FRAGMENT_TAG);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {

        final Fragment fragment = getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);

        if (fragment != null && fragment instanceof AtmOverviewFragment) {
            new UserPreferences(this).writeUserSelectedBank(Bank.UNDEFINED);
        }

        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}
