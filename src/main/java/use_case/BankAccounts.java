package use_case;

import model.BankAccount;

public interface BankAccounts {

    void saveNewCustomer (BankAccount bankAccount);
    BankAccount findByAccountNumber(String accountNumber);
    void saveBalance(BankAccountDTO bankAccountDTO);

}
