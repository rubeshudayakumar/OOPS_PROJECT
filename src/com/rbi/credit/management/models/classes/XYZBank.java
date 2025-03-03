package com.rbi.credit.management.models.classes;

import com.rbi.credit.management.services.implementations.BankImpl;

import java.util.ArrayList;

public class XYZBank extends BankImpl {
    public XYZBank(){
        this.cardTypes = new ArrayList<>();
        this.cardTypes.add("Infinity");
        this.cardTypes.add("Prestige");
        this.cardTypes.add("Wanderer");
    }

    public ArrayList<String> getCardTypes() {
        return cardTypes;
    }
}
