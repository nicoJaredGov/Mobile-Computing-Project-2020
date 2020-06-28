package com.example.newproject2020.orders;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.newproject2020.PHPRequest;
import com.example.newproject2020.RegSharedPrefs;
import com.example.newproject2020.RequestHandler;
import com.example.newproject2020.customer.CustomerActivity;
import com.example.project2020.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddOrderCustomerActivity extends AppCompatActivity {

    ListView restaurantList;
    ImageView backButton;
    EditText searchBar;
    ArrayAdapter adapter;
    String restaurantChoice;
    int customerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_add_order);

        restaurantList = findViewById(R.id.restaurantListView);
        backButton = findViewById(R.id.r_back_button);
        searchBar = findViewById(R.id.restaurant_search_filter);

        PHPRequest request = new PHPRequest("https://lamp.ms.wits.ac.za/home/s2067058/");
        ContentValues cv = new ContentValues();
        request.doRequest(this, "restaurants.php", cv, new RequestHandler() {
            @Override
            public void processResponse(String response) throws JSONException {
                populateRestaurants(response);
            }
        });
    }

   public void populateRestaurants(String r) throws JSONException {
        JSONArray ja = new JSONArray(r);
        final ArrayList<String> restaurantName = new ArrayList<>();
        for(int i=0; i<ja.length(); i++){
            JSONObject jo = ja.getJSONObject(i);
            restaurantName.add(jo.getString("RESTAURANT_NAME"));
        }

        adapter = new ArrayAdapter(this,R.layout.restaurant,restaurantName);
        restaurantList.setAdapter(adapter);

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                (AddOrderCustomerActivity.this).adapter.getFilter().filter(charSequence.toString());
                restaurantList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String a = (String) parent.getAdapter().getItem(position);
                        searchBar.setText(a);
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
       restaurantList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               searchBar.setText(restaurantName.get(position));
           }
       });

    }

    public void backOnClick(View view) {
        Intent intent = new Intent(this, CustomerActivity.class);
        startActivity(intent);
        finish();
    }

    public void confirm_button_click(View view){
        restaurantChoice = searchBar.getText().toString();

        SharedPreferences sharedPreferences = getSharedPreferences(RegSharedPrefs.SHARED_PREFS, Context.MODE_PRIVATE);
        customerId = sharedPreferences.getInt(RegSharedPrefs.ID_NUM,0);

        PHPRequest orderReq = new PHPRequest("https://lamp.ms.wits.ac.za/home/s2067058/");
        ContentValues cv = new ContentValues();
        cv.put("customerId",customerId);
        cv.put("restaurant",restaurantChoice);

        orderReq.doRequest(this, "addOrderCustomer.php", cv, new RequestHandler() {
            @Override
            public void processResponse(String response) throws JSONException {
                if(!response.equals("TRUE")) {
                    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                } else {
                    processOrderResponse();
                }
            }
        });
    }

    public void processOrderResponse(){
        Intent intent = new Intent(this,CustomerActivity.class);
        startActivity(intent);
        finish();
    }

}
