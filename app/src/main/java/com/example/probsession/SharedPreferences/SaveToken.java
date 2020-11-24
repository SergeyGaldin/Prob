package com.example.probsession.SharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

public class SaveToken {

    public final static String NameFile = "token";

    public static String read(Context context, String settingName, String defaultNum) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(NameFile, Context.MODE_PRIVATE);
        return sharedPreferences.getString(settingName, defaultNum);
    }

    public static void save(Context context, String settingName, String settingValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(NameFile, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(settingName, settingValue);
        editor.apply();
    }
}
