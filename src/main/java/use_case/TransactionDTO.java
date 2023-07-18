package use_case;

import model.Money;

import java.math.BigDecimal;
import java.util.Objects;

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


    @Override
    public String toString() {
        return "TransactionDTO{" +
                "accountNumber='" + accountNumber + '\'' +
                ", money=" + money +
                ", customerBalance=" + customerBalance +
                ", transactionType='" + transactionType + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionDTO that = (TransactionDTO) o;
        return Objects.equals(accountNumber, that.accountNumber) && Objects.equals(money, that.money)
                && Objects.equals(customerBalance, that.customerBalance)
                && Objects.equals(transactionType, that.transactionType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber, money, customerBalance, transactionType);
    }
}
