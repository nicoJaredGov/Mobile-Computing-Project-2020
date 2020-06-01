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

    public static final String SHARED_PREFS = "regSharedPrefs";
    public static final String FULL_NAME = "fullName";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String ID_NUM = "userID";
    SharedPrefs sharedPrefs;
    String name, email, password, num;
    TextView t;
    Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        t = findViewById(R.id.textView);
        b = findViewById(R.id.button);

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        name = sharedPreferences.getString(FULL_NAME," ");
        email = sharedPreferences.getString(EMAIL," ");
        password = sharedPreferences.getString(PASSWORD," ");
        num = sharedPreferences.getString(ID_NUM," ");


        t.setText(name + " : "+email+ " : "+password+ " : "+num);

    }

    public void onClicks(View view){
        Intent i = new Intent(this, UserActivity.class);
        startActivity(i);
    }
}
