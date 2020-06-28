package com.example.newproject2020.orders;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.newproject2020.PHPRequest;
import com.example.newproject2020.RegSharedPrefs;
import com.example.newproject2020.RequestHandler;
import com.example.newproject2020.customer.CustomerActivity;
import com.example.newproject2020.employee.EmployeeActivity;
import com.example.project2020.R;

import org.json.JSONException;

public class AddOrderEmployeeActivity extends AppCompatActivity {

    EditText customerNameInput, customerEmailInput;
    String custName, custEmail, restaurant;
    int employeeId;
    Button mButton;
    TextView mTextView;
    RegSharedPrefs regSharedPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_add_order);

        customerNameInput = findViewById(R.id.custNameInput);
        customerEmailInput = findViewById(R.id.custEmailInput);
        mButton = findViewById(R.id.AddOrderButton);
        mTextView = findViewById(R.id.order_text_view);

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorPrimaryDark));
        }
    }

    public void backOnClick(View view) {
        Intent intent = new Intent(this, EmployeeActivity.class);
        startActivity(intent);
    }

    public void onClick_add_order(View order_view) {
        custName = customerNameInput.getText().toString();
        custEmail = customerEmailInput.getText().toString();

        if(custName.isEmpty()) custName = "N/A";
        if(custEmail.isEmpty()){
            mTextView.setText("Customer email is missing");
            return;
        }

        SharedPreferences sharedPreferences = getSharedPreferences(RegSharedPrefs.SHARED_PREFS, Context.MODE_PRIVATE);
        employeeId = sharedPreferences.getInt(RegSharedPrefs.ID_NUM,0);
        restaurant = sharedPreferences.getString(RegSharedPrefs.RESTAURANT,"");

        PHPRequest orderReq = new PHPRequest("https://lamp.ms.wits.ac.za/home/s2067058/");
        ContentValues cv = new ContentValues();
        cv.put("custEmail",custEmail);
        cv.put("empId",employeeId);
        cv.put("restaurant",restaurant);

        orderReq.doRequest(this, "addOrder.php", cv, new RequestHandler() {
            @Override
            public void processResponse(String response) throws JSONException {
                if(!response.equals("TRUE")){
                    mTextView.setText(response);
                }
                else{
                    processOrderResponse();
                }
            }
        });
    }

    public void processOrderResponse(){
        Intent intent = new Intent(this, EmployeeActivity.class);
        startActivity(intent);
        finish();
    }

}
