package com.michaelfotiadis.ukbankatm.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.michaelfotiadis.ukbankatm.ui.R;
import com.michaelfotiadis.ukbankatm.ui.activity.BaseActivity;
import com.michaelfotiadis.ukbankatm.ui.toast.ActivityNotificationController;

public abstract class BaseFragment extends Fragment {

    @Override
    public void onAttach(final Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    protected void hideKeyboard() {
        // Check if no view has focus:
        final View view = getActivity().getCurrentFocus();
        if (view != null) {
            final InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    protected void showNoNetworkMessage() {

        getNotificationController().showAlert(
                R.string.toast_no_network,
                R.string.label_no_network_action,
                new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
                    }
                });

    }

    protected ActivityNotificationController getNotificationController() {
        return ((BaseActivity) getActivity()).getNotificationController();
    }

}
