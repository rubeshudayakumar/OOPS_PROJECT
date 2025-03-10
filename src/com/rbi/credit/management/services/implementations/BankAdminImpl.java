package com.rbi.credit.management.services.implementations;

import com.rbi.credit.management.models.classes.CreditCard;
import com.rbi.credit.management.models.classes.CustomerIdentification;
import com.rbi.credit.management.models.classes.Person;
import com.rbi.credit.management.models.enums.CardStatus;
import com.rbi.credit.management.services.interfaces.BankAdminInterface;
import com.rbi.credit.management.utils.DataUtils;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class BankAdminImpl extends Person implements BankAdminInterface {
    private BankImpl bank;
    private ArrayList<CustomerIdentification> globalIds;

    public BankAdminImpl(BankImpl bank, String name, int loginPassword){
        this.bank = bank;
        this.id = bank.getAdminIdTrack();
        this.name = name;
        this.loginPassword = loginPassword;
        bank.setAdminIdTrack();
    }

    public void setGlobalIds(ArrayList<CustomerIdentification> globalIds) {
        this.globalIds = globalIds;
    }

    public void addCustomer(BankImpl bank) {
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
        CustomerImpl customer = new CustomerImpl(customerName,bank.getCustomerIdTrack(),loginPassword, globalId);
        bank.setCustomerIdTrack();
        bank.addCustomer(customer);
    }

    public void issueNewCreditCard(ArrayList<CustomerIdentification> customers) {
        CardStatus cardStatus;
        String cardType;

        Random random = new Random();
        long cardNumber = DataUtils.getRandomSixteenDigitNumber();
        int cvv = DataUtils.getRandomThreeDigits();
        int secretPin = DataUtils.getRandomFourDigits();

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

        CustomerImpl customer = checkIfCustomerHaveAnAccountInBank();

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
                    System.out.println("Current Credit Limit : "+creditCard.getCurrentLimit());
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

    public CustomerIdentification updateGlobalCapacity(CustomerImpl customer, ArrayList<CustomerIdentification> customerIdentifications) {
        for(CustomerIdentification ci : customerIdentifications){
            if(ci.getGlobalId() == customer.getGlobalId()){
                ci.setTotalActiveCards();
                return ci;
            }
        }
        return null;
    }

    public int getActiveCardCount(CustomerImpl customer, ArrayList<CustomerIdentification> customerIdentifications){
        for(CustomerIdentification ci : customerIdentifications){
            if(ci.getGlobalId() == customer.getGlobalId()){
                return ci.getTotalActiveCards();
            }
        }
        return -1;
    }

    public void closeOrBlockCreditCard() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the Card number : ");
        long cardNumber = scanner.nextLong();
        CreditCard creditCard = this.bank.getCreditCardByCardNumber(cardNumber);
        System.out.println("Card Details : ");
        System.out.println("CARD TYPE : "+creditCard.getCardType());
        System.out.println("CARD NUMBER : "+creditCard.getCardNumber());
        System.out.println("CARD CVV : "+creditCard.getCvv());
        System.out.println("CARD STATUS : "+creditCard.getCardStatus());
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
            CustomerImpl customer = this.bank.getCustomerByCardNumber(cardNumber);
            this.reduceCardCapacityByOneUsingGlobalId(customer.getGlobalId());
        }
    }

    public void reduceCardCapacityByOneUsingGlobalId(int globalId){
        for(CustomerIdentification customerId: globalIds){
            if(customerId.getGlobalId() == globalId){
                customerId.reduceTotalActiveCards();
                System.out.println("Current Card Capacity for Customer : "+customerId.getTotalActiveCards()+"/5");
            }
        }
    }


    public CustomerImpl checkIfCustomerHaveAnAccountInBank() {
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
            System.out.println("7.Retrieve Blocked/Closed Cards in a file");
            System.out.println("8.Logout");
            Scanner scanner = new Scanner(System.in);
            int answer = scanner.nextInt();
            int exitCode =  switch (answer) {
                case 1 -> {
                    String[] headers = {"Customer ID", "Global ID", "Customer Name"};
                    System.out.printf("%-15s %-15s %-15s%n", headers[0], headers[1], headers[2]);
                    System.out.println("-----------------------------------------------");
                    for(CustomerImpl c : this.bank.customers){
                        System.out.printf("%-15d %-15s %-15s%n", c.getCustomerId() , c.getGlobalId(), c.getCustomerName());
                    }
                    yield 1;
                }
                case 2 -> {
                    bank.viewAllIssuedCreditCards();
                    yield 1;
                }
                case 5 -> {
                    bank.viewBlockedCards();
                    yield 1;
                }
                case 6 -> {
                    closeOrBlockCreditCard();
                    yield 1;
                }
                case 8 -> {
                    System.out.println("Logged Out Successfully!");
                    yield 0;
                }
                case 3 -> {
                    addCustomer(this.bank);
                    yield 1;
                }
                case 4 -> {
                    issueNewCreditCard(globalIds);
                    yield 1;
                }
                case 7 ->{
                    bank.retrieveBlockOrClosedCardsInFile();
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
