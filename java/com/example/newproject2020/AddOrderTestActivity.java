package com.example.newproject2020;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.project2020.R;

public class AddOrderTestActivity extends AppCompatActivity {

    EditText customerNameInput, customerEmailInput;
    String custName, custEmail;
    Button mButton;
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order_test);

        customerNameInput = findViewById(R.id.custNameInput);
        customerEmailInput = findViewById(R.id.custEmailInput);
        mButton = findViewById(R.id.AddOrderButton);
        mTextView = findViewById(R.id.order_text_view);
    }

    /*public void onClick_add_order(View order_view) {
        custName = customerNameInput.getText().toString();
        custEmail = customerNameInput.getText().toString();

        if(custName.isEmpty()) custName = "N/A";
        if(custEmail.isEmpty()){
            mTextView.setText("Customer email is missing");
            return;
        }



        PHPRequest orderReq = new PHPRequest("https://lamp.ms.wits.ac.za/home/s2067058/");
        ContentValues cv = new ContentValues();
        cv.put("customer",custName);
        cv.put("email",custEmail);
        cv.put("")
    }*/

}
