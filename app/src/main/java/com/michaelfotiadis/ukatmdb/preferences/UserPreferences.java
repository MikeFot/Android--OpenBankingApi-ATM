package com.michaelfotiadis.ukatmdb.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.michaelfotiadis.ukatmdb.network.Bank;

public class UserPreferences {

    private static final String PREFS_FILE = "user.preferences";
    private static final String KEY_LAST_SELECTED = "last.selected";

    public final SharedPreferences mSharedPreferences;

    public UserPreferences(final Context context) {

        mSharedPreferences = context.getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);

    }

    public void writeUserSelectedBank(final Bank bank) {

        mSharedPreferences.edit().putString(KEY_LAST_SELECTED, bank.toString()).apply();

    }

    public Bank getUserSelectedBank() {
        return Bank.fromString(mSharedPreferences.getString(KEY_LAST_SELECTED, Bank.UNDEFINED.toString()));
    }

}
