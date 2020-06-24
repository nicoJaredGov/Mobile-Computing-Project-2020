package com.example.newproject2020.orders;

import android.app.Activity;
import android.content.ContentValues;
import android.os.Build;

import androidx.annotation.RequiresApi;

import org.json.JSONException;

import java.sql.Time;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class Order {

    private int orderNumber;
    private String customerEmail;
    private String employeeEmail;
    private String time;
    private String restaurant;
    private int rating = 0;
    private String status = "pending";

    public Order(int orderNumber, String time, String customer, String employee, String restaurant, int rate, String stat) {
        this.orderNumber = orderNumber;
        this.customerEmail = customer;
        this.employeeEmail = employee;
        this.restaurant = restaurant;
        this.time = time;
        this.rating = rate;
        this.status = stat;

        /*DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        this.time = dtf.format(now);*/
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public String getCustomer() {
        return customerEmail;
    }

    public String getEmployee() {
        return employeeEmail;
    }

    public String getTime() {
        return time;
    }

    public int getRating() {
        return rating;
    }

    public void updateRating(int i) {
        switch (i) {
            case 0:
                rating++;
            case 1:
                rating--;
        }
    }

    public String getRestaurant() {
        return restaurant;
    }

    public String getStatus() {
        return status;
    }

    public void updateStatus(int i) {
        switch (i) {
            case 0:
                status = "pending";
            case 1:
                status = "ready";
            case 2:
                status = "collected";
        }
    }
}
