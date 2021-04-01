package com.emilianomenendez.veritran.money;

import com.emilianomenendez.veritran.NegativeAmountException;

import java.util.Objects;

public class Dollars implements Money {
    private final int amount;

    public static Dollars amount(int amount) {
        if (amount < 0) {
            throw new NegativeAmountException(amount);
        }

        return new Dollars(amount);
    }

    private Dollars(int amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return "USD";
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
        return Dollars.amount(amount + amountToAdd.getAmount());
    }

    public Money minus(Money amountToSubtract) {
        if (this.isLessThan(amountToSubtract)) {
            throw new InsufficientFundsException();
        }

        return Dollars.amount(amount - amountToSubtract.getAmount());
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dollars dollars = (Dollars) o;
        return amount == dollars.amount;
    }

    public int hashCode() {
        return Objects.hash(amount);
    }
}
