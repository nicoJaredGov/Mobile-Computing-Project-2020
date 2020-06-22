package com.example.newproject2020;

import java.sql.Time;

public class Order {

    private int orderNumber;
    private String customer;
    private String employee;
    private Time time;
    private String restaurant;
    private boolean rating;

    public Order(int orderNumber, String customer, String employee, String time, String restaurant) {
        this.orderNumber = orderNumber;
        this.customer = customer;
        this.employee = employee;
        this.time = time;
        this.restaurant = restaurant;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public String getCustomer() {
        return customer;
    }

    public String getEmployee() {
        return employee;
    }

    public Time getTime() {
        return time;
    }

    public String getRestaurant() {
        return restaurant;
    }
}
