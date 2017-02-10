package com.apps.ericksonfilipe.popularmovies;


import android.content.Context;
import android.content.SharedPreferences;

import static com.apps.ericksonfilipe.popularmovies.MoviesActivity.POPULAR;

public class SharedPreferencesManager {

    private static final String PREFS_NAME = "SharedPreferences";
    private static final String PREF_KEY_ORDER_BY = "PREF_KEY_ORDER_BY";

    public static void saveOrderBy(Context context, String orderBy) {
        saveStringPreference(context, PREF_KEY_ORDER_BY, orderBy);
    }

    public static String getOrderBy(Context context) {
        return getStringPreference(context, PREF_KEY_ORDER_BY, POPULAR);
    }

    private static void saveStringPreference(final Context context,
                                             final String key,
                                             final String value) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = settings.edit();
        edit.putString(key, value);
        edit.apply();
    }

    private static String getStringPreference(final Context context,
                                              final String key,
                                              final String defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return settings.getString(key, defaultValue);
    }
}
