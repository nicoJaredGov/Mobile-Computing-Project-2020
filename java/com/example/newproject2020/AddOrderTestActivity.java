package com.example.newproject2020;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.project2020.R;

public class AddOrderTestActivity extends AppCompatActivity {

    EditText customerInput;
    EditText employeeInput;
    Button mButton;
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order_test);

        customerInput = findViewById(R.id.custInput);
        employeeInput = findViewById(R.id.empInput);
        mButton = findViewById(R.id.AddOrderButton);
        mTextView = findViewById(R.id.order_text_view);
    }

    public void onClick_add_order(View order_view) {
        String empID = employeeInput.getText().toString();
        String custID = customerInput.getText().toString();

        new addOrder(this,custID,empID);
    }
}
