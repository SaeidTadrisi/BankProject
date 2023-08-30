package use_case;

import java.math.BigDecimal;

public class GetTransactionDTO {

    private final String accountNumber;
    private final BigDecimal balance;
    private final String currencyType;
    private final BigDecimal amount;
    private final String transactionType;

    public GetTransactionDTO (String accountNumber, BigDecimal balance, String currencyType
                            ,BigDecimal amount, String transactionType) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.currencyType = currencyType;
        this.amount = amount;
        this.transactionType = transactionType;
    }

    @Override
    public String toString() {
        return "GetTransactionDTO{" +
                "accountNumber='" + accountNumber + '\'' +
                ", balance=" + balance +
                ", currencyType='" + currencyType + '\'' +
                ", amount=" + amount +
                ", transactionType='" + transactionType + '\'' +
                '}';
    }
}
