package com.example.newproject2020;
// Shahil's url "https://lamp.ms.wits.ac.za/home/s2094785/user_list.php?user="

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.project2020.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    TextView testView;

    private TextView mTextView;
    private EditText mUsername;
    private EditText mPassword;
    Button loginBtn;
    String username;
    String password;
    String pos;
    Integer passwordCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = findViewById(R.id.loginTextView);
        mUsername = findViewById(R.id.usernameInput);
        mPassword = findViewById(R.id.passwordInput);
        loginBtn = findViewById(R.id.LoginBtn);

        passwordCounter = 3; //counts down password attempts - only 3 allowed

        testView = findViewById(R.id.testView); //remove
    }

    public void login_button_click(View view){
        username = mUsername.getText().toString();
        if (username.isEmpty()){
            mTextView.setText("missing details");
            return;
        }
        password = mPassword.getText().toString();
        int usernameInt = Integer.parseInt(username);

        PHPRequest loginReq = new PHPRequest("https://lamp.ms.wits.ac.za/home/s2067058/");
        ContentValues cv = new ContentValues();
        cv.put("user",usernameInt);

        if (getIntent().hasExtra("userType")){
            pos = getIntent().getExtras().getString("userType"); // 1 = customer, 2 = employee
        }

        String file;
        if (pos.startsWith("1")) file = "user0.php";
        else if (pos.startsWith("2")) file = "user1.php";
        else{
            mTextView.setText("Error with userType"+ " " + pos);
            return;
        }

        loginReq.doRequest(LoginActivity.this, file, cv, new RequestHandler() {
            @Override
            public void processResponse(String response) throws JSONException {
                processJSON(response);
            }
        });

    }

    public void processJSON(String r) throws JSONException {
        JSONArray ja = new JSONArray(r);
        JSONObject jo = ja.getJSONObject(0);

        String pass;
        if (pos.startsWith("1")) pass = "PASSWORD";
        else if (pos.startsWith("2")) pass = "EMP_PASSWORD";
        else{
            testView.setText("Error");
            return;
        }

        testView.setText(jo.getString(pass));
        if (password.equals(jo.getString(pass))){
            mTextView.setText("Correct!");
            //next activity
        }
        else{
            passwordCounter--;
            if (passwordCounter == 0){
                loginBtn.setEnabled(false);
            }

            String passwordCounterOutput = "You have "+passwordCounter.toString() + " attempts left.";
            mTextView.setText(passwordCounterOutput);
        }

    }
}
