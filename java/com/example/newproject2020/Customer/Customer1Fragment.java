package com.example.newproject2020.Customer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newproject2020.Order;
import com.example.newproject2020.OrderAdapter;
import com.example.project2020.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Customer1Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Customer1Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    RecyclerView recyclerView;
    OrderAdapter adapter;

    List<Order> orderList;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Customer1Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Customer1Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Customer1Fragment newInstance(String param1, String param2) {
        Customer1Fragment fragment = new Customer1Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

            orderList = new ArrayList<>();
            recyclerView = (RecyclerView) findViewById(R.id.RecyclerViewCust1);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            orderList.add(new Order(1, "Customer 1", "Employee 1", "00:00", "Restaurant 1"));
            orderList.add(new Order(2, "Customer 2", "Employee 2", "00:00", "Restaurant 2"));
            adapter = new OrderAdapter(this,  orderList);
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_customer1, container, false);
    }

}
