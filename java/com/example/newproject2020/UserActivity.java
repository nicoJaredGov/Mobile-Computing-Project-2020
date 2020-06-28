package com.example.newproject2020;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.newproject2020.customer.CustomerActivity;
import com.example.newproject2020.employee.EmployeeActivity;
import com.example.project2020.R;

public class UserActivity extends AppCompatActivity {

    ImageView logo;
    SharedPrefs sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        SharedPreferences sharedPreferences = getSharedPreferences(SharedPrefs.SHARED_PREFS, Context.MODE_PRIVATE);
        if (sharedPreferences.getBoolean(SharedPrefs.LOGGED_IN,false)){
            Intent i;
            String userType = sharedPreferences.getString(SharedPrefs.USER_TYPE,"");
            if (userType.equals("1")) {
                i = new Intent(this, CustomerActivity.class);
            } else {
                i = new Intent(this, EmployeeActivity.class);
            }
            startActivity(i);
            finish();
        }

    }

    public void callCustomerLogin(View view) {
        sharedPref.saveData(this,"1",false);
        Intent intent = new Intent(this, CustomerLoginActivity.class);
        startActivity(intent);
    }

    public void callEmployeeLogin(View view) {
        sharedPref.saveData(this,"2",false);
        Intent intent = new Intent(this, EmployeeLoginActivity.class);
        startActivity(intent);
    }

}
