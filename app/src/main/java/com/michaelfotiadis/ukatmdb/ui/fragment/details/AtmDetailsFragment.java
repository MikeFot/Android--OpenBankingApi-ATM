package com.michaelfotiadis.ukatmdb.ui.fragment.details;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.michaelfotiadis.ukatmdb.R;
import com.michaelfotiadis.ukatmdb.model.AtmDetails;
import com.michaelfotiadis.ukatmdb.utils.TextUtils;
import com.michaelfotiadis.ukbankatm.ui.activity.BaseActivity;
import com.michaelfotiadis.ukbankatm.ui.fragment.BaseFragment;
import com.michaelfotiadis.ukbankatm.ui.toast.AppToast;

import java.util.Locale;

import butterknife.ButterKnife;

public class AtmDetailsFragment extends BaseFragment implements AtmDetailsView {

    private static final String ARG_DETAILS = AtmDetailsFragment.class.getSimpleName() + ".param1";

    private AtmDetailsPresenter mPresenter;
    private ServicesRecyclerController mRecyclerController;

    public AtmDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        mPresenter = new AtmDetailsPresenter(getAtmDetailsArgument(), this);
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_details, menu);

    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_map:

                showOnMap();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }

    private void showOnMap() {

        final AtmDetails atmDetails = getAtmDetailsArgument();


        final String latitude = atmDetails.getLocationLatitude();
        final String longitude = atmDetails.getLocationLongitude();
        if (TextUtils.isNotEmpty(latitude) && TextUtils.isNotEmpty(longitude)) {

            final String placeholder = "geo:%s,%s?q=%s,%s (%s)";


            final String uri = String.format(Locale.ENGLISH, placeholder, latitude, longitude, latitude, longitude, getAtmDetailsArgument().getLabel());
            final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            startActivity(intent);
        } else {
            AppToast.show(getContext(), getString(R.string.message_invalid_location_data));
        }


    }


    private AtmDetails getAtmDetailsArgument() {
        return getArguments().getParcelable(ARG_DETAILS);
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_atm_details, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        ((BaseActivity) getActivity()).setTitle(getAtmDetailsArgument().getLabel());
        ((BaseActivity) getActivity()).setDisplayHomeAsUpEnabled(true);

        mRecyclerController = new ServicesRecyclerController(view);

    }


    @Override
    public void onResume() {
        super.onResume();
        mPresenter.loadData();
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();
        mPresenter.onStop();
    }

    @Override
    public void showContent(final AtmDetails atmDetails) {

        mRecyclerController.setData(atmDetails);

    }


    public static AtmDetailsFragment newInstance(final AtmDetails atmDetails) {
        final AtmDetailsFragment fragment = new AtmDetailsFragment();
        final Bundle args = new Bundle();
        args.putParcelable(ARG_DETAILS, atmDetails);
        fragment.setArguments(args);
        return fragment;
    }

}
