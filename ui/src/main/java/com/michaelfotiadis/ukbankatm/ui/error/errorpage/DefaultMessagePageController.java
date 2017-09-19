package com.michaelfotiadis.ukbankatm.ui.error.errorpage;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.michaelfotiadis.ukbankatm.ui.R;

/**
 *
 */
public class DefaultMessagePageController implements QuotePageController {

    private final Button mActionButton;
    private final TextView mErrorDescription;

    public DefaultMessagePageController(final View view) {

        mActionButton = view.findViewById(R.id.error_button);
        mErrorDescription = view.findViewById(R.id.error_message);

    }

    @Override
    public void display(final CharSequence message) {
        display(message, null);
    }

    @Override
    public void display(final CharSequence message, final QuoteOnClickListenerWrapper listenerWrapper) {
        mErrorDescription.setText(message);

        if (listenerWrapper == null || listenerWrapper.getListener() == null) {
            mActionButton.setVisibility(View.GONE);
        } else {
            mActionButton.setVisibility(View.VISIBLE);
            // set up the action button label
            if (listenerWrapper.getResId() == 0) {
                mActionButton.setText(R.string.label_try_again);
            } else {
                mActionButton.setText(listenerWrapper.getResId());
            }
            mActionButton.setOnClickListener(listenerWrapper.getListener());
        }

    }

}
