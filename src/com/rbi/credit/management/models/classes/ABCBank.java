package com.rbi.credit.management.models.classes;

import com.rbi.credit.management.services.implementations.BankImpl;

import java.util.ArrayList;

public class ABCBank extends BankImpl {
    public ArrayList<String> cardTypes;

    public ABCBank(){
        super();
        this.cardTypes = new ArrayList<>();
        this.cardTypes.add("Gold");
        this.cardTypes.add("Silver");
        this.cardTypes.add("Platinum");
    }

    public ArrayList<String> getCardTypes() {
        return cardTypes;
    }
}
