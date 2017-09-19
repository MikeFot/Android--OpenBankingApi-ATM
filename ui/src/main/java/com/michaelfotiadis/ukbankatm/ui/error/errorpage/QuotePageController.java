package com.michaelfotiadis.ukbankatm.ui.error.errorpage;

/**
 *
 */
public interface QuotePageController {
    void display(final CharSequence message);

    void display(CharSequence message, QuoteOnClickListenerWrapper listenerWrapper);
}
