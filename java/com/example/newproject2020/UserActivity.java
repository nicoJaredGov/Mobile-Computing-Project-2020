package com.example.newproject2020;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.newproject2020.customer.CustomerActivity;
import com.example.newproject2020.employee.EmployeeActivity;
import com.example.project2020.R;

public class UserActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ImageView logo;
    TextView userTextView;
    String text;
    Integer pos;
    SharedPrefs sharedPref;
    String userTypeSaved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        /*String userTypeLoaded = sharedPref.loadData(this,userTypeSaved,"");
        if (!userTypeLoaded.isEmpty()){
            Intent i;
            if (userTypeLoaded == "1") {
                i = new Intent(this, CustomerActivity.class);
            }
            else{
                i = new Intent(this, EmployeeActivity.class);
            }
            startActivity(i);
            finish();
        }*/

        Spinner userSpinner = (Spinner) findViewById(R.id.userSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.users,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userSpinner.setAdapter(adapter);
        userSpinner.setOnItemSelectedListener(this);

         userTextView = (TextView) findViewById(R.id.userTextView);
    }

    public void user_button_click(View view){
        if (pos == 0){
            userTextView.setText("Please select an option");
        }
        else{
            sharedPref.saveData(this,userTypeSaved,pos.toString());
            Intent i;
            if (pos == 1) i = new Intent(this, CustomerLoginActivity.class);
            else if (pos == 2) i = new Intent(this,EmployeeLoginActivity.class);
            else{
                userTextView.setText("Error with application. Try again.");
                return;
            }
            startActivity(i);
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        text = parent.getItemAtPosition(position).toString();
        pos = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        userTextView.setText("Nothing was selected");
    }
}
