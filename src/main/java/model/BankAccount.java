package model;

import exceptions.InsufficientAccountBalanceException;
import exceptions.VariousCurrencyException;

import java.math.BigDecimal;
import java.util.UUID;

public class BankAccount {
    private final Profile profile;
    private BigDecimal balance;
    private final Money money;
    private final String accountNumber;

    public BankAccount(Profile profile, Money money) {
        this.profile = profile;
        this.money = money;
        this.balance = money.getAmount();
        check();
        this.accountNumber = accountNumberGenerator();
    }

    private String accountNumberGenerator() {
        return UUID.randomUUID().toString().replace("-", "")
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

    public String getAccountNumber() {
        return accountNumber;
    }
}

