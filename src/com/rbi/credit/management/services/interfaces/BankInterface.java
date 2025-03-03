package com.rbi.credit.management.services.interfaces;

import com.rbi.credit.management.models.classes.CreditCard;
import com.rbi.credit.management.services.implementations.CustomerImpl;

public interface BankInterface {
    public void addAdmin();
    public CustomerImpl getCustomer(int id);
    public void getAllAdmins();
    public void viewAllIssuedCreditCards();
    public void viewBlockedCards();
    public void retrieveBlockOrClosedCardsInFile();
    public CreditCard getCreditCardByCardNumber(long cardNumber);
    public CustomerImpl getCustomerByCardNumber(long cardNumber);
    public void addCustomer(CustomerImpl customer);
    public int getAdminIdTrack();
    public void setAdminIdTrack();
    public int getCustomerIdTrack();
    public void setCustomerIdTrack();
}
