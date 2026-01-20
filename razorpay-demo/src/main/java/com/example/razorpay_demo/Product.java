package com.example.razorpay_demo;

public class Product {
    public String id;
    public String name;
    public String description;
    public double price;
    public int stock;

    public Product(String id, String name, String description, double price, int stock) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
    }
}