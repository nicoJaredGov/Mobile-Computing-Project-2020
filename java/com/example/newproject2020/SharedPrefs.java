package com.example.newproject2020;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefs {
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String USER_TYPE = "userType";
    public static final String LOGGED_IN = "loggedIn";

    public static void saveData(Context c, String userT, boolean log){
        SharedPreferences sharedPreferences = c.getSharedPreferences(SHARED_PREFS,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_TYPE,userT);
        editor.putBoolean(LOGGED_IN,log);
        editor.apply();
    }
}
