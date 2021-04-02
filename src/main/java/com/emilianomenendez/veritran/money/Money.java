package com.emilianomenendez.veritran.money;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
public class Money {
    private final String currency;
    private final int amount;

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
}
