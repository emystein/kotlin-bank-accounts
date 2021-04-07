package ar.com.flow.money;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class Money {
    private final String currency;
    private final int amount;

    public Money(String currency, int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount must be non-negative: " + amount);
        }

        this.currency = currency;
        this.amount = amount;
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
}
