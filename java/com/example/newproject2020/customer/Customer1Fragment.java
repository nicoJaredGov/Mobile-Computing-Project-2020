package com.example.newproject2020.customer;

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

import com.example.newproject2020.orders.AddOrderCustomerActivity;
import com.example.newproject2020.orders.AddOrderEmployeeActivity;
import com.example.newproject2020.orders.Order;
import com.example.newproject2020.orders.OrderAdapter;
import com.example.newproject2020.PHPRequest;
import com.example.newproject2020.RegSharedPrefs;
import com.example.newproject2020.RequestHandler;
import com.example.newproject2020.orders.OrderAdapterCustOrders;
import com.example.newproject2020.orders.OrderAdapterEmpHistory;
import com.example.project2020.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Customer1Fragment extends Fragment {

    SharedPreferences sharedPreferences;
    String customerName;
    int customerId;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    OrderAdapterCustOrders adapter;
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
                    customerName,
                    jo.getString("EMP_FNAME")+" "+jo.getString("EMP_LNAME"),
                    jo.getString("RESTAURANT_NAME"),
                    Integer.parseInt(jo.getString("RATING")),
                    jo.getString("ORDER_STATUS")));
        }

        return  orders;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        listItemsView = inflater.inflate(R.layout.fragment_customer1, container, false);

        fab = (FloatingActionButton) listItemsView.findViewById(R.id.customerFAB);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AddOrderCustomerActivity.class));
                requireActivity().finish();
            }
        });

        sharedPreferences = getActivity().getSharedPreferences(RegSharedPrefs.SHARED_PREFS, Context.MODE_PRIVATE);
        customerId = sharedPreferences.getInt(RegSharedPrefs.ID_NUM,0);
        customerName = sharedPreferences.getString(RegSharedPrefs.FNAME, "")
                + sharedPreferences.getString(RegSharedPrefs.LNAME, "");

        PHPRequest request = new PHPRequest("https://lamp.ms.wits.ac.za/home/s2067058/");
        ContentValues cv = new ContentValues();
        cv.put("customerId",customerId);
        cv.put("choice",1);

        request.doRequest(this.getActivity(), "fetchOrdersCustomer.php", cv, new RequestHandler() {
            @Override
            public void processResponse(String response) throws JSONException {
                ArrayList<Order> orderArrayList = processJSON(response);

                recyclerView = listItemsView.findViewById(R.id.RecyclerViewCus);
                recyclerView.setHasFixedSize(true);
                layoutManager = new LinearLayoutManager(getContext());
                adapter = new OrderAdapterCustOrders(getContext(),orderArrayList);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
            }
        });


        return listItemsView;
    }
}
