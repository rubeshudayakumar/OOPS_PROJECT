package com.rbi.credit.management;

import com.sun.tools.javac.Main;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class BankAdmin extends Person{
    private Bank bank;
    private ArrayList<CustomerIdentification> globalIds;

    public BankAdmin(Bank bank,String name,int loginPassword){
        this.bank = bank;
        this.id = bank.getAdminIdTrack();
        this.name = name;
        this.loginPassword = loginPassword;
        bank.setAdminIdTrack();
    }

    public void setGlobalIds(ArrayList<CustomerIdentification> globalIds) {
        this.globalIds = globalIds;
    }

    private void addCustomer(Bank bank) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the customer name : ");
        String customerName = scanner.nextLine();
        System.out.println("Enter the Login Password : ");
        int loginPassword = scanner.nextInt();
        System.out.println("Enter global id : ");
        int globalId =  scanner.nextInt();
        CustomerIdentification customerId = null;
        for(CustomerIdentification ci : this.globalIds){
            if(ci.getGlobalId() == globalId){
                customerId = ci;
            }
        }
        if(customerId!=null){
            System.out.println("Customer found!");
        }else{
            System.out.println("Customer not found");
            return;
        }
        Customer customer = new Customer(customerName,bank.getCustomerIdTrack(),loginPassword, globalId);
        bank.setCustomerIdTrack();
        bank.addCustomer(customer);
    }

    private void issueNewCreditCard(ArrayList<CustomerIdentification> customers) {
        CardStatus cardStatus;
        String cardType;

        Random random = new Random();
        long cardNumber = 100000000000L + (long)(random.nextDouble() * 900000000000L);
        int cvv = 100 + random.nextInt(900);
        int secretPin = 1000 + random.nextInt(9000);

        System.out.println("Select the card type : ");
        int i = 1;
        for(String cardName : this.bank.getCardTypes()){
            System.out.println(i+"."+cardName);
            i++;
        }
        Scanner scanner = new Scanner(System.in);
        int selectedOption = scanner.nextInt();
        ArrayList<String> cardTypes = this.bank.getCardTypes();
        String card = cardTypes.get(selectedOption-1);
        CreditCard creditCard = new CreditCard(cardNumber, cvv, CardStatus.ACTIVE, secretPin,card,bank);

        Customer customer = checkIfCustomerHaveAnAccountInBank();

        if(customer == null){
            System.out.println("Customer not found in the bank");
        }else{
            System.out.println("Customer found");
        }

        int activeCardCount = getActiveCardCount(customer, customers);

        if(activeCardCount < 5 && activeCardCount!=-1){
            System.out.println("The customer has a total of "+activeCardCount+" and can get "+(5-activeCardCount)+" more cards.");
            System.out.println("CARD TYPE : "+card);
            System.out.println("CARD NUMBER : "+cardNumber);
            System.out.println("CARD CVV : "+cvv);
            System.out.println("CARD SECRET PIN : "+secretPin);
            System.out.println("Press 1 to issue this card to customer");

            int keyPressed = scanner.nextInt();

            if(keyPressed==1 && customer!=null){
                customer.addCard(creditCard);
                CustomerIdentification ci = updateGlobalCapacity(customer, customers);
                if(ci!=null) {
                    System.out.println("Current capacity : " + ci.getTotalActiveCards() + "/5");
                    System.out.println("Credit card has been added successfully");
                }
            }else{
                System.out.println("Invalid customer ID");
            }
        }
        else{
            System.out.println("Customer doesn't have credit card capacity "+activeCardCount+"/5");
        }
    }

    private CustomerIdentification updateGlobalCapacity(Customer customer,ArrayList<CustomerIdentification> customerIdentifications) {
        for(CustomerIdentification ci : customerIdentifications){
            if(ci.getGlobalId() == customer.getGlobalId()){
                ci.setTotalActiveCards();
                return ci;
            }
        }
        return null;
    }

    private int getActiveCardCount(Customer customer,ArrayList<CustomerIdentification> customerIdentifications){
        for(CustomerIdentification ci : customerIdentifications){
            if(ci.getGlobalId() == customer.getGlobalId()){
                return ci.getTotalActiveCards();
            }
        }
        return -1;
    }



    private Customer checkIfCustomerHaveAnAccountInBank() {
        System.out.println("Enter the Customer ID : ");
        Scanner scanner = new Scanner(System.in);
        int customerId = scanner.nextInt();
        return this.bank.getCustomer(customerId);
    }

    public void adminActions(ArrayList<CustomerIdentification> globalIds){
        while(true){
            System.out.println("1.View all customers data.");
            System.out.println("2.View all issued cards.");
            System.out.println("3.Add new customer");
            System.out.println("4.Issue new credit card");
            System.out.println("5.View blocked cards");
            System.out.println("6.Close/block credit cards");
            System.out.println("7.Logout");
            Scanner scanner = new Scanner(System.in);
            int answer = scanner.nextInt();
            int exitCode =  switch (answer) {
                case 1 -> {
                    String[] headers = {"Customer ID", "Global ID", "Customer Name"};
                    System.out.printf("%-15s %-15s %-15s%n", headers[0], headers[1], headers[2]);
                    System.out.println("-----------------------------------------------");
                    for(Customer c : this.bank.customers){
                        System.out.printf("%-15d %-15s %-15s%n", c.getCustomerId() , c.getGlobalId(), c.getCustomerName());
                    }
                    yield 1;
                }
                case 2,5,6,7 -> {
                    yield 1;
                }
                case 3 -> {
                    addCustomer(this.bank);
                    yield 1;
                }
                case 4 -> {
                    issueNewCreditCard(globalIds);
                    yield 1;
                }
                default -> {
                    yield 0;
                }
            };
            if(exitCode == 0){
                break;
            }
        }
    }

}
