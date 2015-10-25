package com.pinch.android.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.pinch.android.R;

public class SharedPreferenceUtil {
    public static void writeToSharedPreferences(Context context, String key, String value) {
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.shared_preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static void writeToSharedPreferences(Context context, String key, long value) {
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.shared_preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public static String getSharedPreferenceStringFromKey(Context context, String key) {
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.shared_preference_file_key), Context.MODE_PRIVATE);
        String value = sharedPref.getString(key, "");
        return value;
    }

    public static Long getSharedPreferenceLongFromKey(Context context, String key) {
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.shared_preference_file_key), Context.MODE_PRIVATE);
        Long value = sharedPref.getLong(key, 0L);
        return value;
    }
}
