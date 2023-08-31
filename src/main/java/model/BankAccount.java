package model;

import java.math.BigDecimal;
import java.util.UUID;

public class BankAccount {
    private Profile profile;
    private BigDecimal balance;
    private Money firstDeposit;
    private final String accountNumber;
    private CurrencyTypes currencyType;


    public BankAccount(Profile profile, Money firstDeposit) {
        this.profile = profile;
        this.firstDeposit = firstDeposit;
        this.balance = firstDeposit.getAmount();
        check();
        this.accountNumber = accountNumberGenerator();
    }

    public BankAccount(String accountNumber,CurrencyTypes currencyType, BigDecimal balance ) {
        this.accountNumber = accountNumber;
        this.currencyType = currencyType;
        this.balance = balance;
    }

    public String accountNumberGenerator() {
        return UUID.randomUUID().toString().replace("-", "")
                .replaceAll("[^0-9]", "").substring(0, 10);
    }

    public void deposit(Money money) {
        if (currencyType != money.getCurrencyType()) {
            throw new VariousCurrencyException();
        }
        balance = balance.add(money.getAmount());
    }

    public void withdraw(Money money) {
        if (currencyType != money.getCurrencyType()) {
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
        firstDeposit.check();
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
        return firstDeposit.getCurrencyType();
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public Money getFirstDeposit() {
        return firstDeposit;
    }

}

