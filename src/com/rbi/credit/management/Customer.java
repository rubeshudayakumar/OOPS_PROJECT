package com.rbi.credit.management;

import java.util.ArrayList;
import java.util.Scanner;

public class Customer extends Person{
    private ArrayList<CreditCard> creditCards;
    private String customerName;
    private final int globalId;

    Customer(String customerName,int customerId,int loginPassword, int globalId) {
        this.customerName = customerName;
        this.id = customerId;
        this.loginPassword = loginPassword;
        this.globalId = globalId;
        this.creditCards = new ArrayList<>();
    }

    public void customerActions(){
        while(true){
            System.out.println("1.Apply for a credit card");
            System.out.println("2.View Balance");
            System.out.println("3.Close/block credit card");
            System.out.println("4.Logout");
            Scanner scanner = new Scanner(System.in);
            int answer = scanner.nextInt();
            int exitCode =  switch (answer) {
                case 1,2,3,4 -> {
                    yield 1;
                }
                default -> {
                    yield 0;
                }
            };
            if(exitCode == 0){
                break;
            }
        }
    }

    public void addCard(CreditCard creditCard){
        this.creditCards.add(creditCard);
    }

    public int getGlobalId() {
        return globalId;
    }

    public String getCustomerName() {
        return this.customerName;
    }

    public int getCustomerId() {
        return id;
    }

    public ArrayList<CreditCard> getCreditCards() {
        return creditCards;
    }

    public void setCreditCards(ArrayList<CreditCard> creditCards) {
        this.creditCards = creditCards;
    }
}
