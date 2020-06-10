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

    public static void saveData(Context c, String fName, String lName, String email, String password, String idNum){
        SharedPreferences sharedPreferences = c.getSharedPreferences(SHARED_PREFS,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(FNAME,fName);
        editor.putString(LNAME,lName);
        editor.putString(EMAIL,email);
        editor.putString(PASSWORD,password);
        editor.putString(ID_NUM,idNum);
        editor.apply();
    }

    public static String getFirstName(Context c, String defaultValue){
        SharedPreferences sharedPreferences = c.getSharedPreferences(SHARED_PREFS,Context.MODE_PRIVATE);
        return sharedPreferences.getString(FNAME,defaultValue);
    }

    public static String getLastName(Context c, String defaultValue){
        SharedPreferences sharedPreferences = c.getSharedPreferences(SHARED_PREFS,Context.MODE_PRIVATE);
        return sharedPreferences.getString(LNAME,defaultValue);
    }

    public static String getEmail(Context c, String defaultValue){
        SharedPreferences sharedPreferences = c.getSharedPreferences(SHARED_PREFS,Context.MODE_PRIVATE);
        return sharedPreferences.getString(EMAIL,defaultValue);
    }

    public static String getPassword(Context c, String defaultValue){
        SharedPreferences sharedPreferences = c.getSharedPreferences(SHARED_PREFS,Context.MODE_PRIVATE);
        return sharedPreferences.getString(PASSWORD,defaultValue);
    }

    public static String getIdNum(Context c, String defaultValue){
        SharedPreferences sharedPreferences = c.getSharedPreferences(SHARED_PREFS,Context.MODE_PRIVATE);
        return sharedPreferences.getString(ID_NUM,defaultValue);
    }

}
