package use_case;

import model.BankAccount;
import model.Money;

import java.math.BigDecimal;

public class SaveTransactionDTO {
    private final BigDecimal balance;
    private final String accountNumber;
    private final String currencyType;
    private final BigDecimal amount;

    public SaveTransactionDTO (BankAccount bankAccount, Money money) {
        this.balance = bankAccount.getBalance();
        this.currencyType = money.getCurrencyType().name();
        this.amount = money.getAmount();
        this.accountNumber = bankAccount.getAccountNumber();
    }


    public BigDecimal getBalance() {
        return balance;
    }

    public String getCurrencyType() {
        return currencyType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getAccountNumber() {
        return accountNumber;
    }
}
