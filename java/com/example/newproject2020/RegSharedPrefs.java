package com.example.newproject2020;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.newproject2020.SharedPrefs;

public class RegSharedPrefs {
    public static final String SHARED_PREFS = "regSharedPrefs";
    public static final String FNAME = "firstName";
    public static final String LNAME = "lastName";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String ID_NUM = "userID";
    public static final String RESTAURANT = "restaurant";

    public static void saveData(Context c, String fName, String lName, String email, String password, int idNum, String rest) {
        SharedPreferences sharedPreferences = c.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(FNAME, fName);
        editor.putString(LNAME, lName);
        editor.putString(EMAIL, email);
        editor.putString(PASSWORD, password);
        editor.putInt(ID_NUM, idNum);
        editor.putString(RESTAURANT, rest);
        editor.apply();
    }

}