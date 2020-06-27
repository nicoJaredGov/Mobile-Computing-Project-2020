package com.example.newproject2020.orders;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newproject2020.PHPRequest;
import com.example.newproject2020.RegSharedPrefs;
import com.example.newproject2020.RequestHandler;
import com.example.project2020.R;

import org.json.JSONException;

import java.util.List;

public class OrderAdapterAllOrders extends RecyclerView.Adapter<OrderAdapterAllOrders.OrderViewHolder> {

    private List<Order> orderList;
    private Context context;

    public OrderAdapterAllOrders(Context context, List<Order> orderList) {
        this.orderList = orderList;
        this.context = context;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
             View view = LayoutInflater.from(context).inflate(R.layout.cardview_emp_allorder,null);
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
        TextView orderNumber, custName, orderTime; //empName
        Button addBtn;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            orderNumber = itemView.findViewById(R.id.TextViewOrderNumber);
            custName = itemView.findViewById(R.id.TextViewCustomerName);
            //empName = itemView.findViewById(R.id.TextViewEmployeeName);
            orderTime = itemView.findViewById(R.id.TextViewOrderTime);
            addBtn = itemView.findViewById(R.id.addBtn);
        }

        public void bindHolder(final int position){
            orderNumber.setText(String.valueOf(orderList.get(position).getOrderNumber()));
            custName.setText(orderList.get(position).getCustomer());
            orderTime.setText(String.valueOf(orderList.get(position).getTimeCreated()));
            addBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences sharedPreferences = context.getApplicationContext().getSharedPreferences(RegSharedPrefs.SHARED_PREFS, Context.MODE_PRIVATE);
                    int employeeId = sharedPreferences.getInt(RegSharedPrefs.ID_NUM,0);

                    PHPRequest updateReq = new PHPRequest("https://lamp.ms.wits.ac.za/home/s2067058/");
                    ContentValues cv = new ContentValues();
                    cv.put("orderId",orderList.get(position).getOrderNumber());
                    cv.put("empId",employeeId);

                    updateReq.doRequest((Activity) context, "updateOrder.php", cv, new RequestHandler() {
                        @Override
                        public void processResponse(String response) throws JSONException {
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
