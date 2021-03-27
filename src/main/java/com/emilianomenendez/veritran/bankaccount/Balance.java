package com.emilianomenendez.veritran.bankaccount;

import com.emilianomenendez.veritran.money.Dollars;
import com.emilianomenendez.veritran.money.Number;

import java.util.Objects;

public class Balance implements Number {
    private final String sign;
    private final int amount;

    public static Balance create(Number initialBalance) {
        return Balance.create(initialBalance.getAmount());
    }

    private static Balance create(int amount) {
        if (amount >= 0) {
            return new Balance("+", amount);
        } else {
            return new Balance("-", amount);
        }
    }

    public static Number positive(Dollars amount) {
        return new Balance("+", amount.getAmount());
    }

    public static Number negative(Dollars amount) {
        return new Balance("-", -amount.getAmount());
    }

    private Balance(String sign, int amount) {
        this.sign = sign;
        this.amount = amount;
    }

    @Override
    public int getAmount() {
        return amount;
    }

    @Override
    public boolean isLessThan(Number other) {
        return amount < other.getAmount();
    }

    @Override
    public Balance plus(Number other) {
        return create(this.amount + other.getAmount());
    }

    @Override
    public Balance minus(Number other) {
        return create(this.amount - other.getAmount());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Balance balance = (Balance) o;
        return amount == balance.amount && sign.equals(balance.sign);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sign, amount);
    }
}
