package com.rbi.credit.management.models.classes;

import com.rbi.credit.management.services.implementations.BankAdminImpl;
import com.rbi.credit.management.services.implementations.CustomerImpl;

import java.util.ArrayList;

public class Bank {

    private String bankName;

    public int adminIdTrack;
    public int customerIdTrack;
    public ArrayList<BankAdminImpl> bankAdmins;
    public ArrayList<CustomerImpl> customers;
    public ArrayList<String> cardTypes;

    protected Bank(){
        this.adminIdTrack = 0;
        this.customerIdTrack = 0;
        this.bankAdmins = new ArrayList<>();
        this.customers = new ArrayList<>();
    }
}
