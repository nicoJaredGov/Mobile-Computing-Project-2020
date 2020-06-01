package com.example.newproject2020.Customer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.newproject2020.RegistrationSecondActivity;
import com.example.project2020.R;

public class CustomerRegistrationActivity extends AppCompatActivity {

    //Variables
    ImageView backButton;
    Button nextButton;
    TextView registerTitleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_registration);

        //Hooks
        backButton = findViewById(R.id.registration_back_button);
        nextButton = findViewById(R.id.register_next_button);
        registerTitleText = findViewById(R.id.register_title_text);
    }

    public void callRegisterNextScreen(View view) {
        Intent intent = new Intent(getApplicationContext(), RegistrationSecondActivity.class);

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
