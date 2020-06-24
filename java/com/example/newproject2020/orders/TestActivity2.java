package com.example.newproject2020.orders;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.example.newproject2020.PHPRequest;
import com.example.newproject2020.RegSharedPrefs;
import com.example.newproject2020.RequestHandler;
import com.example.project2020.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TestActivity2 extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    String employeeID, employeeName, restaurant;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    OrderAdapter adapter;
    View listItemsView;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);

        sharedPreferences = getSharedPreferences(RegSharedPrefs.SHARED_PREFS, Context.MODE_PRIVATE);
        employeeID = sharedPreferences.getString(RegSharedPrefs.ID_NUM, "");
        employeeName = sharedPreferences.getString(RegSharedPrefs.FNAME, "")
                + sharedPreferences.getString(RegSharedPrefs.LNAME, "");
        restaurant = sharedPreferences.getString(RegSharedPrefs.RESTAURANT, "");

        loadOrders();
    }
    public void loadOrders(){
        PHPRequest request = new PHPRequest("https://lamp.ms.wits.ac.za/home/s2067058/");
        ContentValues cv = new ContentValues();
        cv.put("empID",1);
        cv.put("choice",1);

        request.doRequest(this, "fetchOrders.php", cv, new RequestHandler() {
            @Override
            public void processResponse(String response) throws JSONException {
                ArrayList<Order> orderArrayList = processJSON(response);

                recyclerView = findViewById(R.id.RecyclerView);
                recyclerView.setHasFixedSize(true);
                layoutManager = new LinearLayoutManager(TestActivity2.this);
                adapter = new OrderAdapter(orderArrayList);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
            }
        });
    }

    public ArrayList<Order> processJSON(String response) throws JSONException {
        ArrayList<Order> orders = new ArrayList<>();
        JSONArray ja = new JSONArray(response);

        for(int i=0; i<ja.length(); i++){

            JSONObject jo = ja.getJSONObject(i);
            orders.add(new Order(jo.getInt("ORDER_ID"),
                    jo.getString("TIME_CREATED"),
                    jo.getString("FNAME") + " " + jo.getString("LNAME"),
                    employeeName,
                    restaurant,
                    Integer.parseInt(jo.getString("RATING")),
                    jo.getString("ORDER_STATUS")));

        }

        return  orders;
    }

}