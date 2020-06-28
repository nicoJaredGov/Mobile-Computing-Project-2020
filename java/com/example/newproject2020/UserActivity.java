package com.example.newproject2020;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.newproject2020.customer.CustomerActivity;
import com.example.newproject2020.employee.EmployeeActivity;
import com.example.project2020.R;

public class UserActivity extends AppCompatActivity {

    ImageView logo;
    SharedPrefs sharedPref;
    String userTypeSaved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        String userTypeLoaded = sharedPref.loadData(this,userTypeSaved,"");
        if (!userTypeLoaded.isEmpty()){
            Intent i;
            if (userTypeLoaded.equals("1")) {
                i = new Intent(this, CustomerActivity.class);
            } else {
                i = new Intent(this, EmployeeActivity.class);
            }
            startActivity(i);
            finish();
        }

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorPrimaryDark));
        }
    }

    public void callCustomerLogin(View view) {
        sharedPref.saveData(this,userTypeSaved,"1");
        Intent intent = new Intent(this, CustomerLoginActivity.class);
        startActivity(intent);
    }

    public void callEmployeeLogin(View view) {
        sharedPref.saveData(this,userTypeSaved,"2");
        Intent intent = new Intent(this, EmployeeLoginActivity.class);
        startActivity(intent);
    }

}
