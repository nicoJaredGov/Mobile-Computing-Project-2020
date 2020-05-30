package com.example.newproject2020;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefs {
    public static final String SHARED_PREFS = "sharedPrefs";

    public static void saveData(Context c, String settingName, String settingValue){
        SharedPreferences sharedPreferences = c.getSharedPreferences(SHARED_PREFS,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(settingName,settingValue);
        editor.apply();
    }

    public static String loadData(Context c, String settingName, String defaultValue){
        SharedPreferences sharedPreferences = c.getSharedPreferences(SHARED_PREFS,Context.MODE_PRIVATE);
        return sharedPreferences.getString(settingName,defaultValue);
    }
}
