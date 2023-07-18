package model;

import exceptions.InvalidEntryException;

import java.math.BigDecimal;

public class Money {

    private final BigDecimal amount;

    private final CurrencyTypes currencyTypes;

    public Money(BigDecimal amount, CurrencyTypes currencyTypes) {
        this.currencyTypes = currencyTypes;
        this.amount = amount;
    }

    public void check() {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0 ) {
            throw new InvalidEntryException();
        }
    }
    public BigDecimal getAmount() {
        return amount;
    }

    public CurrencyTypes getCurrencyType() {
        return currencyTypes;
    }

    @Override
    public String toString() {
        return "Money{" +
                "amount=" + amount +
                ", currencyTypes=" + currencyTypes +
                '}';
    }
}
