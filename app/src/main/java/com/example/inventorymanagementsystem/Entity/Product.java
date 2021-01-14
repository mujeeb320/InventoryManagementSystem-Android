package com.example.inventorymanagementsystem.Entity;

public class Product {
    private int id;
    private String name;
    private String quantity;
    private String price;
    private Product product_ID;

    public Product() {
    }

    public Product(int id, String name, String quantity, String price, Product product_ID) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.product_ID = product_ID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Product getProduct_ID() {
        return product_ID;
    }

    public void setProduct_ID(Product product_ID) {
        this.product_ID = product_ID;
    }
}
