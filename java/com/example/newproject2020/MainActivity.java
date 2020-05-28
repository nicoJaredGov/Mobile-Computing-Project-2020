package com.example.newproject2020;
// Shahil's url "https://lamp.ms.wits.ac.za/home/s2094785/user_list.php?user="

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.project2020.R;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private TextView mTextView;
    private EditText mUsername;
    private EditText mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = findViewById(R.id.loginTextView);
        mUsername = findViewById(R.id.usernameInput);
        mPassword = findViewById(R.id.passwordInput);
    }

    public void login_button_click(View view){
        PHPRequest loginReq = new PHPRequest();
        loginReq.doRequest(MainActivity.this, "https://lamp.ms.wits.ac.za/home/s2094785/orders.php", new RequestHandler() {
            @Override
            public void processResponse(String response) {
                /*JSONArray passwordResponse = new JSONArray(response);
                JSONObject item =  passwordResponse.getJSONObject(0);
                String pass = item.getString("PASSWORD");
                mTextView.setText(pass);*/
            }
        });

        OkHttpClient client = new OkHttpClient();


        String username = mUsername.getText().toString();

    }
}
