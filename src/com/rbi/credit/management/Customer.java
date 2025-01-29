package com.rbi.credit.management;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Customer extends Person implements CustomerInteraction{
    private ArrayList<CreditCard> creditCards;
    private String customerName;
    private final int globalId;
    private Bank bank;
    public CustomerIdentification customerIdentification;

    Customer(String customerName,int customerId,int loginPassword, int globalId) {
        this.customerName = customerName;
        this.id = customerId;
        this.loginPassword = loginPassword;
        this.globalId = globalId;
        this.creditCards = new ArrayList<>();
    }

    public static void login(Bank bank,ArrayList<CustomerIdentification> globalCustomers){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the Customer ID : ");
        int customerId = scanner.nextInt();
        System.out.println("Enter the password : ");
        int password = scanner.nextInt();
        Customer customer = Customer.getCustomerByID(customerId,bank);

        if(customer == null){
            System.out.println("Customer ID is incorrect");
            return;
        }

        if(customer.getCustomerId() == customerId && password == customer.loginPassword){
            System.out.println("Logged-In Successful");
            System.out.println("Welcome, "+customer.customerName);
            customer.bank = bank;
            customer.getAndSetGlobalId(globalCustomers);
            customerActions(customer);
        }else{
            System.out.println("Incorrect Password!");
        }
    }

    public void getAndSetGlobalId(ArrayList<CustomerIdentification> customerIdentifications){
        for(CustomerIdentification customerId: customerIdentifications){
            if(customerId.getGlobalId() == globalId){
                customerIdentification = customerId;
            }
        }
    }

    public static void customerActions(Customer customer){
        while(true){
            System.out.println("Choose any of the below actions : ");
            System.out.println("1.Deposit");
            System.out.println("2.Spend");
            System.out.println("3.View Balance");
            System.out.println("4.Close/Block credit card");
            System.out.println("5.Logout");

            Scanner scanner = new Scanner(System.in);
            int answer = scanner.nextInt();
            int exitCode = switch (answer){
                case 1 -> {
                    customer.deposit();
                    yield 1;
                }
                case 2 -> {
                    customer.spend();
                    yield 1;
                }
                case 3 -> {
                    customer.viewBalance();
                    yield 1;
                }
                case 4 -> {
                    customer.closeOrBlockCard();
                    yield 1;
                }
                case 5 -> {
                    System.out.println("Logout Successfully");
                    yield 0;
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



    public static Customer getCustomerByID(int customerId,Bank bank) {
        for(Customer customer : bank.customers){
            if(customer.getCustomerId() == customerId){
                return customer;
            }
        }
        return null;
    }

    public void addCard(CreditCard creditCard){
        this.creditCards.add(creditCard);
    }

    public int getGlobalId() {
        return globalId;
    }

    public String getCustomerName() {
        return this.customerName;
    }

    public int getCustomerId() {
        return id;
    }

    public ArrayList<CreditCard> getCreditCards() {
        return creditCards;
    }

    public void setCreditCards(ArrayList<CreditCard> creditCards) {
        this.creditCards = creditCards;
    }

    public CreditCard pickACard(){
        String[] headers = {"S.No","Card Number", "Card Type", "Limit","Status"};
        System.out.printf("%-25s %-25s %-25s %-25s %-25s%n", headers[0], headers[1], headers[2],headers[3],headers[4]);
        int iterator = 1;
        HashMap<Integer, CreditCard> availableCards = new HashMap<>();
        for(CreditCard creditCard : creditCards){
            System.out.printf("%-25d %-25d %-25s %-25d %-25s%n",iterator,creditCard.getCardNumber(),creditCard.getCardType(),creditCard.getCurrentLimit(),creditCard.getCardStatus());
            availableCards.put(iterator,creditCard);
            iterator++;
        }
        System.out.println("Enter the Serial Number to Select the Card ");
        Scanner scanner = new Scanner(System.in);
        int selectedNumber = scanner.nextInt();
        return availableCards.get(selectedNumber);
    }

    @Override
    public void deposit() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select the Card you want to Spend : ");
        CreditCard creditCard = pickACard();
        if(creditCard.validatePin()) {
            int amountDue = creditCard.getAmountDue();
            System.out.println("Your current due amount is : " + amountDue);
            System.out.println("Enter the amount you want to deposit : ");
            while (true) {
                int amount = scanner.nextInt();
                if (amount > amountDue) {
                    System.out.println("You are over-depositing your credit card. Kindly enter amount less than (or) equal to " + amountDue);
                } else {
                    creditCard.credit(amount);
                    break;
                }
            }
        }else{
            System.out.println("Incorrect pin");
        }
    }

    @Override
    public void spend() {
        ECommerceProductManager eCommerceProductManager = new ECommerceProductManager();
        int purchasedAmount = eCommerceProductManager.gotoSiteAndPurchase();
        System.out.println("Select the Card you want to Spend : ");
        CreditCard creditCard = pickACard();
        if(creditCard.validatePin() && creditCard.validateCVV()){
            creditCard.debit(purchasedAmount);
        }else{
            System.out.println("Incorrect pin (or) CVV");
        }
    }

    @Override
    public void viewBalance() {
        System.out.println("Select the Card you want to View Balance : ");
        CreditCard creditCard = pickACard();
        if(creditCard.validatePin()) {
            System.out.println("Your Balance for the Card " + creditCard.getCardNumber() + " is " + creditCard.getBalance());
        }else{
            System.out.println("Incorrect pin");
        }
    }

    @Override
    public void closeOrBlockCard() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select the Card you want to Close/Block : ");
        CreditCard creditCard = pickACard();
        if(creditCard.validatePin()) {
            System.out.println("Select the Card Action : ");
            System.out.println("1.Block Card");
            System.out.println("2.Close Card");
            int option = scanner.nextInt();
            int result = switch (option) {
                case 1 -> {
                    creditCard.blockCreditCard();
                    System.out.println("CARD TYPE : "+creditCard.getCardType());
                    System.out.println("CARD NUMBER : "+creditCard.getCardNumber());
                    System.out.println("CARD CVV : "+creditCard.getCvv());
                    System.out.println("CARD STATUS : "+creditCard.getCardStatus());
                    yield 1;
                }
                case 2 -> {
                    creditCard.closeCreditCard();
                    System.out.println("CARD TYPE : "+creditCard.getCardType());
                    System.out.println("CARD NUMBER : "+creditCard.getCardNumber());
                    System.out.println("CARD CVV : "+creditCard.getCvv());
                    System.out.println("CARD STATUS : "+creditCard.getCardStatus());
                    yield 1;
                }
                default -> {
                    System.out.println("Invalid option!");
                    yield 0;
                }
            };
            if(result == 1){
                customerIdentification.reduceTotalActiveCards();
                System.out.println("Current Card Capacity for Customer : "+customerIdentification.getTotalActiveCards()+"/5");
            }
        }else{
            System.out.println("Incorrect pin");
        }
    }
}
