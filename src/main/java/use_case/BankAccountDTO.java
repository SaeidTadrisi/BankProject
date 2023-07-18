package use_case;

import java.math.BigDecimal;

public class BankAccountDTO {
    private final String accountNumber;
    private BigDecimal balance;

    public BankAccountDTO(String accountNumber, BigDecimal balance) {
        this.balance = balance;
        this.accountNumber = accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }
}
