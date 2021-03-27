package com.emilianomenendez.veritran.money;

import com.emilianomenendez.veritran.NegativeAmountException;

import java.util.Objects;

public class Dollars implements Number {
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

    @Override
    public int getAmount() {
        return amount;
    }

    public boolean isLessThan(Number other) {
        return amount < other.getAmount();
    }

    public Number plus(Number amountToAdd) {
        return Dollars.amount(amount + amountToAdd.getAmount());
    }

    public Number minus(Number amountToSubtract) {
        if (this.isLessThan(amountToSubtract)) {
            throw new InsufficientFundsException();
        }

        return Dollars.amount(amount - amountToSubtract.getAmount());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dollars dollars = (Dollars) o;
        return amount == dollars.amount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }
}
