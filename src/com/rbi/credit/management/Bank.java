package com.rbi.credit.management;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        String[] headers = {"Admin Name"};
        System.out.printf("%-15s%n", headers[0]);
        System.out.println("----------------");
        for(BankAdmin ba : this.bankAdmins){
            System.out.printf("%-15s%n", ba.name);
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

    public void viewAllIssuedCreditCards(){
        for(Customer customer : this.customers){
            ArrayList<CreditCard> creditCards = customer.getCreditCards();
            String[] headers = {"Customer ID", "Global ID", "Customer Name","Credit Card Type","Credit Card Number","Card Status"};
            System.out.printf("%-25s %-25s %-25s %-25s %-25s %-25s%n", headers[0], headers[1], headers[2],headers[3],headers[4],headers[5]);
            for(CreditCard creditCard : creditCards){
                System.out.printf("%-25s %-25s %-25s %-25s %-25d %-25s%n",customer.getCustomerId(),customer.getGlobalId(),customer.getCustomerName(),creditCard.getCardType(),creditCard.getCardNumber(), creditCard.getCardStatus());
            }
        }
    }

    public void viewBlockedCards(){
        for(Customer customer : this.customers){
            ArrayList<CreditCard> creditCards = customer.getCreditCards();
            String[] headers = {"Customer ID", "Global ID", "Customer Name","Credit Card Type","Credit Card Number","Card Status"};
            System.out.printf("%-25s %-25s %-25s %-25s %-25s %-25s%n", headers[0], headers[1], headers[2],headers[3],headers[4],headers[5]);
            for(CreditCard creditCard : creditCards){
                if(creditCard.getCardStatus() == CardStatus.BLOCKED){
                    System.out.printf("%-25s %-25s %-25s %-25s %-25d %-25s%n",customer.getCustomerId(),customer.getGlobalId(),customer.getCustomerName(),creditCard.getCardType(),creditCard.getCardNumber(), creditCard.getCardStatus());
                }
            }
        }
    }

    public void retrieveBlockOrClosedCardsInFile(){
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        String formattedDateTime = currentDateTime.format(formatter);
        String fileName = "blocked_or_closed_cards_" + formattedDateTime + ".txt";
        try(
                PrintWriter pw = new PrintWriter(new FileWriter(fileName));
                ){
            for(Customer customer : this.customers){
                ArrayList<CreditCard> creditCards = customer.getCreditCards();
                String[] headers = {"Customer ID", "Global ID", "Customer Name","Credit Card Type","Credit Card Number","Card Status"};
                pw.printf("%-25s %-25s %-25s %-25s %-25s %-25s%n", headers[0], headers[1], headers[2],headers[3],headers[4],headers[5]);
                for(CreditCard creditCard : creditCards){
                    if(creditCard.getCardStatus() != CardStatus.ACTIVE){
                        pw.printf("%-25s %-25s %-25s %-25s %-25d %-25s%n",customer.getCustomerId(),customer.getGlobalId(),customer.getCustomerName(),creditCard.getCardType(),creditCard.getCardNumber(), creditCard.getCardStatus());
                    }
                }
            }
        }catch (IOException e){
            System.out.println("IO Error : "+e.getMessage());
        }
        finally {
            System.out.println("The data has been retrieved in a file named : "+fileName);
        }
    }

    public CreditCard getCreditCardByCardNumber(long cardNumber) {
        for(Customer customer : this.customers){
            ArrayList<CreditCard> creditCards = customer.getCreditCards();
            for(CreditCard creditCard : creditCards){
               if(creditCard.getCardNumber() == cardNumber){
                   return creditCard;
               }
            }
        }
        return null;
    }

    public Customer getCustomerByCardNumber(long cardNumber){
        for(Customer customer : this.customers){
            ArrayList<CreditCard> creditCards = customer.getCreditCards();
            for(CreditCard creditCard : creditCards){
                if(creditCard.getCardNumber() == cardNumber){
                    return customer;
                }
            }
        }
        return null;
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
