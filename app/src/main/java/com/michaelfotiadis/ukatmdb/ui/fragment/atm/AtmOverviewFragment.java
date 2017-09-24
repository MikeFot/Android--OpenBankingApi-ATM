package com.michaelfotiadis.ukatmdb.ui.fragment.atm;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.location.LocationRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jakewharton.rxbinding2.support.v7.widget.RxSearchView;
import com.jakewharton.rxbinding2.support.v7.widget.SearchViewQueryTextEvent;
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
import com.michaelfotiadis.ukatmdb.utils.ListUtils;
import com.michaelfotiadis.ukatmdb.utils.SearchUtils;
import com.michaelfotiadis.ukatmdb.utils.TextUtils;
import com.michaelfotiadis.ukbankatm.ui.activity.BaseActivity;
import com.michaelfotiadis.ukbankatm.ui.fragment.BaseRecyclerFragment;
import com.michaelfotiadis.ukbankatm.ui.recyclerview.manager.RecyclerManager;
import com.michaelfotiadis.ukbankatm.ui.recyclerview.manager.State;
import com.michaelfotiadis.ukbankatm.ui.toast.AppToast;
import com.michaelfotiadis.ukbankatm.ui.viewmanagement.SimpleUiStateKeeper;
import com.michaelfotiadis.ukbankatm.ui.viewmanagement.UiStateKeeper;
import com.patloew.rxlocation.RxLocation;
import com.vistrav.ask.Ask;
import com.vistrav.ask.annotations.AskDenied;
import com.vistrav.ask.annotations.AskGranted;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class AtmOverviewFragment extends BaseRecyclerFragment<AtmDetails> implements AtmOverviewView {

    private static final String ARG_BANK = AtmOverviewFragment.class.getSimpleName() + ".param.bank";
    private static final String ARG_OUTSTATE = AtmOverviewFragment.class.getSimpleName() + ".outstate";
    private static final long LOCATION_UPDATE_THRESHOLD = TimeUnit.SECONDS.toMillis(5);
    private static final String REQUESTED_PERMISSION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int REQUEST_LOCATION = 504;
    protected RecyclerManager<AtmDetails> mRecyclerManager;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @Inject
    Gson mGson;
    private AtmOverviewPresenter mBankPresenter;
    private AtmRecyclerContentUpdater mContentUpdater;
    private List<AtmDetails> mItems;
    private DisposableObserver<SearchViewQueryTextEvent> mSearchDisposable;
    private Disposable mLocationDisposable;

    public AtmOverviewFragment() {
        // Required empty public constructor
    }

    public static AtmOverviewFragment newInstance(final Bank bank) {
        final AtmOverviewFragment fragment = new AtmOverviewFragment();
        final Bundle args = new Bundle();
        args.putString(ARG_BANK, bank.toString());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(final Context context) {
        super.onAttach(context);
        Injector.getComponentStore().getAndroidAwareComponent().inject(this);
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        new UserPreferences(getContext()).writeUserSelectedBank(getBankArgument());

        mBankPresenter = new AtmOverviewPresenter(getBankArgument(), this);

    }

    private Bank getBankArgument() {
        return getArguments() != null ? Bank.fromString(getArguments().getString(ARG_BANK)) : Bank.UNDEFINED;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mSearchDisposable != null) {
            mSearchDisposable.dispose();
        }
        if (mLocationDisposable != null) {
            mLocationDisposable.dispose();
        }
        writePreferences(Collections.<AtmDetails>emptyList());

    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {

        inflater.inflate(R.menu.menu_overview, menu);

        final MenuItem searchMenu = menu.findItem(com.michaelfotiadis.ukbankatm.ui.R.id.action_search);

        // Initialise the search view
        final AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity.getSupportActionBar() == null) {
            return;
        }

        final SearchView searchView = new SearchView(activity.getSupportActionBar().getThemedContext());
        searchMenu.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItem.SHOW_AS_ACTION_IF_ROOM);
        searchMenu.setActionView(searchView);

        searchMenu.setOnActionExpandListener(new DefaultOnActionExpandListener());

        mSearchDisposable = RxSearchView.queryTextChangeEvents(searchView)
                .debounce(300, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(getSearchObserver());

    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_nearest:
                askForUserLocation();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }

    private void writePreferences(final List<AtmDetails> atmDetails) {
        if (atmDetails == null) {
            return;
        }
        getActivity().getPreferences(Context.MODE_PRIVATE).edit().putString(ARG_OUTSTATE, mGson.toJson(atmDetails)).apply();
    }

    private List<AtmDetails> readPreferences() {
        final String payload = getActivity().getPreferences(Context.MODE_PRIVATE).getString(ARG_OUTSTATE, "");

        //noinspection IfMayBeConditional
        if (TextUtils.isNotEmpty(payload)) {
            //noinspection AnonymousInnerClassMayBeStatic
            return mGson.fromJson(payload, new TypeToken<List<AtmDetails>>() {
            }.getType());
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

        final AtmRecyclerAdapter adapter = new AtmRecyclerAdapter(getActivity(), (itemView, item) -> {

            if (getActivity() instanceof MainView) {
                ((MainView) getActivity()).showDetailsScreen(item);
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
            mItems = existingData;
            mContentUpdater.setItems(mItems);
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
        mItems = atmDetails;
        mContentUpdater.setItems(mItems);
    }

    @Override
    public void showError(final Throwable t) {
        setRecyclerError(t.getMessage(), true);
    }

    private DisposableObserver<SearchViewQueryTextEvent> getSearchObserver() {
        return new DisposableObserver<SearchViewQueryTextEvent>() {
            @Override
            public void onComplete() {
                AppLog.d("Search onComplete");
            }

            @Override
            public void onError(final Throwable e) {
                AppLog.e("Error searching!");
            }

            @Override
            public void onNext(final SearchViewQueryTextEvent onTextChangeEvent) {

                getRecyclerView().getItemAnimator().isRunning(() ->
                        filterItems(onTextChangeEvent.queryText().toString()));


                if (onTextChangeEvent.isSubmitted()) {
                    hideKeyboard();
                }

            }
        };
    }

    private void askForUserLocation() {
        if (ContextCompat.checkSelfPermission(getActivity(), REQUESTED_PERMISSION) == PackageManager.PERMISSION_GRANTED) {
            AppLog.d("Already have location permission");
            getUserLocation();

        } else {
            AppLog.d("Asking for location permission");
            Ask.on(getActivity())
                    .forPermissions(REQUESTED_PERMISSION)
                    .withRationales("In order to find the nearest ATM, the app needs access to your general location.")
                    .go();
        }
    }

    @AskGranted(REQUESTED_PERMISSION)
    public void locationAccessGranted(final int id) {
        getUserLocation();
    }

    @AskDenied(REQUESTED_PERMISSION)
    public void locationAccessDenied(final int id) {
        AppToast.show(getContext(), R.string.error_no_permission);
    }

    @SuppressWarnings("MissingPermission")
    private void getUserLocation() {
        AppLog.d("Got user location");
        if (mLocationDisposable != null && !mLocationDisposable.isDisposed()) {
            mLocationDisposable.dispose();
        }

        // Create one instance and share it
        final LocationRequest locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
                .setInterval(LOCATION_UPDATE_THRESHOLD);

        mLocationDisposable = new RxLocation(getContext()).location().updates(locationRequest)
                .subscribeOn(Schedulers.io())
                .flatMap(location -> new RxLocation(getContext()).geocoding().fromLocation(location).toObservable())
                .map(this::findNearestAddresses)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(details -> mContentUpdater.setItems(details));
    }

    private List<AtmDetails> findNearestAddresses(@NonNull final Address address) {
        if (address == null) {
            AppLog.e("NULL ADDRESS!!!");
            Crashlytics.logException(new Exception("Null address!"));
            return ListUtils.isListNotNullOrEmpty(mItems) ? mItems : Collections.emptyList();
        }

        AppLog.d("Got address " + address.getLatitude() + " " + address.getLongitude());
        if (ListUtils.isListNotNullOrEmpty(mItems)) {
            mContentUpdater.setItems(Collections.emptyList());

            final Map<Double, AtmDetails> tree = new TreeMap<>();

            for (final AtmDetails atm : mItems) {

                final Double lon = atm.getLongitudeAsDouble();
                final Double lat = atm.getLatitudeAsDouble();

                final double distance;
                if (lon != null && lat != null) {
                    distance = Math.hypot(lon - address.getLongitude(), lat - address.getLatitude());
                    AppLog.d("Adding " + distance + " atm " + atm.getAddressTownName());
                } else {
                    distance = Double.MAX_VALUE;
                }

                tree.put(distance, atm);
            }

            return new ArrayList<>(tree.values());
        } else {
            return ListUtils.isListNotNullOrEmpty(mItems) ? mItems : Collections.emptyList();
        }
    }

    private void filterItems(final String query) {
        // move filtering to RX observable
        if (mItems != null) {
            if (TextUtils.isEmpty(query)) {
                AppLog.d("Resetting adapter");
                mContentUpdater.setItems(mItems);
            } else {
                AppLog.d(String.format("Searching for %s", query));
                final List<AtmDetails> filteredItems = new ArrayList<>();

                for (final AtmDetails details : mItems) {

                    if (SearchUtils.isThereAMatch(details.getLabel(), query)
                            || SearchUtils.isThereAMatch(details.getSiteName(), query)
                            || SearchUtils.isThereAMatch(details.getAddressCountry(), query)
                            || SearchUtils.isThereAMatch(details.getAddressPostCode(), query)
                            || SearchUtils.isThereAMatch(details.getAddressTownName(), query)
                            || SearchUtils.isThereAMatch(details.getAddressBuildingNumberOrName(), query)
                            || SearchUtils.isThereAMatch(details.getAddressOptionalAddressField(), query)
                            || SearchUtils.isThereAMatch(details.getAddressStreetName(), query)
                            || SearchUtils.isThereAMatch(details.getSupportedLanguages(), query)
                            || SearchUtils.isThereAMatch(details.getAdditionalATMServices(), query)
                            || SearchUtils.isThereAMatch(details.getAccessibilityTypes(), query)
                            || SearchUtils.isThereAMatch(details.getAtmServices(), query)
                            || SearchUtils.isThereAMatch(details.getCurrency(), query)) {
                        filteredItems.add(details);
                    }

                }
                mContentUpdater.setItems(filteredItems);
            }
        }

    }

}
