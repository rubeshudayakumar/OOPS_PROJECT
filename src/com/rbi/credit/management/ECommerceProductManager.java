package com.rbi.credit.management;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class ECommerceProductManager {

    public ArrayList<ECommerceSite> eCommerceSites;

    public ECommerceProductManager(){
        ECommerceSite amazon = new ECommerceSite("Amazon",
                new ArrayList<Product>() {{
                    add(new Product("Laptop", 1500));
                    add(new Product("Smartphone", 800));
                    add(new Product("Tablet", 500));
                    add(new Product("Smartwatch", 300));
                    add(new Product("Headphones", 200));
                }}
        );
        ECommerceSite flipkart = new ECommerceSite("Flipkart",
                new ArrayList<Product>() {{
                    add(new Product("Monitor", 250));
                    add(new Product("Keyboard", 100));
                    add(new Product("Mouse", 50));
                    add(new Product("Printer", 400));
                    add(new Product("Speakers", 150));
                }}
        );
        this.eCommerceSites = new ArrayList<>();
        this.eCommerceSites.add(amazon);
        this.eCommerceSites.add(flipkart);
    }

    public int gotoSiteAndPurchase(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select the E-Commerce site : ");
        int iterator = 1;
        HashMap<Integer,ECommerceSite> availableSites = new HashMap<>();
        for(ECommerceSite eCommerceSite: eCommerceSites){
            System.out.println(iterator+"."+eCommerceSite.name);
            availableSites.put(iterator,eCommerceSite);
            iterator++;
        }
        int selectedOption = scanner.nextInt();
        ECommerceSite selectedEcommerceSite = availableSites.get(selectedOption);
        if(selectedEcommerceSite!=null){
            ArrayList<Cart> cartItems = selectedEcommerceSite.purchaseItems();
            return selectedEcommerceSite.getTotalPurchasedAmount(cartItems);
        }
        return 0;
    }
}
