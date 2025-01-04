package com.rbi.credit.management;

import java.util.ArrayList;

public class XYZBank extends Bank{


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
