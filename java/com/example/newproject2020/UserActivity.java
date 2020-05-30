package com.example.newproject2020;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import com.example.project2020.R;

import java.util.ArrayList;
import java.util.List;

public class UserActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

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
            Intent i = new Intent(this,MainActivity.class);
            i.putExtra("userType",userTypeLoaded); //pos: 1 - customer, 2 - employee
            startActivity(i);
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
            Intent i = new Intent(this,MainActivity.class);
            i.putExtra("userType",pos.toString()); //pos: 1 - customer, 2 - employee
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
