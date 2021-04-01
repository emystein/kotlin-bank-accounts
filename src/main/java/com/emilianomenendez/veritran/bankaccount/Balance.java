package com.emilianomenendez.veritran.bankaccount;

import com.emilianomenendez.veritran.money.Dollars;
import com.emilianomenendez.veritran.money.Money;
import com.emilianomenendez.veritran.money.Number;

import java.util.Objects;

public class Balance implements Money {
    private final String currency;
    private final String sign;
    private final int amount;

    public static Balance create(Money initialBalance) {
        return Balance.create(initialBalance.getCurrency(), initialBalance.getAmount());
    }

    private static Balance create(String currency, int amount) {
        if (amount >= 0) {
            return new Balance(currency, "+", amount);
        } else {
            return new Balance(currency, "-", amount);
        }
    }

    public static Money positive(Dollars amount) {
        return new Balance(amount.getCurrency(), "+", amount.getAmount());
    }

    public static Money negative(Dollars amount) {
        return new Balance(amount.getCurrency(), "-", -amount.getAmount());
    }

    private Balance(String currency, String sign, int amount) {
        this.currency = currency;
        this.sign = sign;
        this.amount = amount;
    }

    @Override
    public String getCurrency() {
        return currency;
    }

    @Override
    public int getAmount() {
        return amount;
    }

    @Override
    public boolean isGreaterThanOrEqual(Number other) {
        return amount >= other.getAmount();
    }

    @Override
    public boolean isLessThan(Number other) {
        return amount < other.getAmount();
    }

    @Override
    public Balance plus(Number other) {
        return create(this.currency, this.amount + other.getAmount());
    }

    @Override
    public Balance minus(Number other) {
        return create(this.currency, this.amount - other.getAmount());
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
