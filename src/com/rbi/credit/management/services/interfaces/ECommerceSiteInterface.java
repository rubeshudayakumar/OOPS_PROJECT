package com.rbi.credit.management.services.interfaces;

import com.rbi.credit.management.models.classes.Cart;

import java.util.ArrayList;

public interface ECommerceSiteInterface {
    public ArrayList<Cart> purchaseItems();
    public int getTotalPurchasedAmount(ArrayList<Cart> cartItems);
}
