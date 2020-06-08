package com.example.newproject2020.Employee;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.newproject2020.LoginActivity;
import com.example.newproject2020.PHPRequest;
import com.example.newproject2020.RegSharedPrefs;
import com.example.newproject2020.RequestHandler;
import com.example.newproject2020.TestActivity;
import com.example.project2020.R;

import org.json.JSONException;

public class EmployeeRegistrationActivity extends AppCompatActivity {

    //Shared Preferences
    public static final String SHARED_PREFS = "empSharedPrefs";
    public static final String FULL_NAME = "fullName";
    public static final String PASSWORD = "password";
    public static final String ID_NUM = "userID";

    //Variables
    ImageView backButton;
    Button nextButton;
    TextView registerTitleText;
    TextView empRegTextView;

    RegSharedPrefs regSharedPref;
    EditText nameField, numField, passwordField, confirmField;
    String name, employeeNum, password, confirmPassword;
    String firstName, lastName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_registration);

        //Hooks
        backButton = findViewById(R.id.registration_back_button);
        nextButton = findViewById(R.id.nextBtn);
        registerTitleText = findViewById(R.id.register_title_text);
        empRegTextView = findViewById(R.id.empRegTextView);
        nameField = findViewById(R.id.nameEditText);
        numField = findViewById(R.id.empNumEditText);
        passwordField = findViewById(R.id.passwordEditText);
        confirmField = findViewById(R.id.confirmEditText);

    }

    public void customer_registration_back_btn_click(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void callRegisterNextScreen(View view) {
        name = nameField.getText().toString();
        employeeNum = numField.getText().toString();
        password = passwordField.getText().toString();
        confirmPassword = confirmField.getText().toString();

        if(name.isEmpty() || employeeNum.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()){
            empRegTextView.setText("Fill in all details");
            return;
        }
        if(!password.equals(confirmPassword)){
            empRegTextView.setText("Confirmed password not matching");
            return;
        }

        splitName(name);

        PHPRequest customerRegReq = new PHPRequest("https://lamp.ms.wits.ac.za/home/s2067058/");
        ContentValues cv = new ContentValues();
        cv.put("fname",firstName);
        cv.put("lname",lastName);
        cv.put("password",password);

        customerRegReq.doRequest(this, "empReg.php", cv, new RequestHandler() {
            @Override
            public void processResponse(String response) throws JSONException {
                if (!response.equals("TRUE")){
                    empRegTextView.setText(response);
                    return;
                }
            }
        });

        regSharedPref.saveData(this,name," ",password,employeeNum);
        Intent intent = new Intent(getApplicationContext(), TestActivity.class);

        //Add Transition
        Pair[] pairs = new Pair[3];

        pairs[0] = new Pair<View, String>(backButton, "transition_registration_back_button");
        pairs[1] = new Pair<View, String>(nextButton, "transition_register_next_button");
        pairs[2] = new Pair<View, String>(registerTitleText, "transition_register_title_text");

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(EmployeeRegistrationActivity.this, pairs);
            startActivity(intent, options.toBundle());
            finish();
        } else {
            startActivity(intent);
            finish();
        }
    }

    public void splitName(String name) {
        String[] splitName = name.split(" ", 2);
        firstName = splitName[0];
        if (splitName.length == 1) lastName = "N/A";
        else lastName = splitName[1];;
    }

    public void emp_reg_back_btn_click(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
