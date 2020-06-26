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

public class OrderAdapterCustHistory extends RecyclerView.Adapter<OrderAdapterCustHistory.OrderViewHolder> {

    private List<Order> orderList;
    private Context context;

    public OrderAdapterCustHistory(Context context, List<Order> orderList) {
        this.orderList = orderList;
        this.context = context;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cardview_customer_history,null);
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

        TextView orderNumber, orderTime, empName, restaurantName;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            orderNumber = itemView.findViewById(R.id.TextViewOrderNumber);
            empName = itemView.findViewById(R.id.TextViewEmployeeName);
            orderTime = itemView.findViewById(R.id.TextViewOrderTime);
            restaurantName = itemView.findViewById(R.id.TextViewRestaurant);
        }

        public void bindHolder(int position){
            orderNumber.setText(String.valueOf(orderList.get(position).getOrderNumber()));
            empName.setText(orderList.get(position).getEmployee());
            orderTime.setText(String.valueOf(orderList.get(position).getTimeCollected()));
            restaurantName.setText(orderList.get(position).getRestaurant());
        }
    }

}
