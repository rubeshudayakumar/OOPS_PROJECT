package com.rbi.credit.management;

import java.util.ArrayList;

public class ABCBank extends Bank{
    public ArrayList<String> cardTypes;

    public ABCBank(){
        this.cardTypes = new ArrayList<>();
        this.cardTypes.add("Gold");
        this.cardTypes.add("Silver");
        this.cardTypes.add("Platinum");
    }

    public ArrayList<String> getCardTypes() {
        return cardTypes;
    }
}
