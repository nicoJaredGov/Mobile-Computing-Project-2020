package com.example.newproject2020;

import android.app.Activity;
import android.content.ContentValues;

import org.json.JSONException;

import static org.json.JSONObject.NULL;

public class addOrder {

    private String customerFirstName;
    private String customerEmail;
    private String employeeNumber;
    boolean confirmOrder;

    public addOrder(Activity activity, String custName, String custEmail, String empNum) {
        customerFirstName = custName;
        customerEmail = custEmail;
        employeeNumber = empNum;

        PHPRequest orderReq = new PHPRequest("https://lamp.ms.wits.ac.za/home/s2094785/");
        ContentValues cv = new ContentValues();
        cv.put("customer",customerFirstName);
        cv.put("email",custEmail);
        cv.put("employee",employeeNumber);

        String file = "addOrder.php";

    }

}
