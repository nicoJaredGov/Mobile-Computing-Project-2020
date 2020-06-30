package com.example.newproject2020.customer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ActivityOptions;
import android.content.ContentValues;
import android.content.Intent;
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

import com.example.newproject2020.CustomerLoginActivity;
import com.example.newproject2020.PHPRequest;
import com.example.newproject2020.RegSharedPrefs;
import com.example.newproject2020.RequestHandler;
import com.example.newproject2020.SharedPrefs;
import com.example.newproject2020.TestActivity;
import com.example.project2020.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CustomerRegistrationActivity extends AppCompatActivity {

    //Variables
    ImageView backButton;
    Button nextButton;
    TextView registerTitleText;
    TextView cusRegTextView;

    RegSharedPrefs regSharedPref;
    SharedPrefs sharedPref;
    EditText firstNameField, lastNameField, emailField, passwordField, confirmField;
    String firstName, lastName, email, password, confirmPassword, restaurant;
    int idNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_registration);

        //Hooks
        backButton = findViewById(R.id.registration_back_button);
        nextButton = findViewById(R.id.nextBtn);
        registerTitleText = findViewById(R.id.register_title_text);
        cusRegTextView = findViewById(R.id.customerRegTextView);
        firstNameField = findViewById(R.id.FirstNameEditText);
        lastNameField = findViewById(R.id.LastNameEditText);
        emailField = findViewById(R.id.emailEditText);
        passwordField = findViewById(R.id.passwordEditText);
        confirmField = findViewById(R.id.confirmEditText);

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this,R.color.orange_dark));
        }

    }

    public void callRegisterNextScreen(View view) throws InterruptedException {
        cusRegTextView.setText("");
        firstName = firstNameField.getText().toString();
        lastName = lastNameField.getText().toString();
        email = emailField.getText().toString();
        password = passwordField.getText().toString();
        confirmPassword = confirmField.getText().toString();

        if(firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()){
            cusRegTextView.setText("Fill in all details");
            return;
        }
        if(!TextUtils.isEmpty(email) && !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            cusRegTextView.setText("Invalid email");
            return;
        }
        if(!password.equals(confirmPassword)){
            cusRegTextView.setText("Confirmed password not matching");
            return;
        }

        //splitName(name);

        PHPRequest customerRegReq = new PHPRequest("https://lamp.ms.wits.ac.za/home/s2067058/");
        ContentValues cv = new ContentValues();
        cv.put("email",email);
        cv.put("fname",firstName);
        cv.put("lname",lastName);
        cv.put("password",password);

        customerRegReq.doRequest(this, "custReg.php", cv, new RequestHandler() {
            @Override
            public void processResponse(String response) throws JSONException {
                if(response.equals("FALSE")){
                    cusRegTextView.setText("This email is already registered.");
                } else {
                    processIdResponse(response);
                }
            }
        });

    }

    /*public void splitName(String name) {
        String[] splitName = name.split(" ", 2);
        firstName = splitName[0];
        if (splitName.length == 1) lastName = "N/A";
        else lastName = splitName[1];;
    }*/

    public void processIdResponse(String r) throws JSONException {
        JSONArray ja = new JSONArray(r);
        JSONObject jo = ja.getJSONObject(0);
        idNum = jo.getInt("CUSTOMER_ID");
        password = jo.getString("PASSWORD");
        regSharedPref.saveData(this,firstName,lastName,email,password,idNum,restaurant);
        sharedPref.saveData(this,"1",true);
        Intent intent = new Intent(this, CustomerActivity.class);
        startActivity(intent);
        finish();
    }

    public void customer_registration_back_btn_click(View view) {
        onBackPressed();
    }
}
