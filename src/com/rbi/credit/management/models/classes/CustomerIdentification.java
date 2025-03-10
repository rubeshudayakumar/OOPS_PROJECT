package com.rbi.credit.management.models.classes;

public class CustomerIdentification {
    private static int idIncrement = 0;
    private final int globalId;

    private int totalActiveCards;

    public int getTotalActiveCards() {
        return totalActiveCards;
    }

    public void setTotalActiveCards() {
        this.totalActiveCards++;
    }

    public CustomerIdentification(){
        this.globalId = CustomerIdentification.idIncrement;
        System.out.println("Customer created with global id : "+CustomerIdentification.idIncrement);
        CustomerIdentification.idIncrement ++;
        this.totalActiveCards = 0;
    }

    public void reduceTotalActiveCards(){
        this.totalActiveCards--;
    }

    public int getGlobalId() {
        return globalId;
    }
}
