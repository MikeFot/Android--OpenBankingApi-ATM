package com.michaelfotiadis.ukbankatm.ui.viewmanagement;

import android.view.View;

import com.michaelfotiadis.ukbankatm.ui.error.errorpage.QuoteOnClickListenerWrapper;


/**
 *
 */
public interface UiStateKeeper {

    View getContentView();

    View getEmptyView();

    View getErrorView();

    View getProgressView();

    void showContent();

    void showEmpty();

    void showEmpty(CharSequence message);

    void showError(final CharSequence message);

    void showError(final CharSequence message, final QuoteOnClickListenerWrapper listenerWrapper);

    void showProgress();
}
