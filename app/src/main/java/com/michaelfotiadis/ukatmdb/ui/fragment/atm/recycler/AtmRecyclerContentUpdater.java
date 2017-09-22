package com.michaelfotiadis.ukatmdb.ui.fragment.atm.recycler;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.michaelfotiadis.ukatmdb.model.AtmDetails;
import com.michaelfotiadis.ukatmdb.utils.AppLog;
import com.michaelfotiadis.ukatmdb.utils.ListUtils;
import com.michaelfotiadis.ukbankatm.ui.recyclerview.listener.EndlessRecyclerOnScrollListener;
import com.michaelfotiadis.ukbankatm.ui.recyclerview.manager.RecyclerManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Handler for intelligently updating a {@link RecyclerView}.
 */
public class AtmRecyclerContentUpdater {

    private static final boolean DEBUG_ENABLED = true;

    private static final int CHUNK_SIZE = 20;

    private final List<AtmDetails> mPendingItems;
    private final List<List<AtmDetails>> mListOfLists;

    private final RecyclerView mRecyclerView;
    private final RecyclerManager<AtmDetails> mRecyclerManager;
    private final RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.OnScrollListener mOnScrollListener;

    public AtmRecyclerContentUpdater(final RecyclerManager<AtmDetails> recyclerManager,
                                     final RecyclerView recyclerView,
                                     final RecyclerView.LayoutManager layoutManager) {
        mRecyclerManager = recyclerManager;

        mPendingItems = new ArrayList<>();
        mListOfLists = new ArrayList<>();

        mRecyclerView = recyclerView;
        mLayoutManager = layoutManager;



    }

    /**
     * Attempts to add a page to the adapter
     *
     * @param page number of the current page
     */
    private void addPage(final int page) {
        if (mListOfLists.isEmpty()) {
            log("No more pages to add to recycler for page " + page);
        } else {

            /*
             *  post this on the view's thread to avoid a {@link java.util.ConcurrentModificationException}
             */
            mRecyclerView.post(() -> {
                final int index = 0; // always use the first index

                mRecyclerManager.addItems(mListOfLists.get(index));
                mListOfLists.remove(index);
                log(String.format(
                        Locale.UK,
                        "On Load More page %d : Recycler now has %d items and we still have %d chunks to add",
                        page,
                        mRecyclerManager.getItemCount(),
                        mListOfLists.size()));
            });
        }
    }

    public void setItems(final List<AtmDetails> items) {

        // if we get the same number of items, it's safe to assume that the list has not changed
        if (items.isEmpty() || items.size() != mPendingItems.size()) {

            mPendingItems.clear();
            mPendingItems.addAll(items);

            log("List is different and will update with " + mPendingItems.size() + " items");

            mListOfLists.clear();
            mListOfLists.addAll(ListUtils.chunk(mPendingItems, CHUNK_SIZE));

            if (mListOfLists.isEmpty()) {
                mRecyclerManager.setItems(mPendingItems);
            } else {
                mRecyclerManager.setItems(mListOfLists.get(0));
                mListOfLists.remove(0);
            }

            if (mOnScrollListener != null) {
                mRecyclerView.removeOnScrollListener(mOnScrollListener);
            }
            mOnScrollListener = getListener();
            mRecyclerView.addOnScrollListener(mOnScrollListener);

            mRecyclerManager.updateUiState();
            log("Adapter now has " + mRecyclerManager.getItemCount() + " items and " + mPendingItems.size() + " pending items");
        } else {
            log("Got the same number of items - will not update recycler items");
        }

    }

    @NonNull
    private EndlessRecyclerOnScrollListener getListener() {
        return new EndlessRecyclerOnScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(final int page) {
                addPage(page);
            }
        };
    }

    private static void log(final String message) {

        if (DEBUG_ENABLED) {
            AppLog.d("Atm Updater: " + message);
        }

    }

}
