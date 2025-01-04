package com.rbi.credit.management;

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

    public CreditCard(long cardNumber, int cvv, CardStatus cardStatus, int secretPin, String cardType, Bank bank) {
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.cardStatus = cardStatus;
        this.secretPin = secretPin;
        this.cardType = cardType;
        this.bank = bank;
    }

    public CardStatus getCardStatus() {
        return cardStatus;
    }
}
