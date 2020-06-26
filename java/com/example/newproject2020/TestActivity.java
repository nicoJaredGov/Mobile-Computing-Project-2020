package com.example.newproject2020;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.project2020.R;

public class TestActivity extends AppCompatActivity {

    String firstName,lastName,email,password,restaurant;
    int num;
    TextView t;
    Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        t = findViewById(R.id.textView);
        b = findViewById(R.id.button);

        SharedPreferences sharedPreferences = getSharedPreferences(RegSharedPrefs.SHARED_PREFS, Context.MODE_PRIVATE);
        firstName = sharedPreferences.getString(RegSharedPrefs.FNAME," ");
        lastName = sharedPreferences.getString(RegSharedPrefs.LNAME," ");
        email = sharedPreferences.getString(RegSharedPrefs.EMAIL," ");
        password = sharedPreferences.getString(RegSharedPrefs.PASSWORD," ");
        num = sharedPreferences.getInt(RegSharedPrefs.ID_NUM,0);
        restaurant = sharedPreferences.getString(RegSharedPrefs.RESTAURANT,"");


        t.setText(firstName+ " : " +lastName + " : "+email+ " : "+password+ " : "+num + " : " +restaurant);

    }

    public void onClicks(View view){
        Intent i = new Intent(this, UserActivity.class);
        startActivity(i);
    }
}
