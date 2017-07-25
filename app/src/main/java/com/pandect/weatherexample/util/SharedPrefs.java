package com.pandect.weatherexample.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by PROFESSORCORE on 7/13/17.
 */

public class SharedPrefs {

    private static final String SHARED_PREFS = "com.pandect.weather.sharedprefs";
    private static final String DENY_FOREVER = "com.pandect.weather.denyforever";

    public static void setDenyForever(Activity activity, boolean denyForever) {
        SharedPreferences sharedPref = activity.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(DENY_FOREVER, denyForever);
        editor.apply();

    }

    public static boolean getDenyForever(Activity activity) {
        SharedPreferences sharedPref = activity.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        return sharedPref.getBoolean(DENY_FOREVER, false);
    }
}
