package com.rbi.credit.management.models.classes;

public class Cart {
    public Product product;
    public int quantity;

    public Cart(Product product,int quantity){
        this.product = product;
        this.quantity = quantity;
    }
}
