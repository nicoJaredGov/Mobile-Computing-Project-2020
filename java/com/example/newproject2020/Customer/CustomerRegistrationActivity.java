package com.example.newproject2020.Customer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.newproject2020.RegSharedPrefs;
import com.example.newproject2020.TestActivity;
import com.example.project2020.R;

public class CustomerRegistrationActivity extends AppCompatActivity {

    //Variables
    ImageView backButton;
    Button nextButton;
    TextView registerTitleText;
    TextView cusRegTextView;

    RegSharedPrefs regSharedPref;
    EditText nameField, emailField, passwordField, confirmField;
    String name, email, password, confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_registration);

        //Hooks
        backButton = findViewById(R.id.registration_back_button);
        nextButton = findViewById(R.id.nextBtn);
        registerTitleText = findViewById(R.id.register_title_text);
        cusRegTextView = findViewById(R.id.customerRegTextView);
        nameField = findViewById(R.id.nameEditText);
        emailField = findViewById(R.id.emailEditText);
        passwordField = findViewById(R.id.passwordEditText);
        confirmField = findViewById(R.id.confirmEditText);

    }

    public void callRegisterNextScreen(View view) {
        name = nameField.getText().toString();
        email = emailField.getText().toString();
        password = passwordField.getText().toString();
        confirmPassword = confirmField.getText().toString();

        if(name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()){
            cusRegTextView.setText("Fill in all details");
            return;
        }
        if(!password.equals(confirmPassword)){
            cusRegTextView.setText("Confirmed password not matching");
            return;
        }

        regSharedPref.saveData(this,name,email,password,"1");
        Intent intent = new Intent(this, TestActivity.class); //Intent to customer activity

        //Add Transition
        Pair[] pairs = new Pair[3];

        pairs[0] = new Pair<View, String>(backButton, "transition_registration_back_button");
        pairs[1] = new Pair<View, String>(nextButton, "transition_register_next_button");
        pairs[2] = new Pair<View, String>(registerTitleText, "transition_register_title_text");

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(CustomerRegistrationActivity.this, pairs);
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }
    }



}
