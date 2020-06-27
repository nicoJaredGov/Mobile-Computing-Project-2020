package com.example.newproject2020.employee;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newproject2020.orders.AddOrderEmployeeActivity;
import com.example.newproject2020.orders.Order;
import com.example.newproject2020.PHPRequest;
import com.example.newproject2020.RegSharedPrefs;
import com.example.newproject2020.RequestHandler;
import com.example.newproject2020.orders.OrderAdapterAllOrders;
import com.example.project2020.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class EmployeeFragment2 extends Fragment {

    SharedPreferences sharedPreferences;
    String employeeName, restaurant;
    int employeeId;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    OrderAdapterAllOrders adapter;
    View listItemsView;
    FloatingActionButton fab;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public ArrayList<Order> processJSON(String response) throws JSONException {
        ArrayList<Order> orders = new ArrayList<>();
        JSONArray ja = new JSONArray(response);

        for(int i=0; i<ja.length(); i++){

            JSONObject jo = ja.getJSONObject(i);
            orders.add(new Order(jo.getInt("ORDER_ID"),
                    jo.getString("TIME_CREATED"),
                    jo.getString("TIME_COLLECTED"),
                    jo.getString("FNAME") + " " + jo.getString("LNAME"),
                    employeeName,
                    restaurant,
                    Integer.parseInt(jo.getString("RATING")),
                    jo.getString("ORDER_STATUS")));

        }

        return  orders;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        listItemsView = inflater.inflate(R.layout.fragment_employee2, container, false);

        fab = (FloatingActionButton) listItemsView.findViewById(R.id.employeeFAB);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AddOrderEmployeeActivity.class));
            }
        });

        sharedPreferences = getActivity().getSharedPreferences(RegSharedPrefs.SHARED_PREFS, Context.MODE_PRIVATE);
        employeeId = sharedPreferences.getInt(RegSharedPrefs.ID_NUM,0);
        employeeName = sharedPreferences.getString(RegSharedPrefs.FNAME, "")
                + sharedPreferences.getString(RegSharedPrefs.LNAME, "");
        restaurant = sharedPreferences.getString(RegSharedPrefs.RESTAURANT, "");

        PHPRequest request = new PHPRequest("https://lamp.ms.wits.ac.za/home/s2067058/");
        ContentValues cv = new ContentValues();
        cv.put("empId",employeeId);
        cv.put("restaurant",restaurant);
        cv.put("choice",2);

        request.doRequest(this.getActivity(), "fetchOrders.php", cv, new RequestHandler() {
            @Override
            public void processResponse(String response) throws JSONException {
                ArrayList<Order> orderArrayList = processJSON(response);
                recyclerView = listItemsView.findViewById(R.id.RecyclerViewEmp1);
                recyclerView.setHasFixedSize(true);
                layoutManager = new LinearLayoutManager(getContext());
                adapter = new OrderAdapterAllOrders(getContext(),orderArrayList);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
            }
        });


        return listItemsView;
    }

}
