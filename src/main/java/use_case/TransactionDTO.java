package use_case;

import java.math.BigDecimal;

public class TransactionDTO {

    private final String accountNumber;
    private final String transactionType;
    private final String currencyType;
    private final BigDecimal amount;
    private final BigDecimal customerBalance;

    public TransactionDTO(String accountNumber,BigDecimal amount, String currencyType
            , BigDecimal customerBalance, String transactionType) {

        this.accountNumber = accountNumber;
        this.amount = amount;
        this.currencyType = currencyType;
        this.customerBalance = customerBalance;
        this.transactionType = transactionType;
    }
    public String getAccountNumber() {
        return accountNumber;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public String getCurrencyType() {
        return currencyType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getCustomerBalance() {
        return customerBalance;
    }

    @Override
    public String toString() {
        return  "accountNumber='" + accountNumber + '\'' +
                ", transactionType='" + transactionType + '\'' +
                ", currencyType='" + currencyType + '\'' +
                ", amount=" + amount +
                ", customerBalance=" + customerBalance;
    }
}
