package com.example.newproject2020.employee;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ActivityOptions;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.newproject2020.CustomerLoginActivity;
import com.example.newproject2020.EmployeeLoginActivity;
import com.example.newproject2020.PHPRequest;
import com.example.newproject2020.RegSharedPrefs;
import com.example.newproject2020.RequestHandler;
import com.example.newproject2020.SharedPrefs;
import com.example.newproject2020.TestActivity;
import com.example.project2020.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class EmployeeRegistrationActivity extends AppCompatActivity {

    //Variables
    ImageView backButton;
    Button nextButton;
    TextView registerTitleText;
    TextView empRegTextView;

    EditText firstNameField, lastNameField, empEmailField, passwordField, confirmField, restaurantField;
    RegSharedPrefs regSharedPref;
    SharedPrefs sharedPref;
    String employeeEmail, password, confirmPassword, restaurant;
    String firstName, lastName;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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

       Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this,R.color.blue_dark));
        }

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

        PHPRequest empRegReq = new PHPRequest("https://lamp.ms.wits.ac.za/home/s2067058/");
        ContentValues cv = new ContentValues();
        cv.put("fname",firstName);
        cv.put("lname",lastName);
        cv.put("password",password);
        cv.put("employeeEmail",employeeEmail);
        cv.put("restaurant",restaurant);

        empRegReq.doRequest(this, "empReg.php", cv, new RequestHandler() {
            @Override
            public void processResponse(String response) throws JSONException {
                if(response.equals("FALSE")){
                    empRegTextView.setText("This email is already registered.");
                } else {
                    processJSON(response);
                }
            }
        });

    }

    public void processJSON(String response) throws JSONException {
        JSONArray ja = new JSONArray(response);
        JSONObject jo = ja.getJSONObject(0);
        regSharedPref.saveData(this,firstName,lastName,employeeEmail,jo.getString("EMP_PASSWORD"),jo.getInt("EMPLOYEE_ID"),restaurant);
        sharedPref.saveData(this,"2",true);

        Intent intent = new Intent(getApplicationContext(), TestActivity.class);
        startActivity(intent);
        finish();
    }

    /*public void splitName(String name) {
        String[] splitName = name.split(" ", 2);
        firstName = splitName[0];
        if (splitName.length == 1) lastName = "N/A";
        else lastName = splitName[1];;
    }*/

    public void emp_reg_back_btn_click(View view) {
        onBackPressed();
    }
}
