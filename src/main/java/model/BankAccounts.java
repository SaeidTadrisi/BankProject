package model;

public interface BankAccounts {

    void saveNewCustomer (BankAccount bankAccount);
    BankAccount findByAccountNumber (String accountNumber);

}
