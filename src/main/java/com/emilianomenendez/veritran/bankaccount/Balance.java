package com.emilianomenendez.veritran.bankaccount;

import com.emilianomenendez.veritran.money.Money;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
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

    public static Balance sum(Balance balance1, Balance balance2) {
        return Balance.create(balance1.currency, balance1.getAmount() + balance2.getAmount());
    }

    public boolean isGreaterThanOrEqual(Money other) {
        return amount >= other.getAmount();
    }

    public boolean isGreaterThanOrEqual(Balance other) {
        return getAmount() >= other.getAmount();
    }

    public Balance plus(Money other) {
        return create(this.currency, this.amount + other.getAmount());
    }

    public Balance minus(Money other) {
        return create(this.currency, this.amount - other.getAmount());
    }
}
