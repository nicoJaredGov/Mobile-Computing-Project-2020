package com.example.newproject2020;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newproject2020.Customer.Customer1Fragment;
import com.example.project2020.R;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private Context mCtx;
    private List<Order> orderList;

    public OrderAdapter(Context mContext, List<Order> orderList) {
        this.mCtx = mContext;
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.fragment_customer1, null);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orderList.get(position);
        holder.textViewOrderNumber.setText(String.valueOf(order.getOrderNumber()));
        holder.textViewCustName.setText(order.getCustomer());
        holder.textViewEmpName.setText(order.getEmployee());
        holder.textViewOrderTime.setText(String.valueOf(order.getTime()));
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    class OrderViewHolder extends RecyclerView.ViewHolder {

        TextView textViewOrderNumber;
        TextView textViewCustName, textViewEmpName, textViewOrderTime;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewOrderNumber = itemView.findViewById(R.id.TextViewOrderNumber);
            textViewCustName = itemView.findViewById(R.id.TextViewCustomerName);
            textViewEmpName = itemView.findViewById(R.id.TextViewEmployeeName);
            textViewOrderTime = itemView.findViewById(R.id.TextViewOrderTime);
        }
    }

}
