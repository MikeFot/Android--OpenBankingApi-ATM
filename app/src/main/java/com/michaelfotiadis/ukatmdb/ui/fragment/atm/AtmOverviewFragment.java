package com.michaelfotiadis.ukatmdb.ui.fragment.atm;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

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
import com.michaelfotiadis.ukatmdb.utils.SearchUtils;
import com.michaelfotiadis.ukatmdb.utils.TextUtils;
import com.michaelfotiadis.ukbankatm.ui.activity.BaseActivity;
import com.michaelfotiadis.ukbankatm.ui.fragment.BaseRecyclerFragment;
import com.michaelfotiadis.ukbankatm.ui.recyclerview.manager.RecyclerManager;
import com.michaelfotiadis.ukbankatm.ui.recyclerview.manager.State;
import com.michaelfotiadis.ukbankatm.ui.viewmanagement.SimpleUiStateKeeper;
import com.michaelfotiadis.ukbankatm.ui.viewmanagement.UiStateKeeper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;

public class AtmOverviewFragment extends BaseRecyclerFragment<AtmDetails> implements AtmOverviewView {

    private static final String ARG_BANK = AtmOverviewFragment.class.getSimpleName() + ".param.bank";
    private static final String ARG_OUTSTATE = AtmOverviewFragment.class.getSimpleName() + ".outstate";

    protected RecyclerManager<AtmDetails> mRecyclerManager;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @Inject
    Gson mGson;
    private AtmOverviewPresenter mBankPresenter;
    private AtmRecyclerContentUpdater mContentUpdater;
    private List<AtmDetails> mItems;
    private DisposableObserver<SearchViewQueryTextEvent> mSearchObservable;

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
        if (mSearchObservable != null) {
            mSearchObservable.dispose();
        }
        writePreferences(Collections.<AtmDetails>emptyList());

    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {

        inflater.inflate(com.michaelfotiadis.ukbankatm.ui.R.menu.menu_search, menu);

        final MenuItem searchMenu = menu.findItem(com.michaelfotiadis.ukbankatm.ui.R.id.action_search);

        // Initialise the search view
        final AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity.getSupportActionBar() == null) {
            return;
        }

        final SearchView searchView = new SearchView(activity.getSupportActionBar().getThemedContext());
        searchMenu.setShowAsAction(MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
        searchMenu.setActionView(searchView);

        // known 26.0.0-alpha1 BUG!
      /*  // Add an expand listener
        searchMenu.setOnActionExpandListener(
                new MenuItem.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionExpand(MenuItem menuItem) {
                        // Return true to allow the action view to expand
                        searchView.setVisibility(View.VISIBLE);
                        return true;
                    }
                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                        // When the action view is collapsed, reset the filterItems
                        searchView.setVisibility(View.INVISIBLE);
                        // Return true to allow the action view to collapse
                        return true;
                    }
                });*/

        mSearchObservable = RxSearchView.queryTextChangeEvents(searchView)
                .debounce(400, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(getSearchObserver());

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

    public static AtmOverviewFragment newInstance(final Bank bank) {
        final AtmOverviewFragment fragment = new AtmOverviewFragment();
        final Bundle args = new Bundle();
        args.putString(ARG_BANK, bank.toString());
        fragment.setArguments(args);
        return fragment;
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

    private void filterItems(final String query) {

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
