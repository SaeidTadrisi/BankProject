package model;

import java.math.BigDecimal;
import java.util.Objects;

public class Transactions {
    private final String firstName;
    private final String lastName;
    private final String nationalId;
    private final String transactionType;
    private final BigDecimal amount;
    private final BigDecimal customerBalance;
    private final String transactionTime;

    public Transactions(String firstName, String lastName, String nationalId, String transactionType,
                        BigDecimal amount, BigDecimal customerBalance, String transactionTime) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nationalId = nationalId;
        this.transactionType = transactionType;
        this.amount = amount;
        this.customerBalance = customerBalance;
        this.transactionTime = transactionTime;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", nationalId='" + nationalId + '\'' +
                ", transactionType='" + transactionType + '\'' +
                ", amount=" + amount +
                ", customerBalance=" + customerBalance +
                ", transactionTime=" + transactionTime.toString() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transactions that = (Transactions) o;
        return Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(nationalId, that.nationalId) && Objects.equals(transactionType, that.transactionType) && Objects.equals(amount, that.amount) && Objects.equals(customerBalance, that.customerBalance) && Objects.equals(transactionTime, that.transactionTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, nationalId, transactionType, amount, customerBalance, transactionTime);
    }
}
