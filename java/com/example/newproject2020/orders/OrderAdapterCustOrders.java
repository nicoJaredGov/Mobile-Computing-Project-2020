package com.example.newproject2020.orders;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project2020.R;

import java.util.List;

public class OrderAdapterCustOrders extends RecyclerView.Adapter<OrderAdapterCustOrders.OrderViewHolder> {

    private List<Order> orderList;
    private Context context;

    public OrderAdapterCustOrders(Context context, List<Order> orderList) {
        this.orderList = orderList;
        this.context = context;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
             View view = LayoutInflater.from(context).inflate(R.layout.cardview_customer_order,null);
             return new OrderViewHolder(view);
    }

    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position ) {
                holder.bindHolder(position);

    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    class OrderViewHolder extends RecyclerView.ViewHolder {

        TextView orderNumber, orderTime, orderStatus, empNameTextView, restaurantName;
        String empName;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            orderNumber = itemView.findViewById(R.id.TextViewOrderNumber);
            empNameTextView = itemView.findViewById(R.id.TextViewEmployeeName);
            orderTime = itemView.findViewById(R.id.TextViewOrderTime);
            orderStatus = itemView.findViewById(R.id.TextViewStatus);
            restaurantName = itemView.findViewById(R.id.TextViewRestaurant);
        }

        public void bindHolder(final int position){
            orderNumber.setText(String.valueOf(orderList.get(position).getOrderNumber()));
            empName = orderList.get(position).getEmployee();
            if (empName.startsWith("null null")){
                empNameTextView.setText("waiting");
            } else {
                empNameTextView.setText("Staff: " + empName);
            }

            orderTime.setText(String.valueOf(orderList.get(position).getTimeCreated()));
            orderStatus.setText(orderList.get(position).getStatus());
            restaurantName.setText(orderList.get(position).getRestaurant());


        }
    }

}
