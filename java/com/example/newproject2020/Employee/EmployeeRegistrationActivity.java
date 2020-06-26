package com.example.newproject2020.employee;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.newproject2020.CustomerLoginActivity;
import com.example.newproject2020.PHPRequest;
import com.example.newproject2020.RegSharedPrefs;
import com.example.newproject2020.RequestHandler;
import com.example.newproject2020.TestActivity;
import com.example.project2020.R;

public class EmployeeRegistrationActivity extends AppCompatActivity {

    //Variables
    ImageView backButton;
    Button nextButton;
    TextView registerTitleText;
    TextView empRegTextView;

    RegSharedPrefs regSharedPref;
    EditText firstNameField, lastNameField, empEmailField, passwordField, confirmField, restaurantField;
    String employeeEmail, password, confirmPassword, restaurant;
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

        firstNameField = findViewById(R.id.FirstNameEditText);
        lastNameField = findViewById(R.id.LastNameEditText);
        empEmailField = findViewById(R.id.empEmailEditText);
        passwordField = findViewById(R.id.passwordEditText);
        confirmField = findViewById(R.id.confirmEditText);
        restaurantField = findViewById(R.id.restaurantEditText);

    }

    public void callRegisterNextScreen(View view) {
        empRegTextView.setText("");
        firstName = firstNameField.getText().toString();
        lastName = lastNameField.getText().toString();
        employeeEmail = empEmailField.getText().toString();
        password = passwordField.getText().toString();
        confirmPassword = confirmField.getText().toString();
        restaurant = restaurantField.getText().toString();

        if(firstName.isEmpty() || lastName.isEmpty() || employeeEmail.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || restaurant.isEmpty()){
            empRegTextView.setText("Fill in all details");
            return;
        }
        if(!password.equals(confirmPassword)){
            empRegTextView.setText("Confirmed password not matching");
            return;
        }

        //splitName(name);

        PHPRequest empRegReq = new PHPRequest("https://lamp.ms.wits.ac.za/home/s2067058/");
        ContentValues cv = new ContentValues();
        cv.put("fname",firstName);
        cv.put("lname",lastName);
        cv.put("password",password);
        cv.put("employeeEmail",employeeEmail);
        cv.put("restaurant",restaurant);

        empRegReq.doRequest(this, "empReg.php", cv, new RequestHandler() {
            @Override
            public void processResponse(String response) {
                if (!response.equals("TRUE")){
                    empRegTextView.setText(response);
                    return;
                }
            }
        });

        regSharedPref.saveData(this,firstName,lastName,employeeEmail,password,0,restaurant);
        Intent intent = new Intent(getApplicationContext(), EmployeeActivity.class);

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

    /*public void splitName(String name) {
        String[] splitName = name.split(" ", 2);
        firstName = splitName[0];
        if (splitName.length == 1) lastName = "N/A";
        else lastName = splitName[1];;
    }*/

    public void emp_reg_back_btn_click(View view) {
        Intent intent = new Intent(this, CustomerLoginActivity.class);
        startActivity(intent);
    }
}
