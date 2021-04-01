package com.emilianomenendez.veritran.bankaccount;

import com.emilianomenendez.veritran.money.Money;

import java.util.Objects;

public abstract class Balance {
    private final String currency;
    private final int amount;

    public static Balance create(Money initialBalance) {
        return Balance.create(initialBalance.getCurrency(), initialBalance.getAmount());
    }

    private static Balance create(String currency, int amount) {
        if (amount >= 0) {
            return new PositiveBalance(currency, amount);
        } else {
            return new NegativeBalance(currency, amount);
        }
    }

    public static Balance positive(Money amount) {
        return new PositiveBalance(amount.getCurrency(), amount.getAmount());
    }

    public static Balance negative(Money amount) {
        return new NegativeBalance(amount.getCurrency(), amount.getAmount());
    }

    public Balance(String currency, int amount) {
        this.currency = currency;
        this.amount = amount;
    }

    public abstract String getSign();

    public String getCurrency() {
        return currency;
    }

    public int getAmount() {
        return amount;
    }

    public boolean isGreaterThanOrEqual(Money other) {
        return amount >= other.getAmount();
    }

    public boolean isGreaterThanOrEqual(Balance other) {
        return getAmount() >= other.getAmount();
    }

    public boolean isLessThan(Balance other) {
        return amount < other.getAmount();
    }

    public Balance plus(Money other) {
        return create(this.currency, this.amount + other.getAmount());
    }

    public Balance minus(Money other) {
        return create(this.currency, this.amount - other.getAmount());
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Balance balance = (Balance) o;
        return amount == balance.amount && getSign().equals(balance.getSign());
    }

    public int hashCode() {
        return Objects.hash(getSign(), amount);
    }
}
