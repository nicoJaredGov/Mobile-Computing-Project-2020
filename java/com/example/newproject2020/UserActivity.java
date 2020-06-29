package com.example.newproject2020;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.newproject2020.customer.CustomerActivity;
import com.example.newproject2020.employee.EmployeeActivity;
import com.example.project2020.R;

public class UserActivity extends AppCompatActivity {

    ImageView logo;
    TextView titleText,selectText;
    Button btnEmp,btnCust;
    SharedPrefs sharedPref;

    Animation animFromTop;
    Animation animFromBottom;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = this.getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
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

        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorPrimaryDark));
        }*/

        animFromTop = AnimationUtils.loadAnimation(this, R.anim.slide_top_animation);
        animFromBottom = AnimationUtils.loadAnimation(this, R.anim.slide_bottom_animation);

        logo = findViewById(R.id.imgLogo);
        titleText = findViewById(R.id.user_title_text);
        selectText = findViewById(R.id.user_select_text);
        btnCust = findViewById(R.id.user_customer_button);
        btnEmp = findViewById(R.id.user_employee_button);

        logo.setAnimation(animFromTop);
        titleText.setAnimation(animFromTop);
        selectText.setAnimation(animFromBottom);
        btnCust.setAnimation(animFromBottom);
        btnEmp.setAnimation(animFromBottom);

    }

    public void callCustomerLogin(View view) {
        sharedPref.saveData(this,"1",false);
        Intent intent = new Intent(this, CustomerLoginActivity.class);

        Pair[] custPairs = new Pair[1];
        custPairs[0] = new Pair<View,String>(btnCust,"transition_cust_btn");

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(UserActivity.this, custPairs);
            startActivity(intent,options.toBundle());
        }
        else{
            startActivity(intent);
        }
    }

    public void callEmployeeLogin(View view) {
        sharedPref.saveData(this,"2",false);
        Intent intent = new Intent(this, EmployeeLoginActivity.class);

        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View,String>(btnEmp,"transition_emp_btn");

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(UserActivity.this, pairs);
            startActivity(intent,options.toBundle());
        }
        else{
            startActivity(intent);
        }
    }

}
