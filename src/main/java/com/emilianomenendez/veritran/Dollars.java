package com.emilianomenendez.veritran;

import java.util.Objects;

public class Dollars {
    private int amount;

    public static Dollars amount(int amount) {
        if (amount < 0) {
            throw new NegativeAmountException(amount);
        }

        return new Dollars(amount);
    }

    private Dollars(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public boolean isLessThan(Dollars other) {
        return amount < other.amount;
    }

    public Dollars plus(Dollars amountToAdd) {
        return Dollars.amount(amount += amountToAdd.amount);
    }

    public Dollars minus(Dollars amountToSubtract) {
        return Dollars.amount(amount -= amountToSubtract.amount);
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
