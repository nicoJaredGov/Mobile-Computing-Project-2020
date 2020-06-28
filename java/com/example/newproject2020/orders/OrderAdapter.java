package com.example.newproject2020.orders;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newproject2020.PHPRequest;
import com.example.newproject2020.RequestHandler;
import com.example.project2020.R;

import org.json.JSONException;

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

        TextView orderNumber, custName, orderTime, orderStatus;
        Button updateBtn;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            orderNumber = itemView.findViewById(R.id.TextViewOrderNumber);
            custName = itemView.findViewById(R.id.TextViewCustomerName);
            //empName = itemView.findViewById(R.id.TextViewEmployeeName);
            orderTime = itemView.findViewById(R.id.TextViewOrderTime);
            orderStatus = itemView.findViewById(R.id.TextViewStatus);
            updateBtn = itemView.findViewById(R.id.updateBtn);
        }

        public void bindHolder(final int position){
            orderNumber.setText(String.valueOf(orderList.get(position).getOrderNumber()));
            custName.setText(orderList.get(position).getCustomer());
            orderTime.setText(String.valueOf(orderList.get(position).getTimeCreated()));
            orderStatus.setText(orderList.get(position).getStatus());
            updateBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int cvStatus;
                    if(orderList.get(position).getStatus().equals("pending")) cvStatus = 1;
                    else cvStatus = 2;

                    PHPRequest updateReq = new PHPRequest("https://lamp.ms.wits.ac.za/home/s2067058/");
                    ContentValues cv = new ContentValues();
                    cv.put("orderId",orderList.get(position).getOrderNumber());
                    cv.put("empId",0);
                    cv.put("status",cvStatus);

                    updateReq.doRequest((Activity) context, "updateOrder.php", cv, new RequestHandler() {
                        @Override
                        public void processResponse(String response) {
                            if(response.equals("TRUE")){
                                Intent intent = new Intent(context.getApplicationContext(),context.getClass());
                                context.startActivity(intent);
                                ((Activity) context).finish();
                            } else {
                                Toast.makeText(context.getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });
        }
    }
}
