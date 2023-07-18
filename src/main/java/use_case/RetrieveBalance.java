package use_case;

import model.BankAccount;

import java.math.BigDecimal;

public class RetrieveBalance {

    private final BankAccounts bankAccounts;

    public RetrieveBalance(BankAccounts bankAccounts) {
        this.bankAccounts = bankAccounts;
    }

    public BigDecimal getAccountBalance (String accountNumber) {
        BankAccount bankAccount = bankAccounts.findByAccountNumber(accountNumber);
        return bankAccount.getBalance();
    }
}
