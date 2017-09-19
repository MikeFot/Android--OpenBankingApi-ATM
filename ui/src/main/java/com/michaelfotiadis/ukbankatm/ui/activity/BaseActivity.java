package com.michaelfotiadis.ukbankatm.ui.activity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.michaelfotiadis.ukbankatm.ui.R;
import com.michaelfotiadis.ukbankatm.ui.toast.ActivityNotificationController;
import com.michaelfotiadis.ukbankatm.ui.toast.SnackbarNotificationController;


public abstract class BaseActivity extends AppCompatActivity {

    protected static final int NO_LAYOUT = 0;
    private ActivityNotificationController mNotificationController;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getLayoutResId() != NO_LAYOUT) {
            setContentView(getLayoutResId());
            final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            if (toolbar != null) {
                setSupportActionBar(toolbar);
            }
            final CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
            if (coordinatorLayout != null) {
                mNotificationController = new SnackbarNotificationController(this);
            }
        }
    }

    @LayoutRes
    protected abstract int getLayoutResId();

    @Override
    public void setTitle(final CharSequence title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        } else {
            super.setTitle(title);
        }
    }

    protected void addContentFragmentIfMissing(final Fragment fragment, final int id, final String fragmentTag) {

        if (getSupportFragmentManager().findFragmentByTag(fragmentTag) == null) {
            final FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(id, fragment, fragmentTag);
            fragmentTransaction.commit();
        }
    }

    protected void replaceContentFragment(final Fragment fragment, final int id, final String fragmentTag) {

        if (getSupportFragmentManager().findFragmentByTag(fragmentTag) == null) {
            addContentFragmentIfMissing(fragment, id, fragmentTag);
        } else {
            final FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(id, fragment, fragmentTag);
            fragmentTransaction.commit();
        }


    }

    public void setDisplayHomeAsUpEnabled(final boolean isEnabled) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(isEnabled);
        }
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public ActivityNotificationController getNotificationController() {
        return mNotificationController;
    }

}
