
package com.bb.vib.ui.home.others.smartOtpPin.softTokenSdk.utils;

import android.content.Context;

import com.bb.vib.VibApp;


public class SharedPrefUtils {

    public static final String SOFT_TOKEN_LOGIN_TIME = "SOFT_TOKEN_LOGIN_TIME";

    private SecurePreferences preferences;

    /**
     * @param context your current context.
     */
    public SharedPrefUtils(Context context) {
        this.preferences = new SecurePreferences(context);
    }

    public static void putString(String key, String value) {
        VibApp.Companion.getSharedPreferences().putStringOrReplace(key, value);
    }

    public static String getString(String key) {
        return VibApp.Companion.getSharedPreferences().getStringFromSharedPre(key);
    }

    public static void putInt(String key, int value) {
        VibApp.Companion.getSharedPreferences().putIntToShare(key, value);
    }

    public static int getInt(String key, int defValue) {
        return VibApp.Companion.getSharedPreferences().getIntFromShare(key, defValue);
    }

    public static void putBoolean(String key, boolean value) {
        VibApp.Companion.getSharedPreferences().putBooleanToShare(key, value);
    }

    public static boolean getBoolean(String key, boolean defValue) {
        return VibApp.Companion.getSharedPreferences().getBooleanFromShare(key, defValue);
    }

    public static int getSoftTokenLoginTime() {
        return getInt(SOFT_TOKEN_LOGIN_TIME, 1);
    }

    public static void setSoftTokenLoginTime(int value) {
        putInt(SOFT_TOKEN_LOGIN_TIME, value);
    }

    private void putStringOrReplace(String key, String value) {
        if (value == null) {
            preferences.edit().remove(key).commit();
        } else {
            preferences.edit().putString(key, value).commit();
        }
    }

    private String getStringFromSharedPre(String key) {
        if (preferences.contains(key)) {
            return preferences.getString(key, "");
        }
        return null;
    }

    private void putBooleanToShare(String key, boolean value) {
        preferences.edit().putBoolean(key, value).commit();
    }

    private Boolean getBooleanFromShare(String key, boolean defValue) {
        if (preferences.contains(key)) {
            return preferences.getBoolean(key, defValue);
        } else {
            return defValue;
        }
    }

    private void putIntToShare(String key, int value) {
        preferences.edit().putInt(key, value).commit();
    }

    private int getIntFromShare(String key, int defValue) {
        if (preferences.contains(key)) {
            return preferences.getInt(key, defValue);
        } else {
            return defValue;
        }
    }
}
