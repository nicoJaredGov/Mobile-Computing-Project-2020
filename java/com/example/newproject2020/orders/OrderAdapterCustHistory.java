package com.example.newproject2020.orders;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newproject2020.PHPRequest;
import com.example.newproject2020.RequestHandler;
import com.example.newproject2020.customer.Customer2Fragment;
import com.example.newproject2020.customer.CustomerActivity;
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
        ImageView thumbsUp, thumbsDown;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            orderNumber = itemView.findViewById(R.id.TextViewOrderNumber);
            empName = itemView.findViewById(R.id.TextViewEmployeeName);
            orderTime = itemView.findViewById(R.id.TextViewOrderTime);
            restaurantName = itemView.findViewById(R.id.TextViewRestaurant);
            thumbsDown = itemView.findViewById(R.id.thumbsDown);
            thumbsUp = itemView.findViewById(R.id.thumbsUp);
        }

        public void bindHolder(final int position){
            orderNumber.setText(String.valueOf(orderList.get(position).getOrderNumber()));
            empName.setText(orderList.get(position).getEmployee());
            orderTime.setText(String.valueOf(orderList.get(position).getTimeCollected()));
            restaurantName.setText(orderList.get(position).getRestaurant());

            switch (orderList.get(position).getRating()){
                case 1:
                    thumbsUp.setColorFilter(context.getResources().getColor(R.color.blue_dark));
                    break;
                case -1:
                    thumbsDown.setColorFilter(context.getResources().getColor(R.color.blue_dark));
            }

            thumbsUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Order order = orderList.get(position);
                    if (order.getRating() == 0) {
                        thumbsUp.setColorFilter(context.getResources().getColor(R.color.lightBlue));
                        PHPRequest updateReq = new PHPRequest("https://lamp.ms.wits.ac.za/home/s2067058/");
                        ContentValues cv = new ContentValues();
                        cv.put("orderId",orderList.get(position).getOrderNumber());
                        cv.put("empId",0);
                        cv.put("status",3);

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

                    } else {
                        Toast.makeText(context.getApplicationContext(), "Order already rated", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            thumbsDown.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Order order = orderList.get(position);
                    if (order.getRating() == 0) {
                        thumbsDown.setColorFilter(context.getResources().getColor(R.color.lightBlue));
                        PHPRequest updateReq = new PHPRequest("https://lamp.ms.wits.ac.za/home/s2067058/");
                        ContentValues cv = new ContentValues();
                        cv.put("orderId", orderList.get(position).getOrderNumber());
                        cv.put("empId", 0);
                        cv.put("status", 4);

                        updateReq.doRequest((Activity) context, "updateOrder.php", cv, new RequestHandler() {
                            @Override
                            public void processResponse(String response) {
                                if (response.equals("TRUE")) {
                                    Intent intent = new Intent(context.getApplicationContext(), CustomerActivity.class);
                                    context.startActivity(intent);
                                    ((Activity) context).finish();
                                } else {
                                    Toast.makeText(context.getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else {
                        Toast.makeText(context.getApplicationContext(), "Order already rated", Toast.LENGTH_SHORT).show();
                    }
                }
            });


        }
    }

}
