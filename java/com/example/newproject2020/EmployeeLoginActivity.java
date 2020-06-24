package com.example.newproject2020;
// Shahil's url "https://lamp.ms.wits.ac.za/home/s2094785/user_list.php?user="

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.newproject2020.employee.EmployeeActivity;
import com.example.newproject2020.employee.EmployeeRegistrationActivity;
import com.example.project2020.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class EmployeeLoginActivity extends AppCompatActivity {

    TextView testView;

    private TextView mTextView;
    private EditText mUsername;
    private EditText mPassword;
    Button nextBtn;
    Button registerBtn;

    String username, password;
    Integer passwordCounter;
    RegSharedPrefs regSharedPrefs;
    String firstName,lastName,employeeEmail,restaurant;
    int idNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_login);
        mTextView = findViewById(R.id.mTextView);
        mUsername = findViewById(R.id.usernameInput);
        mPassword = findViewById(R.id.passwordInput);
        nextBtn = findViewById(R.id.nextBtn);

        passwordCounter = 3; //counts down password attempts - only 3 allowed

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

        PHPRequest loginReq = new PHPRequest("https://lamp.ms.wits.ac.za/home/s2067058/");
        ContentValues cvLogin = new ContentValues();
        cvLogin.put("user",username);

        loginReq.doRequest(EmployeeLoginActivity.this, "user1.php", cvLogin, new RequestHandler() {
            @Override
            public void processResponse(String response) throws JSONException {
                    processJSON(response);
            }
        });
    }

    public void registerOnClick(View view) {
        Intent i = new Intent(this, EmployeeRegistrationActivity.class);
        startActivity(i);
    }

    public void processJSON(String r) throws JSONException {
        if(r.equals("[]")){
            mTextView.setText("Username invalid");
            return;
        }
        JSONArray ja = new JSONArray(r);
        JSONObject jo = ja.getJSONObject(0);

        testView.setText(jo.getString("EMP_PASSWORD"));
        if (password.equals(jo.getString("EMP_PASSWORD"))){
            //mTextView.setText("Correct!");
            firstName = jo.getString("EMP_FNAME");
            lastName = jo.getString("EMP_LNAME");
            employeeEmail = jo.getString("EMP_EMAIL");
            restaurant = jo.getString("RESTAURANT_NAME");
            idNum = jo.getInt("EMPLOYEE_ID");
            regSharedPrefs.saveData(this,firstName,lastName,employeeEmail,password,idNum,restaurant);

            Intent intent = new Intent(this, EmployeeActivity.class);
            startActivity(intent);
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
