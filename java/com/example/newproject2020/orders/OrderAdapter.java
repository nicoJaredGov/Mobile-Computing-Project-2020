package com.example.newproject2020.orders;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project2020.R;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private List<Order> orderList;
    private Context context;

    public OrderAdapter(Context context,List<Order> orderList) {
        this.orderList = orderList;
        this.context = context;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
             View view = LayoutInflater.from(context).inflate(R.layout.cardview_emp_currorder,null);
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

        TextView orderNumber, custName, orderTime, orderStatus; //empName

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            orderNumber = itemView.findViewById(R.id.TextViewOrderNumber);
            custName = itemView.findViewById(R.id.TextViewCustomerName);
            //empName = itemView.findViewById(R.id.TextViewEmployeeName);
            orderTime = itemView.findViewById(R.id.TextViewOrderTime);
            orderStatus = itemView.findViewById(R.id.TextViewStatus);
        }

        public void bindHolder(int position){
            orderNumber.setText(String.valueOf(orderList.get(position).getOrderNumber()));
            custName.setText(orderList.get(position).getCustomer());
            orderTime.setText(String.valueOf(orderList.get(position).getTimeCreated()));
            orderStatus.setText(orderList.get(position).getStatus());
        }
    }
}
