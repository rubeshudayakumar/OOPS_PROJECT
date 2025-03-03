package com.rbi.credit.management.services.implementations;

import com.rbi.credit.management.models.classes.CreditCard;
import com.rbi.credit.management.models.classes.CustomerIdentification;
import com.rbi.credit.management.models.classes.Bank;
import com.rbi.credit.management.models.enums.CardStatus;
import com.rbi.credit.management.services.interfaces.BankInterface;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class BankImpl extends Bank implements BankInterface {
    protected BankImpl(){
        super();
    }

    abstract public ArrayList<String> getCardTypes();

    public void addAdmin() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the Admin name : ");
        String adminName = scanner.nextLine();
        System.out.println("Enter the Login Password : ");
        int loginPassword = scanner.nextInt();
        BankAdminImpl bankAdmin = new BankAdminImpl(this,adminName,loginPassword);
        this.bankAdmins.add(bankAdmin);
    }

    public CustomerImpl getCustomer(int id) {
        for(CustomerImpl customer: this.customers){
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
        for(BankAdminImpl ba : this.bankAdmins){
            System.out.printf("%-15s%n", ba.name);
        }
    }

    public boolean loginAdmin(String name, int password, ArrayList<CustomerIdentification> globalIds) {
        boolean flag = false;
        for(BankAdminImpl ba : this.bankAdmins){
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
        for(CustomerImpl customer : this.customers){
            ArrayList<CreditCard> creditCards = customer.getCreditCards();
            String[] headers = {"Customer ID", "Global ID", "Customer Name","Credit Card Type","Credit Card Number","Card Status"};
            System.out.printf("%-25s %-25s %-25s %-25s %-25s %-25s%n", headers[0], headers[1], headers[2],headers[3],headers[4],headers[5]);
            for(CreditCard creditCard : creditCards){
                System.out.printf("%-25s %-25s %-25s %-25s %-25d %-25s%n",customer.getCustomerId(),customer.getGlobalId(),customer.getCustomerName(),creditCard.getCardType(),creditCard.getCardNumber(), creditCard.getCardStatus());
            }
        }
    }

    public void viewBlockedCards(){
        for(CustomerImpl customer : this.customers){
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
            for(CustomerImpl customer : this.customers){
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
            System.err.println("IO Error : "+e.getMessage());
        }
        finally {
            System.out.println("The data has been retrieved in a file named : "+fileName);
        }
    }

    public CreditCard getCreditCardByCardNumber(long cardNumber) {
        for(CustomerImpl customer : this.customers){
            ArrayList<CreditCard> creditCards = customer.getCreditCards();
            for(CreditCard creditCard : creditCards){
               if(creditCard.getCardNumber() == cardNumber){
                   return creditCard;
               }
            }
        }
        return null;
    }

    public CustomerImpl getCustomerByCardNumber(long cardNumber){
        for(CustomerImpl customer : this.customers){
            ArrayList<CreditCard> creditCards = customer.getCreditCards();
            for(CreditCard creditCard : creditCards){
                if(creditCard.getCardNumber() == cardNumber){
                    return customer;
                }
            }
        }
        return null;
    }

    public void addCustomer(CustomerImpl customer) {
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
