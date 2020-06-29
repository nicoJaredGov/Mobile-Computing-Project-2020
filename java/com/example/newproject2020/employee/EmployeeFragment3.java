package com.example.newproject2020.employee;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newproject2020.orders.Order;
import com.example.newproject2020.orders.OrderAdapter;
import com.example.newproject2020.PHPRequest;
import com.example.newproject2020.RegSharedPrefs;
import com.example.newproject2020.RequestHandler;
import com.example.newproject2020.orders.OrderAdapterEmpHistory;
import com.example.project2020.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class EmployeeFragment3 extends Fragment {

    private String employeeName, restaurant;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private OrderAdapterEmpHistory adapter;
    private View listItemsView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public ArrayList<Order> processJSON(String response) throws JSONException {
        ArrayList<Order> orders = new ArrayList<>();
        JSONArray ja = new JSONArray(response);
        double upvotes = 0;
        double downvotes = 0;
        int rating;

        for (int i = 0; i < ja.length(); i++) {

            JSONObject jo = ja.getJSONObject(i);
            orders.add(new Order(jo.getInt("ORDER_ID"),
                    jo.getString("TIME_CREATED"),
                    jo.getString("TIME_COLLECTED"),
                    jo.getString("FNAME") + " " + jo.getString("LNAME"),
                    employeeName,
                    restaurant,
                    Integer.parseInt(jo.getString("RATING")),
                    jo.getString("ORDER_STATUS")));

            rating = jo.getInt("RATING");
            if(rating==1){upvotes++;}
            else if(rating==-1){downvotes++;}
        }

        ProgressBar ratingBar = listItemsView.findViewById(R.id.ratingBar);
        TextView upvotesTextView = listItemsView.findViewById(R.id.ratingUpvotesText);
        TextView downvotesTextView = listItemsView.findViewById(R.id.ratingDownvotesText);
        TextView overallRating = listItemsView.findViewById(R.id.ratingPercent);

        upvotesTextView.setText(String.valueOf((int) upvotes));
        downvotesTextView.setText(String.valueOf((int) downvotes));

        double percentRating;
        if(upvotes == 0 && downvotes == 0) percentRating = 0;
        else percentRating = (upvotes/(upvotes+downvotes))*100;
        String strDouble = String.format("%.2f", percentRating);
        overallRating.setText(strDouble+"%");
        ratingBar.setProgress((int) percentRating);

        return orders;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        listItemsView = inflater.inflate(R.layout.fragment_employee3, container, false);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(RegSharedPrefs.SHARED_PREFS, Context.MODE_PRIVATE);
        int employeeId = sharedPreferences.getInt(RegSharedPrefs.ID_NUM, 0);
        employeeName = sharedPreferences.getString(RegSharedPrefs.FNAME, "")
                + sharedPreferences.getString(RegSharedPrefs.LNAME, "");
        restaurant = sharedPreferences.getString(RegSharedPrefs.RESTAURANT, "");

        PHPRequest request = new PHPRequest("https://lamp.ms.wits.ac.za/home/s2067058/");
        ContentValues cv = new ContentValues();
        cv.put("empId", employeeId);
        cv.put("restaurant", restaurant);
        cv.put("choice", 3);

        request.doRequest(this.getActivity(), "fetchOrders.php", cv, new RequestHandler() {
            @Override
            public void processResponse(String response) throws JSONException {
                ArrayList<Order> orderArrayList = processJSON(response);

                recyclerView = listItemsView.findViewById(R.id.RecyclerViewEmp1);
                recyclerView.setHasFixedSize(true);
                layoutManager = new LinearLayoutManager(getContext());
                adapter = new OrderAdapterEmpHistory(getContext(), orderArrayList);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
            }
        });

        return listItemsView;
    }


}
