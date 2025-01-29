package com.rbi.credit.management;

import java.util.HashMap;
import java.util.Scanner;

enum CardStatus{
    BLOCKED,
    CLOSED,
    ACTIVE,
}

public class CreditCard {
    private long cardNumber;
    private int cvv;
    private int secretPin;
    private CardStatus cardStatus;
    private Bank bank;
    private String cardType;
    private int currentLimit;
    private int balance;
    public static HashMap<String, Integer> creditCardLimits;

    static{
        creditCardLimits = new HashMap<>();
        creditCardLimits.put("Silver",100000);
        creditCardLimits.put("Gold",200000);
        creditCardLimits.put("Platinum",300000);
        creditCardLimits.put("Infinity",100000);
        creditCardLimits.put("Prestige",200000);
        creditCardLimits.put("Wanderer",300000);
    }


    public CreditCard(long cardNumber, int cvv, CardStatus cardStatus, int secretPin, String cardType, Bank bank) {
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.cardStatus = cardStatus;
        this.secretPin = secretPin;
        this.cardType = cardType;
        this.bank = bank;
        int limit = creditCardLimits.get(cardType);
        this.currentLimit = limit;
        this.balance = limit;
    }

    public CardStatus getCardStatus() {
        return cardStatus;
    }

    public int getBalance() {
        return balance;
    }

    public int getCurrentLimit() {
        return currentLimit;
    }

    public String getCardType() {
        return cardType;
    }

    public long getCardNumber() {
        return cardNumber;
    }

    public int getCvv() {
        return cvv;
    }

    public void blockCreditCard(){
        this.cardStatus = CardStatus.BLOCKED;
    }

    public void closeCreditCard() {
        this.cardStatus = CardStatus.CLOSED;
    }

    public void debit(int amount){
        if(cardStatus==CardStatus.ACTIVE){
            this.balance-=amount;
            System.out.println("Transaction completed! Your current Balance : "+getBalance());
        }else {
            System.out.println("Transaction Failed! Card Status is : "+cardStatus);
        }
    }

    public void credit(int amount){
        if(cardStatus==CardStatus.ACTIVE) {
            this.balance += amount;
            System.out.println("Transaction completed! Your current Balance : " + getBalance());
        }else {
            System.out.println("Transaction Failed! Card Status is : "+cardStatus);
        }
    }

    public int getAmountDue(){
        return this.currentLimit - this.balance;
    }

    public boolean validatePin(){
        System.out.println("Enter the secret pin : ");
        Scanner scanner = new Scanner(System.in);
        int enteredPin = scanner.nextInt();
        return enteredPin == this.secretPin;
    }

    public boolean validateCVV(){
        System.out.println("Enter the CVV : ");
        Scanner scanner = new Scanner(System.in);
        int enteredCVV = scanner.nextInt();
        return enteredCVV == this.cvv;
    }
}
