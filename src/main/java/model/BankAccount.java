package model;

import java.math.BigDecimal;

public class BankAccount {
    private final Profile profile;
    private BigDecimal balance;
    private final Money money;


    public BankAccount(Profile profile, Money money) {
        this.profile = profile;
        this.money = money;
        this.balance = money.getAmount();
        check();
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

    public BigDecimal getAmount() {
        return balance;
    }

    public CurrencyTypes getCurrencyType() {
        return money.getCurrencyType();
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "firstName='" + getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                ", nationalId='" + getNationalId() + '\'' +
                ", phoneNumber='" + getPhoneNumber() + '\'' +
                ", balance=" + getAmount() +
                ", currency=" + getCurrencyType() +
                '}';
    }
}

