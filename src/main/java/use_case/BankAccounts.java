package use_case;

import model.BankAccount;

public interface BankAccounts {

    BankAccount findByAccountNumber(String accountNumber);

    void saveBalance(BankAccountDTO bankAccountDTO);
}
