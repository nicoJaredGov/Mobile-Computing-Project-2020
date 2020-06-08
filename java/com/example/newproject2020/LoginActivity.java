package com.example.newproject2020;
// Shahil's url "https://lamp.ms.wits.ac.za/home/s2094785/user_list.php?user="

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.newproject2020.Customer.CustomerRegistrationActivity;
import com.example.newproject2020.Employee.EmployeeRegistrationActivity;
import com.example.project2020.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    TextView testView;

    private TextView mTextView;
    private EditText mUsername;
    private EditText mPassword;
    Button nextBtn;
    Button registerBtn;

    String username, password, pos;
    Integer passwordCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mTextView = findViewById(R.id.mTextView);
        mUsername = findViewById(R.id.usernameInput);
        mPassword = findViewById(R.id.passwordInput);
        nextBtn = findViewById(R.id.nextBtn);

        passwordCounter = 3; //counts down password attempts - only 3 allowed

        if (getIntent().hasExtra("userType")){
            pos = getIntent().getExtras().getString("userType"); // 1 = customer, 2 = employee
        }

        testView = findViewById(R.id.testView); //remove
    }

    public void nextOnClick(View view){
        username = mUsername.getText().toString();
        if (username.isEmpty()){
            mTextView.setText("Username missing");
            return;
        }
        password = mPassword.getText().toString();
        if (password.isEmpty()){
            mTextView.setText("Password missing");
            return;
        }

        PHPRequest loginReq = new PHPRequest("https://lamp.ms.wits.ac.za/home/s2094785/");
        ContentValues cvLogin = new ContentValues();
        cvLogin.put("user",username);

        String file;
        if (pos.startsWith("1")) file = "user0.php";
        else if (pos.startsWith("2")) file = "user1.php";
        else{
            mTextView.setText("Error with userType"+ " " + pos);
            return;
        }

        loginReq.doRequest(LoginActivity.this, file, cvLogin, new RequestHandler() {
            @Override
            public void processResponse(String response) throws JSONException {
                    processJSON(response);
            }
        });
    }

    public void registerOnClick(View view){
        if (pos.startsWith("1")){
            Intent i = new Intent(this, CustomerRegistrationActivity.class);
            startActivity(i);
        }
        else if (pos.startsWith("2")){
            Intent i = new Intent(this, EmployeeRegistrationActivity.class);
            startActivity(i);
        }
        else{
            mTextView.setText("Error with registration path"+ " " + pos);
            return;
        }
    }

    public void processJSON(String r) throws JSONException {
        if(r.equals("[]")){
            mTextView.setText("Username invalid");
            return;
        }
        JSONArray ja = new JSONArray(r);
        JSONObject jo = ja.getJSONObject(0);

        String pass;
        if (pos.startsWith("1")) pass = "PASSWORD";
        else if (pos.startsWith("2")) pass = "EMP_PASSWORD";
        else{
            testView.setText("Error");
            return;
        }

        testView.setText(jo.getString(pass));
        if (password.equals(jo.getString(pass))){
            mTextView.setText("Correct!");
            //next activity
        }
        else{
            passwordCounter--;
            if (passwordCounter == 0){
                nextBtn.setEnabled(false);
            }

            String passwordCounterOutput = "You have "+passwordCounter.toString() + " attempts left.";
            mTextView.setText(passwordCounterOutput);
        }

    }

    public void goBack(View view) {
        Intent intent = new Intent(this, UserActivity.class);
        startActivity(intent);
    }
}
