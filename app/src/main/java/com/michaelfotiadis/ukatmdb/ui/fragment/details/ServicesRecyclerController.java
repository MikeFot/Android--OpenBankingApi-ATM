package com.michaelfotiadis.ukatmdb.ui.fragment.details;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.michaelfotiadis.ukatmdb.R;
import com.michaelfotiadis.ukatmdb.model.AtmDetails;
import com.michaelfotiadis.ukatmdb.ui.fragment.details.factory.UiAtmDetailsFactory;
import com.michaelfotiadis.ukatmdb.ui.fragment.details.recycler.additionalservices.AdditionalServices;
import com.michaelfotiadis.ukatmdb.ui.fragment.details.recycler.additionalservices.AdditionalServicesRecyclerAdapter;
import com.michaelfotiadis.ukatmdb.ui.fragment.details.recycler.generalinfo.GeneralInfoRecyclerAdapter;
import com.michaelfotiadis.ukatmdb.utils.TextUtils;
import com.michaelfotiadis.ukbankatm.ui.recyclerview.manager.RecyclerManager;
import com.michaelfotiadis.ukbankatm.ui.toast.AppToast;
import com.michaelfotiadis.ukbankatm.ui.utils.ViewUtils;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Dirty controller class for fast results! Forgive me :P
 */
public class ServicesRecyclerController {

    private final UiAtmDetailsFactory factory;
    private final View rootView;
    @BindView(R.id.recycler_info)
    RecyclerView infoRecycler;
    @BindView(R.id.recycler_services)
    RecyclerView servicesRecycler;
    @BindView(R.id.recycler_additonal_services)
    RecyclerView additionalServicesRecycler;
    @BindView(R.id.recycler_accessibility_types)
    RecyclerView accessibilityRecycler;
    @BindView(R.id.recycler_currency)
    RecyclerView currencyRecycler;
    @BindView(R.id.recycler_languages)
    RecyclerView languagesRecycler;
    @BindView(R.id.label)
    TextView labelView;
    @BindView(R.id.map_container)
    View mapContainer;


    private RecyclerManager<String> mInfoManager;
    private RecyclerManager<AdditionalServices> mServicesManager;
    private RecyclerManager<AdditionalServices> mAdditionalServicesManager;
    private RecyclerManager<AdditionalServices> mAccessibilityManager;
    private RecyclerManager<AdditionalServices> mCurrencyManager;
    private RecyclerManager<AdditionalServices> mLanguagesManager;
    private GoogleMap map;
    private AtmDetails atmDetails;

    ServicesRecyclerController(final View view) {
        ButterKnife.bind(this, view);
        this.rootView = view;

        factory = new UiAtmDetailsFactory(view.getResources());

        ViewUtils.showView(mapContainer, false);

        initInfoRecycler(view);
        initServicesRecycler(view);
        initAdditionalServicesRecycler(view);
        initAccessibilityRecycler(view);
        initCurrencyRecycler(view);
        initLanguagesRecycler(view);

    }

    protected void setData(final AtmDetails atmDetails) {

        this.atmDetails = atmDetails;

        final String label;
        if (TextUtils.isNotEmpty(atmDetails.getLabel())) {
            label = atmDetails.getLabel();
        } else if (TextUtils.isNotEmpty(atmDetails.getSiteName())) {
            label = atmDetails.getSiteName();
        } else if (TextUtils.isNotEmpty(atmDetails.getSiteID())) {
            label = atmDetails.getSiteID();
        } else {
            label = null;
        }

        if (label != null) {
            labelView.setText(label);
        } else {
            ViewUtils.showView(labelView, false);
        }

        final List<String> items = factory.getInfoItems(atmDetails);
        mInfoManager.setItems(items);

        setServices(atmDetails.getAtmServices(), servicesRecycler, mServicesManager);
        setServices(atmDetails.getAdditionalATMServices(), additionalServicesRecycler, mAdditionalServicesManager);
        setServices(atmDetails.getAccessibilityTypes(), accessibilityRecycler, mAccessibilityManager);
        setServices(atmDetails.getCurrency(), currencyRecycler, mCurrencyManager);
        setCountryServices(atmDetails.getSupportedLanguages(), languagesRecycler, mLanguagesManager);

        showInternalMapView(atmDetails.getLatitudeAsDouble(), atmDetails.getLongitudeAsDouble(), atmDetails.getLabel());

    }

    void showOnMap() {

        if (atmDetails != null) {
            final String latitude = atmDetails.getLocationLatitude();
            final String longitude = atmDetails.getLocationLongitude();
            if (TextUtils.isNotEmpty(latitude) && TextUtils.isNotEmpty(longitude)) {
                final String placeholder = "geo:%s,%s?q=%s,%s (%s)";
                final String uri = String.format(Locale.ENGLISH, placeholder, latitude, longitude, latitude, longitude, atmDetails.getLabel());
                final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                rootView.getContext().startActivity(intent);
            } else {
                AppToast.show(rootView.getContext(), rootView.getContext().getString(R.string.message_invalid_location_data));
            }
        }


    }

    private void showInternalMapView(final Double lat, final Double lon, final String label) {

        if (map != null && lat != null && lon != null) {
            final LatLng atm = new LatLng(lat, lon);
            map.addMarker(new MarkerOptions().position(atm)
                    .title(label));
            map.setIndoorEnabled(true);
            map.setBuildingsEnabled(true);
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(atm, 16.0f));
            map.getUiSettings().setAllGesturesEnabled(false);
            if (ActivityCompat.checkSelfPermission(rootView.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                map.setMyLocationEnabled(true);
            }

            map.setOnMapClickListener(latLng -> showOnMap());
        }

    }


    private void initInfoRecycler(final View view) {

        infoRecycler.setHasFixedSize(true);
        infoRecycler.setLayoutManager(new LinearLayoutManager(view.getContext()));

        final GeneralInfoRecyclerAdapter adapter = new GeneralInfoRecyclerAdapter(view.getContext());
        mInfoManager = new RecyclerManager.Builder<>(adapter)
                .setRecycler(infoRecycler)
                .setEmptyMessage("Nothing to see here")
                .build();

        mInfoManager.updateUiState();

    }

    private void setServices(final List<String> services,
                             final View recyclerView,
                             final RecyclerManager<AdditionalServices> recyclerManager) {

        final List<AdditionalServices> additionalServices = factory.getAdditionalServices(services);

        if (additionalServices.isEmpty()) {
            ViewUtils.showView(recyclerView, false);
        } else {
            recyclerManager.setItems(additionalServices);
            ViewUtils.showViewAnimated(recyclerView, true);
        }

    }


    private void setCountryServices(final List<String> services,
                                    final View recyclerView,
                                    final RecyclerManager<AdditionalServices> recyclerManager) {

        final List<AdditionalServices> additionalServices = factory.getCountryServices(services);

        if (additionalServices.isEmpty()) {
            ViewUtils.showView(recyclerView, false);
        } else {
            recyclerManager.setItems(additionalServices);
            ViewUtils.showViewAnimated(recyclerView, true);
        }

    }

    private void initServicesRecycler(final View view) {
        servicesRecycler.setHasFixedSize(true);
        servicesRecycler.setLayoutManager(new GridLayoutManager(view.getContext(), 2));

        final AdditionalServicesRecyclerAdapter adapter = new AdditionalServicesRecyclerAdapter(view.getContext());
        mServicesManager = new RecyclerManager.Builder<>(adapter)
                .setRecycler(servicesRecycler)
                .setEmptyMessage("Nothing to see here")
                .build();

        mServicesManager.updateUiState();
    }

    private void initAdditionalServicesRecycler(final View view) {
        additionalServicesRecycler.setHasFixedSize(true);
        additionalServicesRecycler.setLayoutManager(new GridLayoutManager(view.getContext(), 2));

        final AdditionalServicesRecyclerAdapter adapter = new AdditionalServicesRecyclerAdapter(view.getContext());
        mAdditionalServicesManager = new RecyclerManager.Builder<>(adapter)
                .setRecycler(additionalServicesRecycler)
                .setEmptyMessage("Nothing to see here")
                .build();

        mAdditionalServicesManager.updateUiState();
    }

    private void initAccessibilityRecycler(final View view) {
        accessibilityRecycler.setHasFixedSize(true);
        accessibilityRecycler.setLayoutManager(new GridLayoutManager(view.getContext(), 2));

        final AdditionalServicesRecyclerAdapter adapter = new AdditionalServicesRecyclerAdapter(view.getContext());
        mAccessibilityManager = new RecyclerManager.Builder<>(adapter)
                .setRecycler(accessibilityRecycler)
                .setEmptyMessage("Nothing to see here")
                .build();

        mAccessibilityManager.updateUiState();
    }

    private void initCurrencyRecycler(final View view) {
        currencyRecycler.setHasFixedSize(true);
        currencyRecycler.setLayoutManager(new GridLayoutManager(view.getContext(), 2));

        final AdditionalServicesRecyclerAdapter adapter = new AdditionalServicesRecyclerAdapter(view.getContext());
        mCurrencyManager = new RecyclerManager.Builder<>(adapter)
                .setRecycler(currencyRecycler)
                .setEmptyMessage("Nothing to see here")
                .build();

        mCurrencyManager.updateUiState();
    }

    private void initLanguagesRecycler(final View view) {
        languagesRecycler.setHasFixedSize(true);
        languagesRecycler.setLayoutManager(new GridLayoutManager(view.getContext(), 2));

        final AdditionalServicesRecyclerAdapter adapter = new AdditionalServicesRecyclerAdapter(view.getContext());
        mLanguagesManager = new RecyclerManager.Builder<>(adapter)
                .setRecycler(languagesRecycler)
                .setEmptyMessage("Nothing to see here")
                .build();

        mLanguagesManager.updateUiState();
    }

    public void setMap(final GoogleMap map) {
        this.map = map;

        ViewUtils.showViewAnimated(this.mapContainer, true);

        if (atmDetails != null) {
            showInternalMapView(atmDetails.getLatitudeAsDouble(), atmDetails.getLongitudeAsDouble(), atmDetails.getLabel());
        }
    }
}
