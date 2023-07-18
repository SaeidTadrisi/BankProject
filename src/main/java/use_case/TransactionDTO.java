package use_case;

import model.Money;

import java.math.BigDecimal;

public class TransactionDTO {

    private final String accountNumber;
    private final Money money;
    private final BigDecimal customerBalance;
    private final String transactionType;

    public TransactionDTO(String accountNumber, Money money
            , BigDecimal customerBalance, String transactionType) {

        this.accountNumber = accountNumber;
        this.money = money;
        this.customerBalance = customerBalance;
        this.transactionType = transactionType;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public Money getMoney() {
        return money;
    }

    public BigDecimal getCustomerBalance() {
        return customerBalance;
    }

    public String getTransactionType() {
        return transactionType;
    }

    @Override
    public String toString() {
        return "TransactionDTO{" +
                "accountNumber='" + accountNumber + '\'' +
                ", money=" + money +
                ", customerBalance=" + customerBalance +
                ", transactionType='" + transactionType + '\'' +
                '}';
    }
}
