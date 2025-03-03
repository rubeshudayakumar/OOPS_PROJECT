package com.rbi.credit.management.services.interfaces;

import com.rbi.credit.management.services.implementations.CustomerImpl;
import com.rbi.credit.management.models.classes.CustomerIdentification;
import com.rbi.credit.management.services.implementations.BankImpl;

import java.util.ArrayList;

public interface BankAdminInterface {

    public void setGlobalIds(ArrayList<CustomerIdentification> globalIds);
    public void addCustomer(BankImpl bank);
    public void issueNewCreditCard(ArrayList<CustomerIdentification> customers);
    public CustomerIdentification updateGlobalCapacity(CustomerImpl customer, ArrayList<CustomerIdentification> customerIdentifications);
    public int getActiveCardCount(CustomerImpl customer, ArrayList<CustomerIdentification> customerIdentifications);
    public void closeOrBlockCreditCard();
    public void reduceCardCapacityByOneUsingGlobalId(int globalId);
    public CustomerImpl checkIfCustomerHaveAnAccountInBank();
    public void adminActions(ArrayList<CustomerIdentification> globalIds);
}
