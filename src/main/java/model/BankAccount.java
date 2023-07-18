package model;

import exceptions.InsufficientAccountBalanceException;
import exceptions.VariousCurrencyException;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class BankAccount {
    private final Profile profile;
    private BigDecimal balance;
    private final Money money;
    private String accountNumber;

    public BankAccount(Profile profile, Money money) {

        this.profile = profile;
        this.money = money;
        this.balance = money.getAmount();
    }

    private String accountNumberGenerator() {
        return accountNumber = UUID.randomUUID().toString().replace("-", "")
                .replaceAll("[^0-9]", "").substring(0, 10);
    }

    public void deposit(Money money) {
        if (this.money.getCurrencyType() != money.getCurrencyType()) {
            throw new VariousCurrencyException();
        }
        balance = balance.add(money.getAmount());
    }

    public void withdraw(Money money) {
        if (this.money.getCurrencyType() != money.getCurrencyType()) {
            throw new VariousCurrencyException();
        }
        if (balance.compareTo(money.getAmount()) < 0) {
            throw new InsufficientAccountBalanceException();
        }
        balance = balance.subtract(money.getAmount());
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void check() {
        profile.check();
        money.check();
    }

    public String getFirstName() {
        return profile.getFirstName();
    }

    public String getLastName() {
        return profile.getLastName();
    }

    public String getNationalId() {
        return profile.getNationalId();
    }

    public String getPhoneNumber() {
        return profile.getPhoneNumber();
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "profile=" + profile +
                ", balance=" + balance +
                ", money=" + money +
                ", accountNumber='" + accountNumber + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BankAccount that = (BankAccount) o;
        return Objects.equals(profile, that.profile) && Objects.equals(balance, that.balance) && Objects.equals(money, that.money) && Objects.equals(accountNumber, that.accountNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(profile, balance, money, accountNumber);
    }
}


