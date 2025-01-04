package com.rbi.credit.management;

import java.util.ArrayList;
import java.util.Scanner;

public abstract class Bank {
    private String bankName;

    public int adminIdTrack;
    public int customerIdTrack;
    public ArrayList<BankAdmin> bankAdmins;
    public ArrayList<Customer> customers;
    public ArrayList<String> cardTypes;

    Bank(){
        this.adminIdTrack = 0;
        this.customerIdTrack = 0;
        this.bankAdmins = new ArrayList<>();
        this.customers = new ArrayList<>();
    }

    abstract public ArrayList<String> getCardTypes();

    public void addAdmin() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the Admin name : ");
        String adminName = scanner.nextLine();
        System.out.println("Enter the Login Password : ");
        int loginPassword = scanner.nextInt();
        BankAdmin bankAdmin = new BankAdmin(this,adminName,loginPassword);
        this.bankAdmins.add(bankAdmin);
    }

    public Customer getCustomer(int id) {
        for(Customer customer: this.customers){
            if(customer.getCustomerId() == id){
                return customer;
            }
        }
        return null;
    }

    public void getAllAdmins() {
        int i = 1;

        for(BankAdmin ba : this.bankAdmins){
            System.out.println(i+" "+ba.name);
        }
    }

    public boolean loginAdmin(String name, int password, ArrayList<CustomerIdentification> globalIds) {
        boolean flag = false;
        for(BankAdmin ba : this.bankAdmins){
            if(ba.name.equals(name) && ba.loginPassword == password){
                System.out.println("Login success full");
                ba.setGlobalIds(globalIds);
                ba.adminActions(globalIds);
                flag = true;
            }
        }
        return flag;
    }

    public void addCustomer(Customer customer) {
        this.customers.add(customer);
    }

    public int getAdminIdTrack() {
        return this.adminIdTrack;
    }

    public void setAdminIdTrack() {
        this.adminIdTrack = this.adminIdTrack + 1;
    }

    public int getCustomerIdTrack() {
        return customerIdTrack;
    }

    public void setCustomerIdTrack() {
        this.customerIdTrack = this.customerIdTrack + 1;
    }
}
