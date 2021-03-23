package com.emilianomenendez.veritran;

import java.util.Objects;

public class Dollars {
    private int amount;

    public Dollars(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public boolean isNegative() {
        return amount < 0;
    }

    public boolean isGreaterThan(Dollars amountToCheck) {
        return amount > amountToCheck.amount;
    }

    public Dollars plus(Dollars amountToAdd) {
        return new Dollars(amount += amountToAdd.amount);
    }

    public Dollars minus(Dollars amountToSubtract) {
        return new Dollars(amount -= amountToSubtract.amount);
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
