package com.example.miniventilator;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;

public class preferences {
    private static final String DATA_LOGIN = "status login";
    private static final String USER_NAME = "current user";
    private static final String DATA_AS = "as";

    public static SharedPreferences getSharedReferences(Context context) {
        //noinspection deprecation
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setDataAs(Context context, String data) {
        SharedPreferences.Editor editor = getSharedReferences(context).edit();
        editor.putString(DATA_AS, data);
        editor.apply();
    }
    public static void setUserName(Context context, String data) {
        SharedPreferences.Editor editor = getSharedReferences(context).edit();
        editor.putString(USER_NAME, data);
        editor.apply();
    }
    public static String getUserName(Context context) {
        return getSharedReferences(context).getString(USER_NAME, "");
    }

    public static String getDataAs(Context context) {
        return getSharedReferences(context).getString(DATA_AS, "");
    }

    public static void setDataLogin(Context context, boolean status) {
        SharedPreferences.Editor editor = getSharedReferences(context).edit();
        editor.putBoolean(DATA_LOGIN, status);
        editor.apply();
    }

    public static boolean getDataLogin(Context context) {
        return getSharedReferences(context).getBoolean(DATA_LOGIN, false);
    }

    public static void clearData(Context context) {
        SharedPreferences.Editor editor = getSharedReferences(context).edit();
        editor.remove(DATA_AS);
        editor.remove(DATA_LOGIN);
        editor.remove(USER_NAME);
        editor.apply();
    }
}
