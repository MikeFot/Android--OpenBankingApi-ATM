package com.michaelfotiadis.ukbankatm.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.michaelfotiadis.ukbankatm.ui.R;
import com.michaelfotiadis.ukbankatm.ui.error.errorpage.QuoteOnClickListenerWrapper;
import com.michaelfotiadis.ukbankatm.ui.recyclerview.manager.RecyclerManager;


public abstract class BaseRecyclerFragment<D> extends BaseFragment {

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (this instanceof Searchable) {
            setHasOptionsMenu(true);
        }
    }


    protected abstract RecyclerManager<D> getRecyclerManager();

    protected abstract RecyclerView getRecyclerView();

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_default_recycler, container, false);
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {

        if (isSearchable()) {
            inflater.inflate(R.menu.menu_search, menu);

            final MenuItem searchMenu = menu.findItem(R.id.action_search);

            // Initialise the search view
            final AppCompatActivity activity = (AppCompatActivity) getActivity();
            if (activity.getSupportActionBar() == null) {
                return;
            }

            final SearchView searchView = new SearchView(activity.getSupportActionBar().getThemedContext());
            searchMenu.setShowAsAction(MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
            searchMenu.setActionView(searchView);
            // Add a OnQueryTextListener
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(final String query) {
                    getRecyclerView().getItemAnimator().isRunning(new RecyclerView.ItemAnimator.ItemAnimatorFinishedListener() {
                        @Override
                        public void onAnimationsFinished() {
                            ((Searchable) BaseRecyclerFragment.this).submitQuery(query);
                        }
                    });

                    return true;
                }

                @Override
                public boolean onQueryTextChange(final String searchText) {
                    getRecyclerView().getItemAnimator().isRunning(new RecyclerView.ItemAnimator.ItemAnimatorFinishedListener() {
                        @Override
                        public void onAnimationsFinished() {
                            ((Searchable) BaseRecyclerFragment.this).submitQuery(searchText);
                        }
                    });
                    return true;
                }
            });

            // Add an expand listener
            searchMenu.setOnActionExpandListener(new DefaultOnActionExpandListener());
        }


    }

    protected void setRecyclerError(final String errorMessage, final boolean isRecoverable) {

        if (isRecoverable) {
            getRecyclerManager().setError(errorMessage,
                    new QuoteOnClickListenerWrapper(R.string.label_try_again, new View.OnClickListener() {
                        @Override
                        public void onClick(final View v) {
                            loadData();
                        }
                    }));
        } else {
            getRecyclerManager().setError(errorMessage);
        }


    }

    protected abstract void initRecyclerManager(final View view);

    protected abstract void loadData();

    private boolean isSearchable() {
        return this instanceof Searchable;
    }


    public static class DefaultOnActionExpandListener implements MenuItem.OnActionExpandListener {
        @Override
        public boolean onMenuItemActionExpand(final MenuItem item) {
            return true; // Return true to expand action view
        }

        @Override
        public boolean onMenuItemActionCollapse(final MenuItem item) {
            return true; // Return true to collapse action view
        }
    }

}
