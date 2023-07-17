package model;

import exceptions.CustomerDetailsException;
import exceptions.InsufficientAccountBalance;

import java.math.BigDecimal;
import java.util.Objects;

import static model.TransactionType.DEPOSIT;
import static model.TransactionType.WITHDRAW;

public class Customer {
    private final Profile profile;
    private BigDecimal balance;
    private TransactionType transactionType;

    public Customer(Profile profile, BigDecimal balance) {

        this.profile = profile;
        this.balance = balance;
    }

    public void deposit(BigDecimal amount) {
        transactionType = DEPOSIT;
        balance = balance.add(amount);
    }

    public void withdraw(BigDecimal amount) {
        transactionType = WITHDRAW;
        if (balance.compareTo(amount) < 0) {
            throw new InsufficientAccountBalance("Your account balance is insufficient");
        }
        balance = balance.subtract(amount);
    }


    public BigDecimal getBalance() {
        return balance;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void check () {

        profile.check();
        if (balance == null) {
            throw new CustomerDetailsException("You must enter the balance");
        }
    }


    @Override
    public String toString() {
        return "Customer{" +
                "profile=" + profile +
                ", balance=" + balance +
                ", transactionType=" + transactionType +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(profile, customer.profile) && Objects.equals(balance, customer.balance) && transactionType == customer.transactionType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(profile, balance, transactionType);
    }

    public String getNationalId() {
        return profile.getNationalId();
    }

    public String getFirstName() {
        return profile.getFirstName();
    }

    public String getLastName() {
        return profile.getLastName();
    }

    public String getPhoneNumber() {
        return profile.getPhoneNumber();
    }
}

