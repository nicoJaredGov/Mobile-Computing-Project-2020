package com.example.newproject2020;

import android.app.Activity;
import android.content.ContentValues;

import org.json.JSONException;

import static org.json.JSONObject.NULL;

public class addOrder {

    String customer_name;
    String employee_number;
    boolean confirmOrder;

    public addOrder(Activity activity, String cust, String empNum) {
        customer_name = cust;
        employee_number = empNum;

        PHPRequest orderReq = new PHPRequest("https://lamp.ms.wits.ac.za/home/s2094785/");
        ContentValues cv = new ContentValues();
        cv.put("customer",customer_name);
        cv.put("employee",employee_number);

        String file = "addOrder.php";

        orderReq.doRequest(activity, file, cv, new RequestHandler() {
            @Override
            public void processResponse(String response) throws JSONException {
                if (response.equals("TRUE")){
                    confirmOrder = true;

                }
                else{
                    confirmOrder = false;
                }

            }
        });
    }

}
