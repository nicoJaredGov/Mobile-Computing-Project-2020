package com.example.newproject2020;

import android.app.ActivityOptions;
import android.content.ContentValues;
import android.content.Intent;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.newproject2020.employee.EmployeeActivity;
import com.example.newproject2020.employee.EmployeeRegistrationActivity;
import com.example.project2020.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class EmployeeLoginActivity extends AppCompatActivity {

    private TextView mTextView;
    private EditText mUsername;
    private EditText mPassword;
    Button nextBtn;
    ImageView backBtn;

    String username, password;
    Integer passwordCounter;
    RegSharedPrefs regSharedPrefs;
    SharedPrefs sharedPref;
    String firstName, lastName, employeeEmail, restaurant;
    int idNum;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_login);
        mTextView = findViewById(R.id.mTextView);
        mUsername = findViewById(R.id.usernameInput);
        mPassword = findViewById(R.id.passwordInput);
        nextBtn = findViewById(R.id.nextBtn);
        backBtn = findViewById(R.id.back_button);

        passwordCounter = 3; //counts down password attempts - only 3 allowed

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.blue_dark));
        }
    }

    public void nextOnClick(View view) {
        username = mUsername.getText().toString();
        if (username.isEmpty()) {
            mTextView.setText("Username missing");
            return;
        }
        password = mPassword.getText().toString();
        if (password.isEmpty()) {
            mTextView.setText("Password missing");
            return;
        }

        PHPRequest loginReq = new PHPRequest("https://lamp.ms.wits.ac.za/home/s2067058/");
        ContentValues cvLogin = new ContentValues();
        cvLogin.put("user", username);
        cvLogin.put("password", password);

        loginReq.doRequest(EmployeeLoginActivity.this, "user1.php", cvLogin, new RequestHandler() {
            @Override
            public void processResponse(String response) throws JSONException {
                processJSON(response);
            }
        });
    }

    public void registerOnClick(View view) {
        Intent intent = new Intent(this, EmployeeRegistrationActivity.class);

        Pair[] pairs = new Pair[2];
        pairs[0] = new Pair<View, String>(backBtn, "transition_registration_back_button");
        pairs[1] = new Pair<View, String>(nextBtn, "transition_register_next_button");

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(EmployeeLoginActivity.this, pairs);
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }
    }

    public void processJSON(String r) throws JSONException {
        if (r.equals("NULL")) {
            mTextView.setText("Email not found");
        } else if (r.equals("WRONG")) {
            passwordCounter--;
            if (passwordCounter == 0) {
                nextBtn.setEnabled(false);
            }
            String passwordCounterOutput = "You have " + passwordCounter.toString() + " attempts left.";
            mTextView.setText(passwordCounterOutput);
        } else {
            JSONArray ja = new JSONArray(r);
            JSONObject jo = ja.getJSONObject(0);
            firstName = jo.getString("EMP_FNAME");
            lastName = jo.getString("EMP_LNAME");
            employeeEmail = username;
            restaurant = jo.getString("RESTAURANT_NAME");
            idNum = jo.getInt("EMPLOYEE_ID");
            password = jo.getString("EMP_PASSWORD");
            regSharedPrefs.saveData(this, firstName, lastName, employeeEmail, password, idNum, restaurant);
            sharedPref.saveData(this, "2", true);

            Intent intent = new Intent(this, EmployeeActivity.class);
            startActivity(intent);
            finish();
        }

    }

    public void goBack(View view) {
        onBackPressed();
    }
}
