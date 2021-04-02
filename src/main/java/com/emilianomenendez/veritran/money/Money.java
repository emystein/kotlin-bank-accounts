package com.emilianomenendez.veritran.money;

import java.util.Objects;

public class Money {
    private final String currency;
    private final int amount;

    public Money(String currency, int amount) {
        this.currency = currency;
        this.amount = amount;
    }

    public String getCurrency() {
        return this.currency;
    }

    public int getAmount() {
        return amount;
    }

    public boolean isGreaterThanOrEqual(Money other) {
        return amount >= other.getAmount();
    }

    public boolean isLessThan(Money other) {
        return amount < other.getAmount();
    }

    public Money plus(Money amountToAdd) {
        return new Money(currency, amount + amountToAdd.getAmount());
    }

    public Money minus(Money amountToSubtract) {
        if (this.isLessThan(amountToSubtract)) {
            throw new InsufficientFundsException();
        }

        return new Money(currency, amount - amountToSubtract.getAmount());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Money)) return false;
        Money money = (Money) o;
        return amount == money.amount && currency.equals(money.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currency, amount);
    }
}
