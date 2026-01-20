package com.example.razorpay_demo;
import java.util.List;

public class Order {
    public String orderId;
    public List<CartItem> items;
    public double totalAmount;
    public String status;
    public String razorpayOrderId;

    public Order(String orderId, List<CartItem> items, double totalAmount, String status) {
        this.orderId = orderId;
        this.items = items;
        this.totalAmount = totalAmount;
        this.status = status;
    }
}