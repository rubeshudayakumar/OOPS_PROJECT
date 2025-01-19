package com.rbi.credit.management;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class ECommerceSite {
    public String name;
    public ArrayList<Product> products;

    public ECommerceSite(String name,ArrayList<Product> products){
        this.name = name;
        this.products = products;
    }

    public ArrayList<Cart> purchaseItems(){
        Scanner scanner = new Scanner(System.in);
        int iterator = 1;
        System.out.println("Select the products by entering Serial number to add them to the cart : ");
        String[] headers = {"S.No","Product Name", "Price"};
        System.out.printf("%-25s %-25s %-25s%n", headers[0], headers[1], headers[2]);
        HashMap<Integer, Product> availableProducts = new HashMap<>();
        for(Product product : products){
            System.out.printf("%-25d %-25s %-25d%n",iterator,product.name,product.price);
            availableProducts.put(iterator,product);
            iterator++;
        }
        ArrayList<Cart> cart = new ArrayList<>();
        while(true){
            int enteredSno = scanner.nextInt();
            Product product = availableProducts.get(enteredSno);
            if(product == null){
                break;
            }
            System.out.println("Enter the Quantity : ");
            int quantity = scanner.nextInt();
            cart.add(new Cart(product,quantity));
            System.out.println("Your cart : ");
            headers = new String[]{"Product Name", "Quantity", "Price"};
            System.out.printf("%-25s %-25s %-25s%n",headers[0],headers[1],headers[2]);
            for(Cart item: cart){
                System.out.printf("%-25s %-25d %-25d%n",item.product.name,item.quantity,item.product.price);
            }
            System.out.println("Total Amount : "+getTotalPurchasedAmount(cart));
            System.out.println("Press any other number to Checkout (or) Add other products by Serial number");
        }
        return cart;
    }

    public int getTotalPurchasedAmount(ArrayList<Cart> cartItems){
        int totalAmount = 0;
        for(Cart cart: cartItems){
            totalAmount += (cart.product.price * cart.quantity);
        }
        return totalAmount;
    }

}
