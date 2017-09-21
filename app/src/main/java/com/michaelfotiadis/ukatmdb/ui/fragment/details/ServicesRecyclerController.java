package com.michaelfotiadis.ukatmdb.ui.fragment.details;

import android.content.res.Resources;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.michaelfotiadis.ukatmdb.R;
import com.michaelfotiadis.ukatmdb.model.AtmDetails;
import com.michaelfotiadis.ukatmdb.ui.fragment.details.recycler.additionalservices.AdditionalServices;
import com.michaelfotiadis.ukatmdb.ui.fragment.details.recycler.additionalservices.AdditionalServicesRecyclerAdapter;
import com.michaelfotiadis.ukatmdb.ui.fragment.details.recycler.generalinfo.GeneralInfoRecyclerAdapter;
import com.michaelfotiadis.ukatmdb.utils.ListUtils;
import com.michaelfotiadis.ukatmdb.utils.TextUtils;
import com.michaelfotiadis.ukbankatm.ui.recyclerview.manager.RecyclerManager;
import com.michaelfotiadis.ukbankatm.ui.utils.ViewUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Dirty controller class for fast results! Forgive me :P
 */
public class ServicesRecyclerController {

    @BindView(R.id.recycler_info)
    RecyclerView mInfoRecycler;
    @BindView(R.id.recycler_services)
    RecyclerView mServicesRecycler;
    @BindView(R.id.recycler_additonal_services)
    RecyclerView mAdditionalServicesRecycler;
    @BindView(R.id.recycler_accessibility_types)
    RecyclerView mAccessibilityRecycler;
    @BindView(R.id.recycler_currency)
    RecyclerView mCurrencyRecycler;
    @BindView(R.id.recycler_languages)
    RecyclerView mLanguagesRecycler;

    private RecyclerManager<String> mInfoManager;
    private RecyclerManager<AdditionalServices> mServicesManager;
    private RecyclerManager<AdditionalServices> mAdditionalServicesManager;
    private RecyclerManager<AdditionalServices> mAccessibilityManager;
    private RecyclerManager<AdditionalServices> mCurrencyManager;
    private RecyclerManager<AdditionalServices> mLanguagesManager;

    ServicesRecyclerController(final View view) {
        ButterKnife.bind(this, view);

        initInfoRecycler(view);
        initServicesRecycler(view);
        initAdditionalServicesRecycler(view);
        initAccessibilityRecycler(view);
        initCurrencyRecycler(view);
        initLanguagesRecycler(view);

    }

    protected void setData(final AtmDetails atmDetails) {

        setInfo(atmDetails, mInfoRecycler, mInfoManager);

        setServices(atmDetails.getAtmServices(), mServicesRecycler, mServicesManager);
        setServices(atmDetails.getAdditionalATMServices(), mAdditionalServicesRecycler, mAdditionalServicesManager);
        setServices(atmDetails.getAccessibilityTypes(), mAccessibilityRecycler, mAccessibilityManager);
        setServices(atmDetails.getCurrency(), mCurrencyRecycler, mCurrencyManager);
        setServices(atmDetails.getSupportedLanguages(), mLanguagesRecycler, mLanguagesManager);

    }

    private void setInfo(final AtmDetails atmDetails,
                         final RecyclerView recyclerView,
                         final RecyclerManager<String> recyclerManager) {

        final Resources resources = recyclerView.getResources();

        final List<String> items = new ArrayList<>();

        if (TextUtils.isNotEmpty(atmDetails.getLabel())) {
            items.add(String.format(resources.getString(R.string.placeholder_label), atmDetails.getLabel()));
        }
        if (TextUtils.isNotEmpty(atmDetails.getAddressBuildingNumberOrName())) {
            items.add(String.format(resources.getString(R.string.placeholder_address_building_number), atmDetails.getAddressBuildingNumberOrName()));
        }
        if (TextUtils.isNotEmpty(atmDetails.getAddressStreetName())) {
            items.add(String.format(resources.getString(R.string.placeholder_address_street_name), atmDetails.getAddressStreetName()));
        }
        if (TextUtils.isNotEmpty(atmDetails.getAddressTownName())) {
            items.add(String.format(resources.getString(R.string.placeholder_address_town), atmDetails.getAddressTownName()));
        }
        if (TextUtils.isNotEmpty(atmDetails.getAddressPostCode())) {
            items.add(String.format(resources.getString(R.string.placeholder_address_postcode), atmDetails.getAddressPostCode()));
        }

        if (TextUtils.isNotEmpty(atmDetails.getMinimumValueDispensed())) {
            items.add(String.format(resources.getString(R.string.placeholder_minimum_value), atmDetails.getMinimumValueDispensed()));
        }

        recyclerManager.setItems(items);
    }

    private void initInfoRecycler(final View view) {

        mInfoRecycler.setHasFixedSize(true);
        mInfoRecycler.setLayoutManager(new LinearLayoutManager(view.getContext()));

        final GeneralInfoRecyclerAdapter adapter = new GeneralInfoRecyclerAdapter(view.getContext());
        mInfoManager = new RecyclerManager.Builder<>(adapter)
                .setRecycler(mInfoRecycler)
                .setEmptyMessage("Nothing to see here")
                .build();

        mInfoManager.updateUiState();

    }

    private void setServices(final List<String> services,
                             final View recyclerView,
                             final RecyclerManager<AdditionalServices> recyclerManager) {

        final List<AdditionalServices> additionalServices = new ArrayList<>();

        if (ListUtils.isListNotNullOrEmpty(services)) {

            for (final String atmService : services) {
                additionalServices.add(new AdditionalServices(atmService));
            }

        }

        if (additionalServices.isEmpty()) {
            ViewUtils.showView(recyclerView, false);
        } else {
            recyclerManager.setItems(additionalServices);
            ViewUtils.showViewAnimated(recyclerView, true);
        }

    }

    private void initServicesRecycler(final View view) {
        mServicesRecycler.setHasFixedSize(true);
        mServicesRecycler.setLayoutManager(new GridLayoutManager(view.getContext(), 2));

        final AdditionalServicesRecyclerAdapter adapter = new AdditionalServicesRecyclerAdapter(view.getContext());
        mServicesManager = new RecyclerManager.Builder<>(adapter)
                .setRecycler(mServicesRecycler)
                .setEmptyMessage("Nothing to see here")
                .build();

        mServicesManager.updateUiState();
    }

    private void initAdditionalServicesRecycler(final View view) {
        mAdditionalServicesRecycler.setHasFixedSize(true);
        mAdditionalServicesRecycler.setLayoutManager(new GridLayoutManager(view.getContext(), 2));

        final AdditionalServicesRecyclerAdapter adapter = new AdditionalServicesRecyclerAdapter(view.getContext());
        mAdditionalServicesManager = new RecyclerManager.Builder<>(adapter)
                .setRecycler(mAdditionalServicesRecycler)
                .setEmptyMessage("Nothing to see here")
                .build();

        mAdditionalServicesManager.updateUiState();
    }

    private void initAccessibilityRecycler(final View view) {
        mAccessibilityRecycler.setHasFixedSize(true);
        mAccessibilityRecycler.setLayoutManager(new GridLayoutManager(view.getContext(), 2));

        final AdditionalServicesRecyclerAdapter adapter = new AdditionalServicesRecyclerAdapter(view.getContext());
        mAccessibilityManager = new RecyclerManager.Builder<>(adapter)
                .setRecycler(mAccessibilityRecycler)
                .setEmptyMessage("Nothing to see here")
                .build();

        mAccessibilityManager.updateUiState();
    }

    private void initCurrencyRecycler(final View view) {
        mCurrencyRecycler.setHasFixedSize(true);
        mCurrencyRecycler.setLayoutManager(new GridLayoutManager(view.getContext(), 2));

        final AdditionalServicesRecyclerAdapter adapter = new AdditionalServicesRecyclerAdapter(view.getContext());
        mCurrencyManager = new RecyclerManager.Builder<>(adapter)
                .setRecycler(mCurrencyRecycler)
                .setEmptyMessage("Nothing to see here")
                .build();

        mCurrencyManager.updateUiState();
    }

    private void initLanguagesRecycler(final View view) {
        mLanguagesRecycler.setHasFixedSize(true);
        mLanguagesRecycler.setLayoutManager(new GridLayoutManager(view.getContext(), 2));

        final AdditionalServicesRecyclerAdapter adapter = new AdditionalServicesRecyclerAdapter(view.getContext());
        mLanguagesManager = new RecyclerManager.Builder<>(adapter)
                .setRecycler(mLanguagesRecycler)
                .setEmptyMessage("Nothing to see here")
                .build();

        mLanguagesManager.updateUiState();
    }

}
